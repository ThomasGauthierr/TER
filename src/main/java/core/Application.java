package core;

import core.behavior.context.ContextImpl;
import core.behavior.context.IContext;
import core.behavior.contract.IContract;
import core.behavior.contract.builder.ArithmeticCondition;
import core.behavior.contract.builder.ContractStepBuilder;
import core.device.DataType;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;

import java.util.*;

public class Application {

    private Map<String, IContext> contexts;
    private Map<String, IContract> contracts;
    private Map<String, ISensor> sensors;
    private Map<String, IActuator> actuators;
    private Annuaire annuaire;

    public Application() {
        sensors = new HashMap<>();
        actuators = new HashMap<>();
        contracts = new HashMap<>();
        contexts = new HashMap<>();
        annuaire = Annuaire.getInstance();
        annuaire.populateFromFile("Annuaire.json");
    }

    public void init() throws TooManyListenersException {

        for (ISensor sensor : sensors.values()) {

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

        /* New way of building contracts, a very simple way */
        addContract(
                ContractStepBuilder.newBuilder()
                        .name("contract01")
                        .on(contexts.get("classroom1"))
                        .where(DataType.TEMPERATURE)
                        .is(ArithmeticCondition.LOWER_THAN_OR_EQUAL_TO, 21)
                        .build()
        );
    }

    public void addSensor(ISensor sensor) {
        String ctxName = annuaire.getInformationAbout(sensor.getID()).getContextName();
        IContext ctx = contexts.get(ctxName);
        if (ctx == null) {
            ctx = new ContextImpl(ctxName);
            contexts.put(ctxName, ctx);
        }
        sensor.addListener(ctx);
        ctx.getSensors().add(sensor);
        this.sensors.put(sensor.getID(), sensor);
    }

    public void addSensors(List<ISensor> sensors) {
        sensors.forEach(this::addSensor);
    }

    public void addActuator(IActuator actuator) {
        String ctxName = annuaire.getInformationAbout(actuator.getID()).getContextName();
        IContext ctx = contexts.get(ctxName);
        if (ctx == null) {
            ctx = new ContextImpl(ctxName);
            contexts.put(ctxName, ctx);
        }

        // sensor.addListener(ctx);
        ctx.getActuators().add(actuator);
        this.actuators.put(actuator.getID(), actuator);
    }

    public void addActuators(List<IActuator> actuators) {
        actuators.forEach(this::addActuator);
    }

    public Collection<ISensor> getSensors() {
        return sensors.values();
    }

    public Collection<IActuator> getActuators() {
        return actuators.values();
    }

    public void addContract(IContract contract) {
        this.contracts.put(contract.getName(), contract);
    }

}
