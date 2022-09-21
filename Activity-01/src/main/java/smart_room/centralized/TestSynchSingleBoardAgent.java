package smart_room.centralized;

public class TestSynchSingleBoardAgent {
    public static void main(String[] args){
        var agent = new SynchSingleBoardAgent(args.length>0? Double.parseDouble(args[0]) : 0.5);
        agent.start();
    }
}
