package main.java.devices.interfaces;

public interface IActuator extends IDevice {
    void sendValue(int value);
}
