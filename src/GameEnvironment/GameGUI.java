package GameEnvironment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.accessibility.Accessible;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameGUI {
	final private String GAME_LAUNCHER = "Game Launcher";
	private String[] gameList;
	private JFrame frame;
	private JPanel contentPanel;
	private JTextField playerOne, playerTwo;
	private JButton[] gameButton;
	
	//------------global color scheme----------------------------------------------------
	Color border = Color.DARK_GRAY;
	Color background = Color.LIGHT_GRAY;
	Color label = Color.WHITE;
	
	GameGUI(String[] gameList){
		System.out.println("Loading Game Launcher...");
		this.gameList = gameList;
		createFrame(this.GAME_LAUNCHER);
		createContentPanel(
				createTopPanel(new JLabel("Player One: "), playerOne = new JTextField(), new JLabel("Player Two: "), playerTwo = new JTextField()), 
				createMidPanel(createButton(this.gameList)), 
				null);
		this.frame.add(this.contentPanel);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	private void createFrame(String title) {
		this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        closingOperation();        
	}
	
	private void createContentPanel(JPanel topPanel, JPanel midPanel, JPanel botPanel) {
		this.contentPanel = new JPanel(new GridLayout(3, 1, 10, 10));
		this.contentPanel.setBorder(BorderFactory.createLineBorder(border, 2));
		this.contentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 
		this.contentPanel.setBackground(background);
		
		if (topPanel != null)
			this.contentPanel.add(topPanel, BorderLayout.NORTH);
		
		if (midPanel != null)
			this.contentPanel.add(midPanel, BorderLayout.CENTER);
		
		if (botPanel != null)
		this.contentPanel.add(botPanel);
	}
	
	private JButton[] createButton(String[] buttonLabels) {
		if (buttonLabels == null)
			return null;
		this.gameButton = new JButton[buttonLabels.length];
		
		for (int i = 0; i < buttonLabels.length; i++) 
			this.gameButton[i] = new JButton(buttonLabels[i]);
			
		return this.gameButton;
	}
	
	private JPanel createTopPanel(Accessible...components) {
		if (components == null)
			return null;
		JPanel topPanel = new JPanel(new GridLayout(1, components.length, 0, 10));
		topPanel.setBorder(BorderFactory.createLineBorder(border, 2));
		topPanel.setBackground(background);
		
		for (Accessible comp: components) {
			topPanel.add((Component) comp);
		}
		return topPanel;
	}
	
	private JPanel createMidPanel(Accessible...components) {
		if (components == null)
			return null;
		
		JPanel midPanel = new JPanel(new GridLayout(1, components.length, 0, 10));
		midPanel.setBorder(BorderFactory.createLineBorder(border, 2));
		midPanel.setBackground(background);
		
		for (Accessible comp: components) {
			midPanel.add((Component) comp);
		}
		return midPanel;
	}
	
	private JPanel createBotPanel(Accessible...components) {
	
		return null;
	}
	
	private void closingOperation() {
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.end();
                System.exit(0);
            }
        });
	}

}
