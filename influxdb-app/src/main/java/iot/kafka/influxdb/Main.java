package iot.kafka.influxdb;

import io.vertx.core.Vertx;

public class Main {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        DataWriterConfig config = DataWriterConfig.fromMap(System.getenv());
        DataWriter dataWriter = new DataWriter(config);

        vertx.deployVerticle(dataWriter, ar -> {
            if (!ar.succeeded()) {
                System.exit(1);
            }
        });
    }
}
