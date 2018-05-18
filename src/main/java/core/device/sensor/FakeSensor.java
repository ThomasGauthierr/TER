package core.device.sensor;

import com.google.common.collect.EvictingQueue;
import core.Message;
import core.behavior.context.FakeMessageStrategy;
import core.device.DataType;
import gnu.io.SerialPort;

public class FakeSensor extends Sensor {

    private FakeMessageStrategy fakeMessageStrategy;

    public FakeSensor(String ID, int bufferSize, DataType dataType, SerialPort serialPort) {
        super(ID, serialPort, bufferSize, dataType);
        fakeMessageStrategy = new FakeMessageStrategy(this);
    }

    @Override
    public void collect() {
        queue = EvictingQueue.create(bufferSize);
        Message message = fakeMessageStrategy.getNextMessage();
        queue.add(message);

        notifyListeners(this, getData());
    }

    public void setFakeMessageStrategyBehavior(int value, double delta){
        fakeMessageStrategy.setFakeValueStrategy(value, delta);
    }

    @Override
    public void writeString(String s) { }

}
