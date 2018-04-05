package core.device;

public interface IActuator extends IDevice {
    void sendValue(int value);
}
