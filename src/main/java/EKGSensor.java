import jssc.SerialPort;
import jssc.SerialPortList;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class EKGSensor {
    private SerialPort serialPort = null;
    private String result = null;
    private String buffer = "";

    public EKGSensor(int portNummer) {
        //konstruktør oprettes
        String[] portnames = null;//oprettelse af StringArray
        try {
            portnames = SerialPortList.getPortNames();//her hentes navnene til portene der er tilkoblet computeren
            serialPort = new SerialPort(portnames[portNummer]);//objektet serialPort tildeles den første port
            serialPort.openPort();//porten åbnes
            serialPort.setRTS(true);//klar til at sende(ReadyToSend = true)
            serialPort.setDTR(true);//klar til at modtage(DataToReceive = true)
            serialPort.setParams(115200, 8, 1, SerialPort.PARITY_NONE);//parametre bestemmes
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);//kontrolere flowet af data
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<EKGDTO> getData() {//metoden oprettes
        try {
            if (serialPort.getInputBufferBytesCount() >= 12) {//kontrolstruktur
                result = serialPort.readString();//strengen aflæses og tildeles result
                buffer += result;
                int index = buffer.lastIndexOf(' ');
                result = buffer.substring(0, index);
                buffer = buffer.substring(index);
                String[] rawValues;
                rawValues = result.split(" ");//nu splittes strengen og gemmes i et array
                List<EKGDTO> values = new LinkedList<>();
                for (int i = 0; i < rawValues.length; i++) {
                    if (!Objects.equals(rawValues[i], "")) {
                        EKGDTO ekgDTO = new EKGDTO();
                        ekgDTO.setEKGMeasurements(Double.parseDouble(rawValues[i]));
                        if (ekgDTO.getEKGMeasurements() < 2048 && ekgDTO.getEKGMeasurements() > -2048) {
                            ekgDTO.setEKGTime(new Timestamp(System.currentTimeMillis()));
                            values.add(ekgDTO);
                        }
                    }
                }
                return values;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//returnArray returneres
    }
}
