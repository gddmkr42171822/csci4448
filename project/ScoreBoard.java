import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ScoreBoard extends JApplet{

	public Boolean isSet;
	JLabel turn2;
	JLabel turn1;

	public ScoreBoard(){
		isSet = false;
	}

	public void addPlayerNames(String player_1, String player_2){
		JPanel scoreBoardPane = new JPanel(new BorderLayout());
		JPanel player1 = new JPanel();
		JPanel player2 = new JPanel();
	    player1.setLayout(new BoxLayout(player1, BoxLayout.PAGE_AXIS));
	    player1.setAlignmentX(CENTER_ALIGNMENT);
	    player2.setLayout(new BoxLayout(player2, BoxLayout.PAGE_AXIS));
	    player2.setAlignmentX(CENTER_ALIGNMENT);
	    JPanel titlePane = new JPanel();
	    JLabel player1_name = new JLabel(player_1);
	    player1_name.setAlignmentX(CENTER_ALIGNMENT);
	    JLabel player2_name = new JLabel(player_2);
	    player2_name.setAlignmentX(CENTER_ALIGNMENT);
	    JLabel title = new JLabel("Score Board");
	    turn1 = new JLabel("");
	    turn1.setAlignmentX(CENTER_ALIGNMENT);
	    turn2 = new JLabel("");
	    turn2.setAlignmentX(CENTER_ALIGNMENT);
	    turn1.setText("It is not your turn!");
	    turn2.setText("It is not your turn!");

	    title.setFont(new Font("Serif", Font.PLAIN, 20));

		player1.add(Box.createRigidArea(new Dimension(5,5)));
	    player1.add(player1_name);
	    player1.add(Box.createRigidArea(new Dimension(5,5)));
	    player1.add(turn1);
	    player2.add(Box.createRigidArea(new Dimension(5,5)));
	    player2.add(player2_name);
	    player1.add(Box.createRigidArea(new Dimension(5,5)));
	    player2.add(turn2);
	    titlePane.add(title);
	    
	    scoreBoardPane.add(titlePane, BorderLayout.NORTH);
	    scoreBoardPane.add(player1, BorderLayout.CENTER);
	    scoreBoardPane.add(player2, BorderLayout.SOUTH);
	    this.getContentPane().add(scoreBoardPane);
	}

}