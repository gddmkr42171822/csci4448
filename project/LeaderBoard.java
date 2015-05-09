import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class LeaderBoard extends JApplet implements ActionListener{
  String docRoot = new String("https://simplejavacheckersapplet.herokuapp.com/");
	JFrame frame;
	JTextArea lArea;
  JLabel nameLabel;
  JTextField nameInput;
  JButton submitButton;
  JButton menuButton;
  JPanel nameSubmitPane;
	JScrollPane lBoardScroll;
  MainMenu mainMenu;

	public LeaderBoard(){
    nameForm();
    addListeners();
    createLeaderBoard();
    showLeaderBoard();		
	}

  /* Create the leaderboard frame and add components to it */
  public void showLeaderBoard(){
    frame = new JFrame("LeaderBoard");
    frame.setLayout(new BorderLayout());
    frame.add(nameSubmitPane, BorderLayout.SOUTH);
    frame.add(lBoardScroll, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
    readFile();
  }

  /* Create the form for the submission of a name to the leaderboard */
  public void nameForm(){
    nameLabel = new JLabel("Submit name to leaderboard:");
    nameInput = new JTextField(10);
    submitButton = new JButton("Submit");
    menuButton = new JButton("Main Menu");
    nameSubmitPane = new JPanel();
    nameSubmitPane.add(nameLabel);
    nameSubmitPane.add(nameInput);
    nameSubmitPane.add(submitButton);
    nameSubmitPane.add(menuButton);
  }

  /* Create the text area for the leaderboard */
  public void createLeaderBoard(){
    lArea = new JTextArea(10,10);
    lArea.setEditable(false);
    lBoardScroll = new JScrollPane(lArea);
  }


  /* Detect the submission of a name */
  public void actionPerformed(ActionEvent ae){
    Object obj = ae.getSource();
    if(obj == submitButton){
        writeFile(nameInput.getText());
        readFile();
    }else if(obj == menuButton){
      mainMenu = MainMenu.getInstance();
      frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
  }

  /* Handle events in the frame */
  public void addListeners(){
    submitButton.addActionListener(this);
    menuButton.addActionListener(this);
  }

  /* Read from the leaderboard.txt and put in the textarea */
  public void readFile(){
    lArea.setText(null);
    String line; 
    StringBuffer strBuff; 
    URL url = null;
    try{
      url = new URL(docRoot + "leaderboard.txt");
    }
    catch(MalformedURLException e) {}
    try{
      InputStream in = url.openStream();
      BufferedReader bf = new BufferedReader(new InputStreamReader(in));
      while((line = bf.readLine()) != null){
        lArea.append(line + "\n");
      }
    }
    catch(IOException e){
      e.printStackTrace();
    }
 	}

  /* Write a string to leadership.txt */
  public void writeFile(String text){
    try{
      URL url = new URL(docRoot + "leaderboard.php?name=" + URLEncoder.encode(text, "UTF-8"));
      //System.out.println(url.getPath());
      URLConnection urlConn = url.openConnection();
      urlConn.getContent();
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}