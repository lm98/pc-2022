package smart_room.distributed.agents;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import smart_room.distributed.PresDetectSensorSimulator;

public class PIRAgent extends AbstractVerticle {
    private final PresDetectSensorSimulator sensor;
    private final int port;
    private final String host;

    public PIRAgent(int port, String host, String id){
        this.port = port;
        this.sensor = new PresDetectSensorSimulator(id);
        this.host = host;

    }

    @Override
    public void start(){
        sensor.init();
        MqttClient client = MqttClient.create(vertx);

        client.connect(port, host, c -> {
            log("Connected");
            sensor.register(e -> {
                log("Publishing event");
                var presence = sensor.presenceDetected();
                client.publish("presence-detection",
                        Buffer.buffer(presence? "present" : "not-present"),
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
