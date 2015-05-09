import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class SettingsMenu extends Controller implements ActionListener {
	JFrame setMenu;
	MainMenu mainMenu;
	JComboBox styles;
	JRadioButton on;
	JRadioButton off;

	private static SettingsMenu settingsMenu;

	public static SettingsMenu getInstance(){
		if(settingsMenu == null){
			settingsMenu = new SettingsMenu();
		}
		settingsMenu.setMenu.setVisible(true);
		return settingsMenu;
	}

	private SettingsMenu(){
		setMenu = new JFrame("Settings");
		setMenu.setLocation(200,200);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setOpaque(true);
		contentPane.setAlignmentY(Component.CENTER_ALIGNMENT);

		JLabel sound = new JLabel("Sound");
		JPanel onoff = new JPanel();
		onoff.setLayout(new FlowLayout());

		on = new JRadioButton("On");
		on.setSelected(true);
		off = new JRadioButton("Off");

		ButtonGroup group = new ButtonGroup();
		group.add(on);
		group.add(off);

		onoff.add(on);
		onoff.add(off);

		JLabel visualStyles = new JLabel("Visual Styles");
		String[] styleStrings = { "Red/Black", "White/Black", "Blue/Red", "Blue/Green" };
		styles = new JComboBox<String>(styleStrings);
		styles.setSelectedIndex(0);
		styles.setMaximumSize(new Dimension(200,15));

		JPanel menuButtons = new JPanel();
		menuButtons.setLayout(new FlowLayout());

		JButton save = new JButton("Save");
		save.setActionCommand("save");
		JButton mainMenu = new JButton("Main Menu");
		mainMenu.setActionCommand("main");
		menuButtons.add(save);
		menuButtons.add(mainMenu);

		on.addActionListener(this);
		off.addActionListener(this);
		styles.addActionListener(this);
		save.addActionListener(this);
		mainMenu.addActionListener(this);

		sound.setAlignmentX(Component.CENTER_ALIGNMENT);
		onoff.setAlignmentX(Component.CENTER_ALIGNMENT);
		visualStyles.setAlignmentX(Component.CENTER_ALIGNMENT);
		styles.setAlignmentX(Component.CENTER_ALIGNMENT);
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

		contentPane.add(Box.createRigidArea(new Dimension(0,10)));
		contentPane.add(sound);
		contentPane.add(Box.createRigidArea(new Dimension(0,5)));
		contentPane.add(onoff);
		contentPane.add(visualStyles);
		contentPane.add(Box.createRigidArea(new Dimension(0,5)));
		contentPane.add(styles);
		contentPane.add(Box.createRigidArea(new Dimension(0,5)));
		contentPane.add(menuButtons);
		contentPane.add(Box.createRigidArea(new Dimension(0,10)));

		setMenu.add(contentPane);
		setMenu.pack();
		setMenu.setSize(250,250);
		setMenu.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae){
		if ("main".equals(ae.getActionCommand())){
			mainMenu = MainMenu.getInstance();
			setMenu.setVisible(false);
		}
		else if ("save".equals(ae.getActionCommand())){
			String selectedStyle = (String)styles.getSelectedItem();
			String[] playerColors = selectedStyle.split("/");
			Controller.playerColor1 = playerColors[0];
			Controller.playerColor2 = playerColors[1];
			if (on.isSelected())
				Controller.playSound = true;
			else
				Controller.playSound = false;
		}
	}	
}