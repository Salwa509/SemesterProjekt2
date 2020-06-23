import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class EKGDAOImpl implements EKGDAO {

    @Override
    public void saveBatch(List<EKGDTO> batch) {
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("INSERT INTO EKGMeasurements" +
                    " (EKGPatientID,EKGMeasurements,EKGTime) VALUES (?,?,?)");
            for (EKGDTO ekgDTO : batch) {
                preparedStatement.setInt(1, ekgDTO.getEKGPatientID());
                preparedStatement.setDouble(2, ekgDTO.getEKGMeasurements());
                preparedStatement.setTimestamp(3, ekgDTO.getEKGTime());
                preparedStatement.setString(3, ekgDTO.getEKGCpr());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EKGDTO> load(String cpr) {
        List<EKGDTO> listEKG = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("SELECT * FROM patient AS p " +
                    "JOIN EKGMeasurements AS E ON p.PatientID = E.EKGPatientID WHERE CPR = ? ");
            preparedStatement.setString(1, cpr);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EKGDTO ekgDTO = new EKGDTO();
                ekgDTO.setEKGPatientID(resultSet.getInt("EKGPatientID"));
                ekgDTO.setEKGMeasurements(resultSet.getDouble("EKGMeasurements"));
                ekgDTO.setEKGTime(resultSet.getTimestamp("EKGTime"));
                ekgDTO.setEKGCpr(resultSet.getString("EKGCpr"));
                listEKG.add(ekgDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listEKG;
    }
}
