package core.influxdb;

import core.device.actuator.IActuator;
import core.device.sensor.ISensor;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;


public class InfluxHelper {
    private static InfluxHelper influxHelper;
    private InfluxDB influxDB;

    private static final String DB_NAME = "test";
    private static final String DB_URL = "127.0.0.1:8086";
    private static final String MEASUREMENT_SENSORS = "sensors";
    private static final String MEASUREMENT_ACTUATORS = "actuators";

    private InfluxHelper() {
        influxDB = InfluxDBFactory.connect(DB_URL, "admin", "admin");
        influxDB.setDatabase(DB_NAME);
        influxDB.setRetentionPolicy("autogen");
        influxDB.enableBatch(BatchOptions.DEFAULTS);
    }

    public static InfluxHelper getInfluxHelper() {
        if (influxHelper == null) {
            influxHelper = new InfluxHelper();
        }
        return influxHelper;
    }

    public void close() {
        influxHelper.influxDB.close();
        influxHelper = null;
    }

    public void insertSensorMeasurement(ISensor sensor, int collectedValue) {
        String ID = sensor.getID();
        String dataType = sensor.getDataType().toString();

        long currentTime = System.currentTimeMillis();

        influxDB.write(Point.measurement(MEASUREMENT_SENSORS)
                .time(currentTime, TimeUnit.MILLISECONDS)
                .addField("id", ID)
                .addField("dataType", dataType)
                .addField("value", collectedValue)
                .build()
        );
    }

    public void insertActuatorMeasurement(IActuator actuator) {
        String ID = actuator.getID();
        String actionType = actuator.getActionType().toString();
        String dataType = actuator.getDataType().toString();
        String state = actuator.getState().toString();
        long currentTime = System.currentTimeMillis();

        influxDB.write(Point.measurement(MEASUREMENT_ACTUATORS)
                .time(currentTime, TimeUnit.MILLISECONDS)
                .addField("id", ID)
                .addField("dataType", dataType)
                .addField("actionType", actionType)
                .addField("state", state)
                .build()
        );
    }
}
