import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SyncQueue {
    private Queue<Integer> queue = new LinkedList<>();
    private final int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public SyncQueue(int capacity) {
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return queue.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    public boolean isFull() {
        lock.lock();
        try {
            return queue.size() == capacity;
        } finally {
            lock.unlock();
        }
    }

    public void enqueue(int value) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await();      //waits till the queue is not full
            }
            queue.add(value);    //adds at the tail
            notEmpty.signal();    //signals that the queue has an element in it
        } finally {
            lock.unlock();
        }
    }

    public int dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();       //waits till the queue is not empty anymore
            }
            int value = queue.remove();     //takes the element from the head
            notFull.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }
}
