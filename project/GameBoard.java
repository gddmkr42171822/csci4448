import java.util.ArrayList;
import java.awt.*;
//import javax.swing.*;

//SINCE WE USE SOME STATICS HERE THIS SHOULD PROBABLY BE A SINGLETON

public class GameBoard{
		public static final int size = 75; //static makes it accessible through the class without instantiation (its used A LOT all over the place), final keeps it constant
		//NOTE: If you change this value, you have to recompile this file and wherever this is used specifically, as well as the Controller.

	public static ArrayList<Checker> checkers = new ArrayList<Checker>(24);
 	public Player player1;
  	public Player player2;

  	public GameBoard(Player player1, Player player2){
    	this.player1 = player1;
    	this.player2 = player2;

    	for(int y = 1; y <= 8; y++){
      	 	for(int x = 1; x <= 8; x++){
        	  //No pieces in rows 4 or 5
	        	if(y != 4 && y != 5){
		          	//Interesting pattern here, if x and y are both
		          	//even or odd, then there should be a piece placed
		          	if((x%2) == (y%2)){
						if(y < 4){
							//Player 1
							Checker checker = new Checker(x, y, 1);
							checker.color = this.player1.selectedColor;
							this.checkers.add(checker);
						}else{
							//Player 2
							Checker checker = new Checker(x, y, 2);
							checker.color = this.player2.selectedColor;
							this.checkers.add(checker);
						}
	          		}
	         	}
       		}
    	}
  	}

  	public void updateCheckerColors(Player player1, Player player2){
  		this.player1 = player1;
  		this.player2 = player2;
  		ArrayList<Checker> newCheckers = new ArrayList<Checker>(24);
  		for(Checker c: checkers){
  			if(c.player == 1){
  				Checker newChecker = new Checker(c.x,c.y,1);
  				newChecker.color = this.player1.selectedColor;
  				newCheckers.add(newChecker);
  			}
  			else{
  				Checker newChecker = new Checker(c.x,c.y,2);
  				newChecker.color = this.player2.selectedColor;
  				newCheckers.add(newChecker);
  			}
  		}
  		checkers.clear();
  		checkers.addAll(newCheckers);
  	}

		/*
	public void printCheckers(){
		for(Checker c : this.checkers){
        System.out.println("\nChecker: " + c.x + " " + c.y + " " + c.player);
		}
	}
	*/
}
