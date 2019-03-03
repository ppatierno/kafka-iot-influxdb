package iot.kafka.influxdb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DataWriter extends AbstractVerticle {

    public static final Logger log = LoggerFactory.getLogger(DataWriter.class);

    private DataWriterConfig config;
    private InfluxDB influxDB;

    public DataWriter(DataWriterConfig config) {
        this.config = config;
    }

    @Override
    public void start(Future<Void> start) {
        log.info("DataWriter started");

        this.vertx.executeBlocking(
                future -> {

                    try {
                        this.influxDB = InfluxDBFactory.connect(this.config.databaseUrl());
                        Pong response = this.influxDB.ping();
                        if (response.getVersion().equalsIgnoreCase("unknown")) {
                            log.error("Error pinging server.");
                            future.fail("Error pinging server.");
                        } else {
                            consume();
                            future.complete();
                        }
                    } catch (Exception ex) {
                        log.error("Error pinging server.", ex);
                        future.fail(ex);
                    }

                }, start.completer()
        );
    }

    @Override
    public void stop(Future<Void> stop) {
        log.info("DataWriter stopped");

        stop.complete();
    }

    private void consume() {

        this.influxDB.setDatabase(this.config.database());

        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.config.bootstrapServers());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "influxdb");

        KafkaConsumer<String, String> consumer = KafkaConsumer.create(this.vertx, config);

        consumer.handler(record -> {

            JsonObject json = new JsonObject(record.value());

            Point point = Point.measurement("device-data")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("temperature", json.getInteger("temperature"))
                    .addField("humidity", json.getInteger("humidity"))
                    .build();

            this.influxDB.write(point);

        });
        consumer.subscribe(this.config.topicTemperature());

    }
}
