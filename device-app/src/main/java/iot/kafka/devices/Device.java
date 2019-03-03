package iot.kafka.devices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import iot.kafka.devices.sensors.impl.DHT22;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.UUID;

public class Device extends AbstractVerticle {

    public static final Logger log = LoggerFactory.getLogger(Device.class);

    private String id;
    private DeviceConfig config;
    private KafkaProducer<String, String> producer;
    private long timerId;
    private DHT22 dht22;

    public Device(DeviceConfig config) {
        this.config = config;
    }

    @Override
    public void start(Future<Void> start) {

        this.id = "d-" + UUID.randomUUID().toString();

        log.info("Device started");
        log.info("Device ID: {}, Config: {}", this.id, this.config);

        this.dht22 = new DHT22();
        Properties dht22Config = new Properties();
        dht22Config.put(DHT22.MIN_TEMPERATURE, String.valueOf(this.config.minTemperature()));
        dht22Config.put(DHT22.MAX_TEMPERATURE, String.valueOf(this.config.maxTemperature()));
        dht22Config.put(DHT22.MIN_HUMIDITY, String.valueOf(this.config.minHumidity()));
        dht22Config.put(DHT22.MAX_HUMIDITY, String.valueOf(this.config.maxHumidity()));
        this.dht22.init(dht22Config);

        this.producer = createProducer();

        this.timerId = vertx.setPeriodic(this.config.delay(), timerId -> {

            int temperature = this.dht22.getTemperature();
            int humidity = this.dht22.getHumidity();

            JsonObject json = new JsonObject();
            json.put("temperature", temperature);
            json.put("humidity", humidity);

            KafkaProducerRecord<String, String> record =
                    KafkaProducerRecord.create(this.config.topicTemperature(), this.id, json.encode());

            producer.write(record, ar -> {
                if (ar.succeeded()) {
                    log.info("Device data: {} sent to [{}, {}, {}]", json.encode(),
                            ar.result().getTopic(), ar.result().getPartition(), ar.result().getOffset());
                } else {
                    log.error("Failed to send device data");
                }
            });
        });

        start.complete();
    }

    @Override
    public void stop(Future<Void> stop) {

        this.producer.close();
        this.vertx.cancelTimer(this.timerId);

        log.info("Device stopped");

        stop.complete();
    }

    private <K, V> KafkaProducer<K, V> createProducer() {
        Properties config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.config.bootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.ACKS_CONFIG, "1");

        KafkaProducer<K, V> producer = KafkaProducer.create(this.vertx, config);

        return producer;
    }
}
