public class ScalarProduct {
    private final SyncQueue syncQueue = new SyncQueue(5);
    private final int[] vectorA = new int[100];
    private final int[] vectorB = new int[100];

    public ScalarProduct() {
        for (int i = 0; i < 100; i++) {
            vectorA[i] =  i+1;
            vectorB[i] =  i+1;
        }
    }

    public void producer() {
        try {
            for (int i = 0; i < vectorA.length; i++) {
                int product = vectorA[i] * vectorB[i];
                syncQueue.enqueue(product);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void consumer() {
        int result = 0;
        try {
            for (int i = 0; i < vectorA.length; i++) {
                int product = syncQueue.dequeue();
                result += product;
            }
            System.out.println("Final Scalar Product: " + result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting Scalar Product Calculation!");

        ScalarProduct scalarProduct = new ScalarProduct();

        Thread producerThread = new Thread(() -> scalarProduct.producer());
        Thread consumerThread = new Thread(() -> scalarProduct.consumer());

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Scalar Product Calculation Completed!");
    }
}
