public class ProducerConsumerThread implements Runnable {
    private EKGListener listener;

    public ProducerConsumerThread(EKGListener listener) {
        this.listener = listener;
    }

    public void run() {
        // Object of a class that has both produce()
        // and consume() methods
        final ProducerConsumer producerConsumer = new ProducerConsumer();
        producerConsumer.registerObserver(this.listener);
        // Create producer thread
        Thread t1 = new Thread(() -> {
            try {
                producerConsumer.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Create consumer thread
        Thread t2 = new Thread(() -> {
            try {
                producerConsumer.consumeDB();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                producerConsumer.consumeGUI();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start both threads
        t1.start();
        t2.start();
        t3.start();

        // t1 finishes before t2

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}