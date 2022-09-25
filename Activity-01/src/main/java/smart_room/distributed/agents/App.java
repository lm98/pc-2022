package smart_room.distributed.agents;

import io.vertx.core.Vertx;

public class App {
    private static final int PORT = 1883;
    private static final String HOST_NAME = "broker.mqtt-dashboard.com";

    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();
        PIRAgent pirAgent = new PIRAgent(PORT, HOST_NAME, "MyPir");
        LumSensorAgent lumAgent = new LumSensorAgent(PORT, HOST_NAME, "MyLum");
        LightAgent lightAgent = new LightAgent(PORT, HOST_NAME, "MyLight");
        vertx.deployVerticle(pirAgent);
        vertx.deployVerticle(lumAgent);
        vertx.deployVerticle(lightAgent);
    }
}
