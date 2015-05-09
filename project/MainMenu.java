import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class MainMenu extends Controller implements ActionListener{
	SettingsMenu settingsMenu;
	StartMenu startMenu;
	OnePlayerMenu oneStarter;
	LeaderBoard lboard;
	JFrame menu;
	JRadioButton singlePlayer;
	JRadioButton doublePlayer;

	private static MainMenu mainMenu;
	public static MainMenu getInstance(){
		if(mainMenu == null){
			mainMenu = new MainMenu();
		}
		mainMenu.menu.setVisible(true);
		return mainMenu;
	}

	private MainMenu(){
		menu = new JFrame("Menu");
		menu.setLocation(200, 200);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setOpaque(true);
		contentPane.setAlignmentY(Component.CENTER_ALIGNMENT);

		JButton start = new JButton("Start");
		start.setActionCommand("start");
		JButton loadGame = new JButton("Load Game");
		loadGame.setActionCommand("load");
		JButton leaderBoard = new JButton("Leaderboard");
		leaderBoard.setActionCommand("leader");
		JButton settings = new JButton("Settings");
		settings.setActionCommand("settings");
		singlePlayer = new JRadioButton("1 Player");
		//singlePlayer.setSelected(true);
		doublePlayer = new JRadioButton("2 Player");
		doublePlayer.setSelected(true);

		ButtonGroup group = new ButtonGroup();
		group.add(singlePlayer);
		group.add(doublePlayer);

		start.addActionListener(this);
		singlePlayer.addActionListener(this);
		doublePlayer.addActionListener(this);
		loadGame.addActionListener(this);
		leaderBoard.addActionListener(this);
		settings.addActionListener(this);

		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		singlePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		doublePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		leaderBoard.setAlignmentX(Component.CENTER_ALIGNMENT);
		settings.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		contentPane.add(Box.createRigidArea(new Dimension(0,15)));
		contentPane.add(start);
		contentPane.add(Box.createRigidArea(new Dimension(0,5)));
		contentPane.add(singlePlayer);
		contentPane.add(doublePlayer);
		contentPane.add(Box.createRigidArea(new Dimension(0,5)));
		contentPane.add(loadGame);
		contentPane.add(Box.createRigidArea(new Dimension(0,5)));
		contentPane.add(leaderBoard);
		contentPane.add(Box.createRigidArea(new Dimension(0,5)));
		contentPane.add(settings);
		contentPane.add(Box.createRigidArea(new Dimension(0,10)));

		menu.add(contentPane);
		menu.pack();
		menu.setSize(250, 250);
		menu.setVisible(true);	
	}	
	/* Detect button presses */
	public void actionPerformed(ActionEvent ae){
		if("leader".equals(ae.getActionCommand())){
			//menu.setVisible(false);
			lboard = new LeaderBoard();
		}
		else if ("settings".equals(ae.getActionCommand())){
			settingsMenu = SettingsMenu.getInstance();
			menu.setVisible(false);
		}else if("start".equals(ae.getActionCommand())){
			if(doublePlayer.isSelected()){
				startMenu = StartMenu.getInstance();
				menu.setVisible(false);
			}
			if(singlePlayer.isSelected()){
				oneStarter = OnePlayerMenu.getInstance();
				menu.setVisible(false);
			}
		}
	}
}