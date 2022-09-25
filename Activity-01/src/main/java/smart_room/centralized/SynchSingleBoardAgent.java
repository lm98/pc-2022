package smart_room.centralized;

import smart_room.Event;

public class SynchSingleBoardAgent extends Thread{
    private final SinglelBoardSimulator board;
    private final double threshold;

    SynchSingleBoardAgent(double t){
        this.board = new SinglelBoardSimulator();
        board.init();
        board.register((Event ev) -> System.out.println("New event: " + ev));
        this.threshold = t;
    }

    @Override
    public void run() {
        while(true){
            //SENSE
            final var isSomeonePresent = board.presenceDetected();
            final var lum = board.getLuminosity();
            //DECIDE
            final var shouldTurnOn = isSomeonePresent && lum <= threshold;
            //ACT
            if(shouldTurnOn){
                board.on();
            } else {
                board.off();
            }
        }
    }
}
