package quizcardbuilder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class QuizCardBuilder {
	private JFrame frame;
	private JTextArea question;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	
	public static void main(String[] args) {
		new QuizCardBuilder().go();
	}
	
	public void go() { // Creating Gui
		frame = new JFrame("Quiz Card Builder");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		cardList = new ArrayList<QuizCard>();
		
		// Creating Question TextBox
		question = new JTextArea(6, 20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		Font bigFont = new Font("sanserif", Font.BOLD, 26);
		question.setFont(bigFont);
		
		JScrollPane qScroller = new JScrollPane(question);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Creating Answer TextBox
		answer = new JTextArea(6, 20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);
		
		JScrollPane aScroller = new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Creating labels and button
		JLabel qLabel = new JLabel("Question");
		JLabel aLabel = new JLabel("Answer");
		JButton next = new JButton("Next Card");
		next.addActionListener(new NextButtonListener());
		
		// Adding components to the frame
		mainPanel.add(qLabel);
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller);
		mainPanel.add(next);
		
		// Creating menu bar for the frame
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(new NewMenuListener());
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new SaveMenuListener());
		
		fileMenu.add(newMenuItem); // Adding "new" to file menu
		fileMenu.add(saveMenuItem); // Adding "save" to file menu
		menuBar.add(fileMenu); // Adding "File" to menu bar
		frame.setJMenuBar(menuBar);
		
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(550, 600);
		frame.setVisible(true);
	}
	
	class NextButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardList.add(card);
			clearCard();
		}
	}
	
	class SaveMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardList.add(card);
			
			// Let the user choose a file
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.showSaveDialog(frame);
				saveFile(chooser.getSelectedFile());
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			clearCard();
		}
	}
	
	class NewMenuListener implements ActionListener{ // New menu event handler
		public void actionPerformed(ActionEvent ev) {
			clearCard();
			cardList.clear();
		}
	}
	
	private void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus(true);
	}
	
	private void saveFile(File f) {
		// Write question and answer to the selected file
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			for(QuizCard card : cardList) {
				writer.write(card.getQuestion() + "/");
				writer.write(card.getAnswer() + "\n");
			}
			writer.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
