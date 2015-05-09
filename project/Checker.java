import java.awt.*;

public class Checker{
  public boolean king;
  public int x;
  public int y;
  public int player;
  public Color color;
  //public int color;

  public Checker(int x, int y, int player){
    this.x = x;
    this.y = y;
	  this.player = player;
	  this.king = false;
  }

  @Override
  public String toString(){
  	String c = "WRONG" ;
  	if (this.player == 1){
  		c = "RED";
  	}
  	if (this.player == 2){
  		c = "BLACK";
  	}
  	return c + " At X: " + this.x + ", Y: " + this.y; 
  }
}
