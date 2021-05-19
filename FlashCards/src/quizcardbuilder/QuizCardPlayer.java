package quizcardbuilder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class QuizCardPlayer {
	JFrame frame;
	JTextArea display;
	JButton button;
	QuizCard currentCard;
	ArrayList<QuizCard> cardList;
	int currentIndex;
	boolean isShowAnswer;
	
	public static void main(String[] args) {
		new QuizCardPlayer().go();
	}

	public void go() {
		frame = new JFrame("Quiz Card Player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		
		// Creating a display text area
		display = new JTextArea(6, 20);
		display.setLineWrap(true);
		display.setWrapStyleWord(true);
		display.setEditable(false); // Display user from editing the text
		Font bigFont = new Font("sanserif", Font.BOLD, 26);
		display.setFont(bigFont);
		
		JScrollPane scroller = new JScrollPane(display);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		button = new JButton("Show Answer");
		button.addActionListener(new ButtonListener());
		cardList = new ArrayList<QuizCard>();
		
		// Adding components to main panel
		mainPanel.add(scroller);
		mainPanel.add(button);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		
		// Adding menubar to frame
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load card set");
		loadMenuItem.addActionListener(new LoadMenuListener());
		
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		
		frame.setSize(550, 400);
		frame.setVisible(true);
	}
	
	class LoadMenuListener implements ActionListener{ // Load card set event handler
		public void actionPerformed(ActionEvent ev) {
			clearCard();
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(frame);
				readFile(chooser.getSelectedFile());
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	class ButtonListener implements ActionListener{ // button event handler
		public void actionPerformed(ActionEvent ev) {
			if(isShowAnswer) { // if the user already see the question
				display.append("\n" + currentCard.getAnswer());
				button.setText("Next Question");
				isShowAnswer = false;
				currentIndex++;
			}
			else { // the user hasn't see the question
				if(currentIndex < cardList.size()) {
					showCard();
				}
				else {
					display.setText("No more card to read");
					button.setEnabled(false);
				}
			}
		}
	}
	
	private void readFile(File f) { // Read all QuizCard and add them one by one
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = null;
			while((line = reader.readLine()) != null) {
				addCard(line);
			}
			reader.close();
			showCard();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Can't read from file");
		}
	}
	
	private void addCard(String line) {
		String[] t = line.split("/");
		QuizCard c = new QuizCard(t[0], t[1]);
		cardList.add(c);
	}
	
	private void showCard() {
		currentCard = cardList.get(currentIndex);
		display.setText(currentCard.getQuestion());
		isShowAnswer = true;
	}
	
	private void clearCard() {
		button.setEnabled(true);
		currentIndex = 0;
		cardList.clear();
	}
}
