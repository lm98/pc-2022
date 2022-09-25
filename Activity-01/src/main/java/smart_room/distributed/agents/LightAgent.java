package smart_room.distributed.agents;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import smart_room.distributed.LightDeviceSimulator;

import java.util.Objects;

public class LightAgent extends AbstractVerticle {
    private final LightDeviceSimulator actuator;
    private final int port;
    private final String host;
    private static final int QOS = 2;
    private static final double THRESHOLD = 0;
    private static boolean isPresent = false;
    private static double lumLevel = 0;

    public LightAgent(int port, String host, String id){
        this.port = port;
        this.host = host;
        this.actuator = new LightDeviceSimulator(id);
    }

    @Override
    public void start(){
        actuator.init();
        MqttClient client = MqttClient.create(vertx);

        client.connect(port, host, c -> {
            log("Connected");
            log("Subscribing to events...");
            client.publishHandler(m -> {
                log("Received event from topic " + m.topicName());
                isPresent = Objects.equals(m.payload().toString(), Topics.PIRTopics.IS_PRESENT);
                setLight();
            }).subscribe(Topics.PIRTopics.TOPIC, QOS);

            client.publishHandler(m -> {
                log("Received event from topic " + m.topicName());
                lumLevel = Double.parseDouble(m.payload().toString());
                setLight();
            }).subscribe(Topics.LuminositySensorTopics.TOPIC, QOS);
        });
    }

    private void setLight(){
        log(isPresent + " " + lumLevel);
        if(isPresent && (lumLevel <= THRESHOLD)){
            actuator.on();
        } else {
            actuator.off();
        }
    }

    private void log(String msg) {
        System.out.println("[MQTT AGENT] "+msg);
    }
}
