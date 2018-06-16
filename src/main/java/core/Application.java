package core;

import core.behavior.context.ConcreteContext;
import core.behavior.context.IContext;
import core.behavior.context.MetaContext;
import core.behavior.contract.IContract;
import core.behavior.contract.builder.ArithmeticCondition;
import core.behavior.contract.builder.ContractStepBuilder;
import core.device.DataType;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

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



        /* New way of building contracts, a very simple way */
        IContext context = contexts.get("couloirEst");
        if (context != null) {
            addContract(
                    ContractStepBuilder.newBuilder()
                            .name("contract01")
                            .on(context)
                            .asConcreteContract()
                            .where(DataType.TEMPERATURE)
                            .is(ArithmeticCondition.LOWER_THAN_OR_EQUAL_TO, 21)
                            .build()
            );

            addContract(
                    ContractStepBuilder.newBuilder()
                            .name("contract02")
                            .on(context)
                            .asConcreteContract()
                            .where(DataType.LIGHT)
                            .is(ArithmeticCondition.HIGHER_THAN, 50)
                            .build()
            );

            IContext metaContext = new MetaContext("BatB");
            metaContext.addObserver(contracts.get("contract01"));
            metaContext.addObserver(contracts.get("contract02"));

            addContract(
                    ContractStepBuilder.newBuilder()
                            .name("contrBat")
                            .on(metaContext)
                            .asMetaContract()
                            .build()
            );
        }


    }

    public void addSensor(ISensor sensor) {
        System.out.println("looking for " + sensor.getIdentifier());
        Annuaire.Information infos = annuaire.getInformationAbout(sensor.getIdentifier());
        if (infos == null) {
            throw new NullPointerException("the given sensor is not in the list");
        }
        IContext ctx = contexts.get(infos.getContextName());
        if (ctx == null) {
            ctx = new ConcreteContext(infos.getContextName());
            contexts.put(infos.getContextName(), ctx);
        }
        // sensor.addListener(ctx);
        ((ConcreteContext) ctx).addSensor(sensor);
        this.sensors.put(sensor.getIdentifier(), sensor);
    }

    public void addSensors(List<ISensor> sensors) {
        sensors.forEach(this::addSensor);
    }

    public void addActuator(IActuator actuator) {
        String ctxName = annuaire.getInformationAbout(actuator.getIdentifier()).getContextName();
        IContext ctx = contexts.get(ctxName);
        if (ctx == null) {
            ctx = new ConcreteContext(ctxName);
            contexts.put(ctxName, ctx);
        }

        // sensor.addListener(ctx);
        ((ConcreteContext) ctx).addActuator(actuator);
        this.actuators.put(actuator.getIdentifier(), actuator);
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
