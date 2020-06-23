import java.util.List;

public interface EKGDAO {
    void saveBatch( List<EKGDTO>  batch);
    List<EKGDTO> load(String cpr);
}
