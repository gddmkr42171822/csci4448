import java.util.*;

public class ComputerPlayer extends Player{
  private AI brain;
  private int[] currentMove;

  public ComputerPlayer(){
  	brain = new AI(2,1);
  }

  public void determineMove(List<Checker> checkers){
  	System.out.println("I'm Thinking....");
  	currentMove = brain.determineMove(checkers);
  	System.out.println("I Just Thought of a Move");
  }

  public int[] getCurrentMove(){
  	return currentMove;
  }
}
