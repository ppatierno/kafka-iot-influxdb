package iot.kafka.devices;

import java.util.Map;

public class DeviceConfig {

    private static final String DEFAULT_BOOTSTRAP_SERVERS = "localhost:9092";
    private static final int DEFAULT_DELAY = 5000;
    private static final int DEFAULT_MIN_TEMPERATURE = 20;
    private static final int DEFAULT_MAX_TEMPERATURE = 25;
    private static final int DEFAULT_MIN_HUMIDITY = 50;
    private static final int DEFAULT_MAX_HUMIDITY = 55;
    private static final String DEFAULT_TOPIC_DEVICE_DATA = "iot-device-data";

    private static final String BOOTSTRAP_SERVERS = "BOOTSTRAP_SERVERS";
    private static final String DELAY = "DELAY";
    private static final String MIN_TEMPERATURE = "MIN_TEMPERATURE";
    private static final String MAX_TEMPERATURE = "MAX_TEMPERATURE";
    private static final String MIN_HUMIDITY = "MIN_HUMIDITY";
    private static final String MAX_HUMIDITY = "MAX_HUMIDITY";
    private static final String TOPIC_DEVICE_DATA = "TOPIC_DEVICE_DATA";
    private static final String TRUSTSTORE_PATH = "TRUSTSTORE_PATH";
    private static final String TRUSTSTORE_PASSWORD = "TRUSTSTORE_PASSWORD";
    private static final String KEYSTORE_PATH = "KEYSTORE_PATH";
    private static final String KEYSTORE_PASSWORD = "KEYSTORE_PASSWORD";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";

    private final String bootstrapServers;
    private final long delay;
    private final int minTemperature;
    private final int maxTemperature;
    private final int minHumidity;
    private final int maxHumidity;
    private final String topicDeviceData;
    private final String trustStorePassword;
    private final String trustStorePath;
    private final String keyStorePassword;
    private final String keyStorePath;
    private final String username;
    private final String password;

    public DeviceConfig(String bootstrapServers, long delay,
                        int minTemperature, int maxTemperature,
                        int minHumidity, int maxHumidity,
                        String topicDeviceData,
                        String trustStorePath, String trustStorePassword,
                        String keyStorePath, String keyStorePassword,
                        String username, String password) {
        this.bootstrapServers = bootstrapServers;
        this.delay = delay;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
        this.topicDeviceData = topicDeviceData;
        this.trustStorePath = trustStorePath;
        this.trustStorePassword = trustStorePassword;
        this.keyStorePath = keyStorePath;
        this.keyStorePassword = keyStorePassword;
        this.username = username;
        this.password = password;
    }

    /**
     * Loads device configuration from a related map
     *
     * @param map map from which loading configuration parameters
     * @return Device configuration instance
     */
    public static DeviceConfig fromMap(Map<String, String> map) {

        String bootstrapServers = map.getOrDefault(DeviceConfig.BOOTSTRAP_SERVERS, DEFAULT_BOOTSTRAP_SERVERS);
        long delay = Long.parseLong(map.getOrDefault(DeviceConfig.DELAY, String.valueOf(DEFAULT_DELAY)));
        int minTemperature = Integer.parseInt(map.getOrDefault(DeviceConfig.MIN_TEMPERATURE, String.valueOf(DEFAULT_MIN_TEMPERATURE)));
        int maxTemperature = Integer.parseInt(map.getOrDefault(DeviceConfig.MAX_TEMPERATURE, String.valueOf(DEFAULT_MAX_TEMPERATURE)));
        int minHumidity = Integer.parseInt(map.getOrDefault(DeviceConfig.MIN_HUMIDITY, String.valueOf(DEFAULT_MIN_HUMIDITY)));
        int maxHumidity = Integer.parseInt(map.getOrDefault(DeviceConfig.MAX_HUMIDITY, String.valueOf(DEFAULT_MAX_HUMIDITY)));
        String topicDeviceData = map.getOrDefault(DeviceConfig.TOPIC_DEVICE_DATA, DEFAULT_TOPIC_DEVICE_DATA);
        String trustStorePath = map.getOrDefault(DeviceConfig.TRUSTSTORE_PATH, null);
        String trustStorePassword = map.getOrDefault(DeviceConfig.TRUSTSTORE_PASSWORD, null);
        String keyStorePath = map.getOrDefault(DeviceConfig.KEYSTORE_PATH, null);
        String keyStorePassword = map.getOrDefault(DeviceConfig.KEYSTORE_PASSWORD, null);
        String username = map.getOrDefault(DeviceConfig.USERNAME, null);
        String password = map.getOrDefault(DeviceConfig.PASSWORD, null);

        return new DeviceConfig(bootstrapServers, delay, minTemperature, maxTemperature, minHumidity, maxHumidity, topicDeviceData,
                                trustStorePath, trustStorePassword, keyStorePath, keyStorePassword, username, password);
    }

    public String bootstrapServers() {
        return this.bootstrapServers;
    }

    public long delay() {
        return this.delay;
    }

    public int minTemperature() {
        return this.minTemperature;
    }

    public int maxTemperature() {
        return this.maxTemperature;
    }

    public int minHumidity() {
        return this.minHumidity;
    }

    public int maxHumidity() {
        return this.maxHumidity;
    }

    public String topicTemperature() {
        return this.topicDeviceData;
    }

    public String trustStorePath() {
        return this.trustStorePath;
    }

    public String trustStorePassword() {
        return this.trustStorePassword;
    }

    public String keyStorePath() {
        return this.keyStorePath;
    }

    public String keyStorePassword() {
        return this.keyStorePassword;
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    @Override
    public String toString() {
        return "DeviceConfig(" +
                "bootstrapServers=" + bootstrapServers +
                ",delay=" + delay +
                ",minTemperature=" + minTemperature +
                ",maxTemperature=" + maxTemperature +
                ",minHumidity=" + minHumidity +
                ",maxHumidity=" + maxHumidity +
                ",topicDeviceData=" + topicDeviceData +
                ",trustStorePath=" + trustStorePath +
                ",trustStorePassword=" + trustStorePassword +
                ",keyStorePath=" + keyStorePath +
                ",keyStorePassword=" + keyStorePassword +
                ",username=" + username + 
                ",password=" + password +
                ")";
    }
}
