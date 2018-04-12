package core.device;

import com.google.common.collect.EvictingQueue;
import core.Message;
import core.utils.Utils;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Sensor extends Device implements ISensor {

    private Queue<Message> queue;

    public Sensor(String ID, SerialPort serialPort, OutputStream outputStream, InputStream inputStream, int bufferSize) {
        super(ID, serialPort, outputStream, inputStream);
        queue = EvictingQueue.create(bufferSize);
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
            queue.add(
                    new Message(ID,
                            Integer.parseInt(strValueTimestamp.split(" ")[0]),
                            Long.parseLong(strValueTimestamp.split(" ")[1])
                    )
            );
        }
    }

    @Override
    public List<Message> getData() {
        return new ArrayList<>(queue);
    }

}
