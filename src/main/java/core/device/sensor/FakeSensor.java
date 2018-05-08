package core.device.sensor;

import com.google.common.collect.EvictingQueue;
import core.Message;
import core.behavior.context.FakeMessageStrategy;
import core.behavior.context.IFakeValueStrategy;
import core.device.DataType;
import core.device.Device;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Queue;

public class FakeSensor extends Sensor {

    private FakeMessageStrategy fakeMessageStrategy;

    public FakeSensor(String ID, int bufferSize, DataType dataType, SerialPort serialPort) {
        super(ID, serialPort, null, null, bufferSize, dataType);
        fakeMessageStrategy = new FakeMessageStrategy(this);
    }

    @Override
    public void collect() {
        queue = EvictingQueue.create(bufferSize);
        Message message = fakeMessageStrategy.getNextMessage();
        queue.add(message);
        setChanged();
        notifyObservers(this);
        System.out.println("i collected fake data " + message);
    }

    public void setFakeMessageStrategyBehavior(IFakeValueStrategy fakeValueStrategy){
        fakeMessageStrategy.setFakeValueStrategy(fakeValueStrategy);
    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public void close() {}

    @Override
    public void writeString(String s) { }

    @Override
    public void writeInt(int i) { }
}
