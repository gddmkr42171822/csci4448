import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.applet.*;

//imports needed?

//Suppress warning about implementing serializable interface
@SuppressWarnings("serial")


public class Controller extends JApplet implements MouseListener, MouseMotionListener{
  public static GameBoard board;
  private MainMenu mainMenu;
  private GameScene currentScene;
  private String mouseMovedCoordinates = "";
  private String mouseDraggedDebugInfo = "";
  private String mouseClickedDebugInfo = "";
  private String mousePressedDebugInfo = "";
  private String eventType = "None";
  private Checker savedChecker;
  private int originalCheckerX;
  private int originalCheckerY;
  private int originalCheckerPlayer;
  private JPanel scoreBoardPane;
  public static ScoreBoard scoreBoard;
  private Player player1;
  private Player player2;
  public static String playerColor1 = "Red";
  public static String playerColor2 = "Black";
  public static int mouseX;
  public static int mouseY;
  public static Boolean playSound = true;
  AudioClip sound;

  public void init(){

    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().setBackground(new Color(0,150,0));
    this.setSize(1000, 650);
    
    Random rand = new Random();
    player1 = new Player();
    player2 = new Player();
    setPlayerColors(player1, playerColor1);
    setPlayerColors(player2, playerColor2);
    player1.turn = rand.nextBoolean();
    player2.turn = !player1.turn;
    board = new GameBoard(player1, player2); 
    mainMenu = MainMenu.getInstance();
    scoreBoard = new ScoreBoard();
    while(!scoreBoard.isSet){ // Wait until the players enter their names
      try{Thread.sleep(1);

        }
      catch(InterruptedException e){}
    }
    if (board.player2 instanceof ComputerPlayer){
      board.player2.score = player2.score;
      board.player2.selectedColor = player2.selectedColor;
      board.player2.name = player2.name;
      board.player2.turn = player2.turn;
      board.player2.color = player2.color;
      player2 = board.player2;
    }
    currentScene = new GameScene(); // GameScene to start.
    currentScene.setPreferredSize(new Dimension(600,600));
    scoreBoard.setPreferredSize(new Dimension(300, 600));
    scoreBoardPane = new JPanel();
    scoreBoardPane.add(scoreBoard);
    this.getContentPane().add(currentScene, BorderLayout.WEST);
    this.getContentPane().add(scoreBoardPane, BorderLayout.EAST);
		addMouseListener(this); //Controller handles Mouse events.
    addMouseMotionListener(this); //Controller handles continuous Mouse events.
    if (player1.color != playerColor1)
    {
      setPlayerColors(player1, playerColor1);
      setPlayerColors(player2, playerColor2);
      board.updateCheckerColors(player1, player2);
      currentScene.repaint();
    }

    if (board.player2 instanceof ComputerPlayer){
      player1.turn = true;
      player2.turn = false;
    }

    if(player1.turn){
      scoreBoard.turn1.setText("It is your turn, " + player1.color + "!");
    }else{
      scoreBoard.turn2.setText("It is your turn, " + player2.color + "!");
    }
   try{
      sound = Applet.newAudioClip(new URL("file:plop.au"));
    }
    catch(Exception e){}
  }

  public void setPlayerColors(Player player, String color){
    switch(color){
      case("Red"): 
        player.selectedColor = Color.red;
        player.color = color;
        break;
      case("Black"):
        player.selectedColor = Color.black;
        player.color = color;
        break;
      case("White"):
        player.selectedColor = Color.white;
        player.color = color;
        break;
      case("Blue"):
        player.selectedColor = Color.blue;
        player.color = color;
        break;
      case("Green"):
        player.selectedColor = Color.green;
        player.color = color;
        break;
      default:
        player.selectedColor = Color.red;
        player.color = "Red";
    }
  }

  public Boolean kingMove(int x, int y){
    for(Checker checker : GameBoard.checkers){
      if((originalCheckerX - x) == 2 && (originalCheckerY - y) == -2){
        if(checker.x == (x + 1) && checker.y == (y - 1)){
          GameBoard.checkers.remove(checker);
          return true;
        }
      }else if((originalCheckerX - x) == 2 && (originalCheckerY - y) == 2){
          if(checker.x == (x + 1) && checker.y == (y + 1)){
            GameBoard.checkers.remove(checker);
            return true;
          }
      }else if((originalCheckerX - x) == -2 && (originalCheckerY - y) == 2){
        if(checker.x == (x - 1) && checker.y == (y + 1)){
          GameBoard.checkers.remove(checker);
          return true;
        }
      }else if((originalCheckerX - x) == -2 && (originalCheckerY - y) == -2){
        if(checker.x == (x - 1) && checker.y == (y - 1)){
          GameBoard.checkers.remove(checker);
          return true;
        }
      }
    }
    return false;
  }
  
  public Boolean validMove(int x, int y){
    for(Checker checker : GameBoard.checkers){  
      if(checker.x == x && checker.y == y){  // If there is a piece where you are moving you can't move there
        return false;
      }
    }
    if((Math.abs(originalCheckerX - x) == 2) && (Math.abs(originalCheckerY - y) == 2)){
      for(Checker checker : GameBoard.checkers){
        if(originalCheckerPlayer != checker.player){ // if the piece you are about to jump is a different color
          if(board.player1.turn){
            if(savedChecker.king){
              return kingMove(x, y);
            }
            if((originalCheckerX - x) == 2){
              if(checker.x == (x + 1) && checker.y == (y - 1)){
                GameBoard.checkers.remove(checker);
                if(y == 8 && savedChecker.king == false){
                  savedChecker.king = true;
                }
                return true;
              }
            }else if((originalCheckerX - x) == -2){
              if(checker.x == (x - 1) && checker.y == (y - 1)){
                GameBoard.checkers.remove(checker);
                if(y == 8 && savedChecker.king == false){
                  savedChecker.king = true;
                }
                return true;
              }
            }
          }else if(board.player2.turn || (board.player1.turn && savedChecker.king)){
            if(savedChecker.king){
              return kingMove(x, y);
            }
            if((originalCheckerX - x) == 2){
              if(checker.x == (x + 1) && checker.y == (y + 1)){
                GameBoard.checkers.remove(checker);
                if(y == 1 && savedChecker.king == false){
                  savedChecker.king = true;
                }
                return true;
              }
            }else if((originalCheckerX - x) == -2){
              if(checker.x == (x - 1) && checker.y == (y + 1)){
                GameBoard.checkers.remove(checker);
                if(y == 1 && savedChecker.king == false){
                  savedChecker.king = true;
                }
                return true;
              }
            }
          }
        }
      }
    }else if((Math.abs(originalCheckerX - x) == 1) && (Math.abs(originalCheckerY - y) == 1)){
      if(board.player1.turn){
        if(savedChecker.king == true){
          return true;
        }
        if(originalCheckerY - y != 1){
          if(y == 8 && savedChecker.king == false){
            savedChecker.king = true;
          }
          return true;
        }
      }else if(board.player2.turn){
        if(savedChecker.king == true){
          return true;
        }
        if(originalCheckerY - y != -1){
          if(y == 1 && savedChecker.king == false){
            savedChecker.king = true;
          }
          return true;
        }
      }
    }else if((Math.abs(originalCheckerX - x) == 0) && (Math.abs(originalCheckerY - y) == 0)){
      return true;
    }
    return false;
  }

  //Methods required to implement Mouse Listener interfaces
  public void mousePressed(MouseEvent me){
    int tileSize = GameBoard.size;
    mouseX = me.getX() / tileSize + 1;
    mouseY = me.getY() / tileSize + 1;

    //mousePressedDebugInfo = "Mouse pressed debug - mousex: " + me.getX() + " mousey: " + me.getY();
    for(Checker checker : GameBoard.checkers){
      if(board.player1.turn){
        if((checker.player == 1) && (mouseX == checker.x) && (mouseY == checker.y)){
          savedChecker = checker;
          originalCheckerX = checker.x;
          originalCheckerY = checker.y;
          originalCheckerPlayer = checker.player;
        }
      }else{
        if((checker.player == 2) && (mouseX == checker.x) && (mouseY == checker.y)){
          savedChecker = checker;
          originalCheckerX = checker.x;
          originalCheckerY = checker.y;
          originalCheckerPlayer = checker.player;
        }
      }
    }

  }

  public void mouseDragged(MouseEvent me){
    int tileSize = GameBoard.size;
    mouseX = me.getX() / tileSize + 1;
    mouseY = me.getY() / tileSize + 1;
    System.out.println("X: "+ mouseX);
    System.out.println("Y: " + mouseY);

    //mouseDraggedDebugInfo = "Mouse dragged debug - mousex: " + me.getX() + " mousey: " + me.getY();
    //Check saved coordinates to see if a players piece is underneath, and if it is continuously redraw it at the drag position
    if(this.validMove(mouseX, mouseY)){
      savedChecker.x = mouseX;
      savedChecker.y = mouseY;
      if (playSound && player2 instanceof ComputerPlayer)
        sound.play();
      currentScene.repaint();  
    }
    if(savedChecker.king == true){
      System.out.println("This checker is a king!");
    }
  }

  public void mouseReleased(MouseEvent me){
    //Reset checker
    if((originalCheckerX != savedChecker.x) || (originalCheckerY != savedChecker.y)){ // Switch the player's turn
      if(board.player1.turn){
        board.player1.turn = false;
        board.player2.turn = true;
        scoreBoard.turn1.setText("It is not your turn!");
        scoreBoard.turn2.setText("It is your turn, " + board.player2.color + "!");
        int r = attemptAiMove();
        if (r == 1){
          makeAIMove();
        }
      }else if(board.player2.turn){
        board.player1.turn = true;
        board.player2.turn = false;
        scoreBoard.turn2.setText("It is not your turn!");
        scoreBoard.turn1.setText("It is your turn, " + board.player1.color + "!");
      }
    }
    savedChecker = null;
    if (playSound)
      sound.play();
    currentScene.repaint();
  }

  public void makeAIMove(){
    int[] move = ((ComputerPlayer) board.player2).getCurrentMove();
    if (move[0] != -1){
      int oldX = move[0] + 1;
      int oldY = move[1] + 1;
      int newX = move[2] + 1;
      int newY = move[3] + 1;
      ArrayList<Checker> toRemove = new ArrayList<Checker>();
      for (Checker c: GameBoard.checkers){
        if ((c.x == oldX) && (c.y == oldY)){
          c.x  = newX;
          c.y = newY;
          if (newY == 8 || newY == 1){
            c.king = true;
          }
        }
        if (move.length > 4){
          int s = 4;
          while (s < move.length){
            if ((c.x == move[s] + 1) && (c.y == move[s+1] + 1)){
              toRemove.add(c);
            }
            s +=2; 
          }
        }
      }
      for (Checker tr : toRemove){
        GameBoard.checkers.remove(tr);
      }
      board.player1.turn = true;
      board.player2.turn = false;
      scoreBoard.turn2.setText("It is not your turn!");
      scoreBoard.turn1.setText("It is your turn, " + board.player1.color + "!");
    }
  }

  public int attemptAiMove(){
    if (board.player2 instanceof ComputerPlayer){
      ComputerPlayer temp = (ComputerPlayer) board.player2;
      temp.determineMove(GameBoard.checkers);
      return 1;
    } else{
      return -1;
    }
  }

  public void mouseMoved(MouseEvent me){} //stub

  public void mouseClicked(MouseEvent me){} //stub

  public void mouseEntered(MouseEvent me){} //stub

  public void mouseExited(MouseEvent me){} //stub
}
