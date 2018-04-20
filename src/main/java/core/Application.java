package core;

import core.behavior.contract.ActionType;
import core.behavior.contract.ContractImpl;
import core.behavior.contract.IContract;
import core.behavior.Manager;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;
import core.device.sensor.Sensor;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Application {

    private List<Manager> managers;
    private List<Sensor> sensors;
    private List<IActuator> actuators;

    public Application() {
        sensors = new ArrayList<>();
        actuators = new ArrayList<>();
        managers = new ArrayList<>();

        // BASIC IMPL WITH 1 MANAGER and contract

        // The predicate tests that the value is < 500 so the actiontype is decrease because when it is violated it means the value has decreased
        IContract<Message> contract = new ContractImpl<>(new Predicate<Message>() {
            @Override
            public boolean test(Message message) {
                System.out.println(this + " testing predicate : " + message.getValue() + " > 500");
                return message.getValue() < 500;
            }
        },ActionType.INCREASE);

        managers.add(new Manager(contract));
    }

    public void init() throws TooManyListenersException {

        // retrieve values from sensors
        for (Sensor sensor : sensors) {

            // The sensor is observed by the manager
            sensor.addObserver(managers.get(0));

            SerialPort sp = sensor.getSerialPort();
            sp.notifyOnDataAvailable(true);
            sp.addEventListener(serialPortEvent -> {
                switch (serialPortEvent.getEventType()) {
                    case SerialPortEvent.DATA_AVAILABLE:
                        sensor.collect();
                        break;
                    default:
                        break;
                }
            });
        }

        for (IActuator actuator : actuators) {
            managers.get(0).addUnrespectedContractListener(actuator);
        }


    }

    public void addSensor(Sensor sensor) {
        sensors.add(sensor);
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public List<ISensor> getSensors(String groupId) {
        return sensors.stream().filter(s -> s.getID().equals(groupId)).collect(Collectors.toList());
    }

    public void addActuator(IActuator actuator) {
        actuators.add(actuator);
    }

    public List<IActuator> getActuators() {
        return actuators;
    }

    public List<IActuator> getActuators(String groupId) {
        return actuators.stream().filter(a -> a.getID().equals(groupId)).collect(Collectors.toList());
    }

    public void addManager(Manager manager) {
        managers.add(manager);
    }

    public List<Manager> getManagers() {
        return managers;
    }
}
