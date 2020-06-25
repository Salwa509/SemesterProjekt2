public class ProducerConsumerThread implements Runnable {
    private EKGListener listener;

    public ProducerConsumerThread(EKGListener listener) {
        this.listener = listener;
    }

    public void run() {
        final ProducerConsumer producerConsumer = new ProducerConsumer();
        producerConsumer.registerObserver(this.listener);
        Thread t1 = new Thread(() -> {
            try {
                producerConsumer.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                producerConsumer.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}