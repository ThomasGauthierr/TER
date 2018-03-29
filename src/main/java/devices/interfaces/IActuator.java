package devices.interfaces;

public interface IActuator extends IDevice {
    void sendValue(int value);
}
