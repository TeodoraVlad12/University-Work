import model.Account;
import model.Operation;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

class BankSystem {
    static final int CREATOR_THREAD_COUNT = 50;  // 5 threads

    static final int CONSISTENCY_CHECK_TIMES = 10;
    static int nextSerialNumber = 0;

    static Map<Integer, Account> accounts = new ConcurrentHashMap<>();

    static List<Operation> operations = Collections.synchronizedList(new ArrayList<>());

    static ReentrantLock serialNumberLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        initializeAccounts(10);  //  10 accounts

        ExecutorService executor = Executors.newFixedThreadPool(CREATOR_THREAD_COUNT);


        for (int i = 0; i < CREATOR_THREAD_COUNT; i++) {
            executor.execute(BankSystem::createTransaction);
            if (i % CONSISTENCY_CHECK_TIMES ==0 ){
                checkGlobalConsistency();
            }
        }

        executor.shutdown();
        executor.awaitTermination(100, TimeUnit.MILLISECONDS);

        checkGlobalConsistency();

        printAllOperations();
    }

    public static void initializeAccounts(int numAccounts) {
        for (int i = 0; i < numAccounts; i++) {
            accounts.put(i, new Account(i, 1000));
        }
    }

    public static void createTransaction() {
        Random random = new Random();

        int senderId = random.nextInt(accounts.size());
        int receiverId = random.nextInt(accounts.size());

        while (senderId == receiverId) {
            receiverId = random.nextInt(accounts.size());
        }

        int amount = random.nextInt(100) + 1;  // Random amount between 1 and 100

        Account firstLock, secondLock;
        if (senderId < receiverId) {
            firstLock = accounts.get(senderId);
            secondLock = accounts.get(receiverId);
        } else {
            firstLock = accounts.get(receiverId);
            secondLock = accounts.get(senderId);
        }

        firstLock.getMutex().lock();
        secondLock.getMutex().lock();

        try {
            Account sender = accounts.get(senderId);
            Account receiver = accounts.get(receiverId);

            if (sender.getAmount() >= amount) {
                sender.setAmount(sender.getAmount() - amount);
                receiver.setAmount(receiver.getAmount() + amount);

                serialNumberLock.lock();
                int serial = nextSerialNumber++;
                serialNumberLock.unlock();

                Operation operation = new Operation(serial, senderId, receiverId, amount);

                // Log the operation in both accounts
                sender.getLog().add(operation);
                receiver.getLog().add(operation);

                synchronized (operations) {
                    operations.add(operation);
                }

                checkConsistencyForAccounts(sender, receiver);
            }
        } finally {
            secondLock.getMutex().unlock();
            firstLock.getMutex().unlock();
        }
    }



    public static void printAllOperations() {
        synchronized (operations) {
            for (Operation operation : operations) {
                System.out.println("Operation Serial: " + operation.getSerialNumber());
                System.out.println("From Account: " + operation.getSourceAccountId());
                System.out.println("To Account: " + operation.getDestinationAccountId());
                System.out.println("Amount: " + operation.getAmount());
                System.out.println("-------------------");
            }
        }
    }

    public static void checkConsistencyForAccounts(Account sender, Account receiver) {
        Account firstLock, secondLock;
        if (sender.getId() < receiver.getId()) {
            firstLock = sender;
            secondLock = receiver;
        } else {
            firstLock = receiver;
            secondLock = sender;
        }

        firstLock.getMutex().lock();
        secondLock.getMutex().lock();

        try {

            int recalculatedBalanceFirst = firstLock.getInitialBalance();
            int recalculatedBalanceSecond = secondLock.getInitialBalance();


            for (Operation operation : firstLock.getLog()) {
                if (operation.getSourceAccountId() == firstLock.getId()) {
                    recalculatedBalanceFirst -= operation.getAmount();
                }
                if (operation.getDestinationAccountId() == firstLock.getId()) {
                    recalculatedBalanceFirst += operation.getAmount();
                }
            }


            for (Operation operation : secondLock.getLog()) {
                if (operation.getSourceAccountId() == secondLock.getId()) {
                    recalculatedBalanceSecond -= operation.getAmount();
                }
                if (operation.getDestinationAccountId() == secondLock.getId()) {
                    recalculatedBalanceSecond += operation.getAmount();
                }
            }


            boolean balancesConsistent = true;
            if (recalculatedBalanceFirst != firstLock.getAmount()) {
                balancesConsistent = false;
                System.out.println("Balance inconsistency detected for Account " + firstLock.getId());
            }
            if (recalculatedBalanceSecond != secondLock.getAmount()) {
                balancesConsistent = false;
                System.out.println("Balance inconsistency detected for Account " + secondLock.getId());
            }


            // Determine overall consistency
            if (balancesConsistent) {
                System.out.println("Consistency check passed for Accounts " + sender.getId() + " and " + receiver.getId());
            } else {
                System.out.println("Consistency check failed for Accounts " + sender.getId() + " and " + receiver.getId());
            }

        } finally {
            secondLock.getMutex().unlock();
            firstLock.getMutex().unlock();
        }
    }



    public static void checkGlobalConsistency() {
        boolean isConsistent = true;

        for (Account account : accounts.values()) {
            account.getMutex().lock();
            try {
                int recalculatedBalance = account.getInitialBalance();

                for (Operation operation : account.getLog()) {
                    if (operation.getSourceAccountId() == account.getId()) {
                        recalculatedBalance -= operation.getAmount();
                    }
                    if (operation.getDestinationAccountId() == account.getId()) {
                        recalculatedBalance += operation.getAmount();
                    }
                }

                if (recalculatedBalance != account.getAmount()) {
                    isConsistent = false;
                    System.out.println("Consistency check failed for Account " + account.getId());
                }
            } finally {
                account.getMutex().unlock();
            }
        }

        if (isConsistent) {
            System.out.println("Global consistency check passed.");
        } else {
            System.out.println("Global consistency check failed.");
        }
    }


}


