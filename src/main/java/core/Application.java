package core;

import core.behavior.contract.*;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;
import core.device.sensor.Sensor;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.TooManyListenersException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Application {

    private List<AbstractContract> contracts;
    private List<Sensor> sensors;
    private List<IActuator> actuators;

    public Application() {
        sensors = new ArrayList<>();
        actuators = new ArrayList<>();
        contracts = new ArrayList<>();

        // BASIC IMPL WITH 1 MANAGER and contract

        // The predicate tests that the value is < 700 so the actiontype is decrease because when it is violated it means the value has decreased
        AbstractContract contract = new ContractImpl("Contract01", new ContractPredicate(o -> {
            if(o instanceof ISensor) {
                ISensor sensor = (ISensor) o;
                return sensor.getData().stream().anyMatch(m -> m.getValue() < 30);
            } else if(o instanceof IContract) {
                // we don't care
            }
            return true;
        }, ActionType.DECREASE));

        AbstractContract contract2 = new ContractImpl("Contract02", new ContractPredicate(o -> {
            if(o instanceof ISensor) {
                // we don't care
            } else if(o instanceof IContract) {
                IContract c = (IContract) o;
                if(c.getName().equalsIgnoreCase("contract01")) {
                    System.out.println("u wot m8");
                }
            }
            return true;
        }, ActionType.DECREASE));

        contracts.add(contract2);
        contracts.add(contract);

        contract2.addObserver(contract);
    }

    public void init() throws TooManyListenersException {

        AbstractContract test = contracts.get(0);
        // retrieve values from sensors
        for (Sensor sensor : sensors) {

            test.addObservable(sensor);

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
            test.addObserver(actuator);
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

}
