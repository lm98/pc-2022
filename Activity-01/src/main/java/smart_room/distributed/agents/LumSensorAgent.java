package smart_room.distributed.agents;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import smart_room.distributed.LuminositySensorSimulator;

public class LumSensorAgent extends AbstractVerticle {
    private final LuminositySensorSimulator sensor;
    private final int port;
    private final String host;

    public LumSensorAgent(int port, String host, String id){
        this.port = port;
        this.host = host;
        this.sensor = new LuminositySensorSimulator(id);
    }

    @Override
    public void start(){
        sensor.init();
        MqttClient client = MqttClient.create(vertx);

        client.connect(port, host, c -> {
            log("Connected");
            sensor.register(e -> {
                log("Publishing event");
                var luminosity = sensor.getLuminosity();
                client.publish(Topics.LuminositySensorTopics.TOPIC,
                        Buffer.buffer(String.valueOf(luminosity)),
                        MqttQoS.AT_LEAST_ONCE,
                        false,
                        false);
            });
        });
    }

    private void log(String msg) {
        System.out.println("[MQTT AGENT] "+msg);
    }
}
