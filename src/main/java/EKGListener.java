import java.util.LinkedList;

public interface EKGListener {
    void notify(LinkedList<EKGDTO> ekgDTO);
}
