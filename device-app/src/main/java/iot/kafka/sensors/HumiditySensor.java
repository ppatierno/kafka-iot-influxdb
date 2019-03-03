package iot.kafka.sensors;

/**
 * Interface for sensors providing humidity value
 */
public interface HumiditySensor extends Sensor {

    /**
     * Return the read humidity value
     *
     * @return  humidity value
     */
    int getHumidity();
}
