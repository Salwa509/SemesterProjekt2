import java.util.LinkedList;
import java.util.List;

public class ProducerConsumer implements EKGObserver {
    private EKGSensor EKGSensor = new EKGSensor(0);
    LinkedList<EKGDTO> ekgdtos = new LinkedList<EKGDTO>();
    int capacity = 400;
    private EKGListener listener;

    public void produce() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (ekgdtos.size() == capacity)
                    wait();
                List<EKGDTO> value = EKGSensor.getData();
                if (value != null) {
                    for (EKGDTO i : value) {
                        ekgdtos.add(i);
                        //System.out.println("Producer produced- " + i.getEKGMeasurements());
                    }
                }
                notify();
            }
        }
    }

    public void consumer() throws InterruptedException {
        while (true) {
            LinkedList<EKGDTO> ConsumedList;
            synchronized (this) {
                while (ekgdtos.size() < 100)
                    wait();
                ConsumedList = ekgdtos;
                ekgdtos = new LinkedList<>();
                if (listener != null) {
                    listener.notify(ConsumedList);
                }
                notify();
            }
            /*for (EKGDTO i : ConsumedList) {
                System.out.println("GUInummer " + i.getEKGMeasurements());
            }*/
        }
    }

    @Override
    public void registerObserver(EKGListener listener) {
        this.listener = listener;
    }
}