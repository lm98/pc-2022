package smart_room.distributed.agents;

public class Topics {
    public static final String SMART_ROOM_TOPIC = "smart-room";
    public static final class PIRTopics {
        public static final String TOPIC = "presence-detection";
        public static final String IS_PRESENT = "present";
        public static final String IS_NOT_PRESENT = "not-present";
    }

    public static final class LuminositySensorTopics {
        public static final String TOPIC = "lum-change-detection";
    }
}
