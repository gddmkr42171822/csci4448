import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class StartMenu extends Controller implements ActionListener {
	JFrame setMenu;
	MainMenu mainMenu;
	JTextField player1;
	JTextField player2;

	private static StartMenu startMenu;

	public static StartMenu getInstance(){
		if(startMenu == null){
			startMenu = new StartMenu();
		}
		startMenu.setMenu.setVisible(true);
		return startMenu;
	}

	private StartMenu(){
		setMenu = new JFrame("Start");
		setMenu.setLocation(200,200);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setOpaque(true);
		contentPane.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel title = new JLabel("Enter Player Names");
		JLabel player1_title = new JLabel("Enter Player 1's Name");
		player1 = new JTextField(10);
		player2 = new JTextField(10);
		JLabel player2_title = new JLabel("Enter Player 2's Name");
		JButton start = new JButton("Start Game");

		JPanel player1_pane = new JPanel();
		JPanel player2_pane = new JPanel();
		JPanel title_pane = new JPanel();
		JPanel start_pane = new JPanel();
		player1_pane.setLayout(new FlowLayout());
		player2_pane.setLayout(new FlowLayout());
		title_pane.setLayout(new FlowLayout());
		start_pane.setLayout(new FlowLayout());
		title_pane.add(title);
		player1_pane.add(player1_title);
		player1_pane.add(player1);
		player2_pane.add(player2_title);
		player2_pane.add(player2);
		start_pane.add(start);

		JPanel menuButtons = new JPanel();
		menuButtons.setLayout(new FlowLayout());
		JButton mainMenu = new JButton("Main Menu");
		menuButtons.add(mainMenu);

		mainMenu.setActionCommand("main");
		start.setActionCommand("start");

		start.addActionListener(this);
		mainMenu.addActionListener(this);

		contentPane.add(Box.createRigidArea(new Dimension(0,5)));
		contentPane.add(title_pane);
		contentPane.add(Box.createRigidArea(new Dimension(0,5)));
		contentPane.add(player1_pane);
		contentPane.add(Box.createRigidArea(new Dimension(0,5)));
		contentPane.add(player2_pane);
		contentPane.add(Box.createRigidArea(new Dimension(0,5)));
		contentPane.add(start_pane);
		contentPane.add(Box.createRigidArea(new Dimension(0,10)));
		contentPane.add(menuButtons);

		setMenu.add(contentPane);
		setMenu.pack();
		setMenu.setSize(300,300);
		setMenu.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae){
		if ("main".equals(ae.getActionCommand())){
			mainMenu = MainMenu.getInstance();
			setMenu.setVisible(false);
		}else if("start".equals(ae.getActionCommand())){
			board.player1.name = player1.getText();
			board.player2.name = player2.getText();
			scoreBoard.addPlayerNames(board.player1.name, board.player2.name);
			scoreBoard.isSet = true;
			setMenu.setVisible(false);
			//System.out.println(board.player1.name);
			//System.out.println(board.player2.name);
		}
	}	
}