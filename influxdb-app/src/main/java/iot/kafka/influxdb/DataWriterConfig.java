package iot.kafka.influxdb;

import java.util.Map;

public class DataWriterConfig {

    private static final String DEFAULT_BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String DEFAULT_DATABASE_URL = "http://localhost:8086";
    private static final String DEFAULT_DATABASE = "sensor";
    private static final String DEFAULT_TOPIC_DEVICE_DATA = "iot-device-data";

    private static final String BOOTSTRAP_SERVERS = "BOOTSTRAP_SERVERS";
    private static final String DATABASE_URL = "DATABASE_URL";
    private static final String DATABASE = "DATABASE";
    private static final String TOPIC_DEVICE_DATA = "TOPIC_DEVICE_DATA";

    private final String bootstrapServers;
    private final String databaseUrl;
    private final String database;
    private final String topicDeviceData;

    public DataWriterConfig(String bootstrapServers, String databaseUrl, String database, String topicDeviceData) {
        this.bootstrapServers = bootstrapServers;
        this.databaseUrl = databaseUrl;
        this.database = database;
        this.topicDeviceData = topicDeviceData;
    }

    /**
     * Loads data writer configuration from a related map
     *
     * @param map map from which loading configuration parameters
     * @return DataWriter configuration instance
     */
    public static DataWriterConfig fromMap(Map<String, String> map) {

        String bootstrapServers = map.getOrDefault(DataWriterConfig.BOOTSTRAP_SERVERS, DEFAULT_BOOTSTRAP_SERVERS);
        String databaseUrl = map.getOrDefault(DataWriterConfig.DATABASE_URL, DEFAULT_DATABASE_URL);
        String database = map.getOrDefault(DataWriterConfig.DATABASE, DEFAULT_DATABASE);
        String topicDeviceData = map.getOrDefault(DataWriterConfig.TOPIC_DEVICE_DATA, DEFAULT_TOPIC_DEVICE_DATA);

        return new DataWriterConfig(bootstrapServers, databaseUrl, database, topicDeviceData);
    }

    public String bootstrapServers() {
        return this.bootstrapServers;
    }

    public String databaseUrl() {
        return this.databaseUrl;
    }

    public String database() {
        return this.database;
    }

    public String topicTemperature() {
        return this.topicDeviceData;
    }

    @Override
    public String toString() {
        return "DataWriterConfig(" +
                "bootstrapServers=" + bootstrapServers +
                "databaseUrl=" + databaseUrl +
                "database=" + database +
                "topicDeviceData=" + topicDeviceData +
                ")";
    }
}
