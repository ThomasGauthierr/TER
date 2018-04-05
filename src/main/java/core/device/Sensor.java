package core.device;

import core.CircularLIFOStack;
import core.ValueTimestamp;
import gnu.io.SerialPort;
import core.utils.Utils;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.TooManyListenersException;

public class Sensor extends Device implements ISensor {

    // Maybe change to FIFO because
    private CircularLIFOStack<ValueTimestamp> stack;

    public Sensor(String ID, SerialPort serialPort, OutputStream outputStream, InputStream inputStream, int bufferSize) {
        super(ID, serialPort, outputStream, inputStream);
        stack = new CircularLIFOStack<>(bufferSize);
    }


    @Override
    public void collect() {
        String[] values = new String[0];
        try {
            values = Utils.getStringFromInputStream(this.getSerialPort().getInputStream()).split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(values[0].length() == 0){
            return;
        }

        for(String strValueTimestamp : values){
            stack.push(
                    new ValueTimestamp(
                            Integer.parseInt(strValueTimestamp.split(" ")[0]),
                            Long.parseLong(strValueTimestamp.split(" ")[1])
                    )
            );
        }
    }

    @Override
    public List<ValueTimestamp> getData() {
        return stack;
    }

}
