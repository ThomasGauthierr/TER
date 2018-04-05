package core;

import core.device.IActuator;
import core.device.IGroup;
import core.device.ISensor;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.stream.Collectors;

public class Application {

    private List<ISensor> sensors;
    private List<IActuator> actuators;
    private List<IGroup> groups;

    public Application() {
        sensors = new ArrayList<>();
        actuators = new ArrayList<>();
        groups = new ArrayList<>();
    }

    public void init() throws TooManyListenersException {

        // retrieve values from sensors
        for (ISensor sensor : sensors) {

            SerialPort sp = sensor.getSerialPort();
            sp.notifyOnDataAvailable(true);
            sp.addEventListener(serialPortEvent -> {
                switch (serialPortEvent.getEventType()) {
                    case SerialPortEvent.DATA_AVAILABLE:
                        // data is available !!!
                        sensor.collect();
                        sensor.getData().forEach(System.out::println);
                        /*IGroup g = sensor.getGroup();
                        if(g.getContract().isRespected(sensor.getData())){

                        } else {
                            g.repair();
                        }*/
                        break;
                    default:
                        break;
                }
            });
        }

        // contracts

        // repair (optional)

        // update actuators

    }

    public void addSensor(ISensor sensor) {
        sensors.add(sensor);
    }

    public List<ISensor> getSensors() {
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

    public void addGroup(IGroup group) {
        groups.add(group);
    }

    public List<IGroup> getGroups() {
        return groups;
    }
}
