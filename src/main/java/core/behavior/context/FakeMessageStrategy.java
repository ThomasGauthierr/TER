package core.behavior.context;

import core.Message;
import core.device.IDevice;

public class FakeMessageStrategy {

    IDevice device;
    IFakeValueStrategy fakeValueStrategy;

    public FakeMessageStrategy(IDevice device){
        this.device = device;
        this.fakeValueStrategy = new WeatherStrategy(20, 2);
    }

    public Message getNextMessage() {
        return new Message(device.getID(), fakeValueStrategy.getNextValue(), System.currentTimeMillis());
    }

    public void setFakeValueStrategy(IFakeValueStrategy fakeValueStrategy) {
        this.fakeValueStrategy = fakeValueStrategy;
    }
}
