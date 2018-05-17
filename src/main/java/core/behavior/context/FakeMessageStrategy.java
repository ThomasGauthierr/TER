package core.behavior.context;

import core.Message;
import core.device.IDevice;

public class FakeMessageStrategy {

    IDevice device;
    int value;
    double delta;

    public FakeMessageStrategy(IDevice device){
        this.device = device;
        value = 20;
        delta = 1.5;
    }

    public Message getNextMessage() {
        return new Message(device.getID(), (int) ((value - delta) + Math.random() * (delta * 2)), System.currentTimeMillis());
    }

    public void setFakeValueStrategy(int value, double delta) {
        this.value = value;
        this.delta = delta;
    }
}
