package SnakeGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.Scrollbar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import javax.swing.SpringLayout;
import javax.swing.JSeparator;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JToggleButton;

public class mainForm extends JFrame {

	private static final long serialVersionUID = 6573850909783955564L;
	public static int block=20, playHeight=block*30, playWidth=block*40;
	public static int greenFroggs=20, redFroggs=10, blueFroggs=5, snakeSize=3;
	public static int totalAnimals =  greenFroggs + redFroggs + blueFroggs + 1;
	public static JLabel labelScore;
	public static ScrollPane scrollPaneGame;
	private static JButton btnStart;
	private static JButton btnPause;
	private static JButton btnStop;
	private static JButton btnSettings;
	public static double froggProbability=0.8;
	public static boolean notDoubleBuffered;
	
	public mainForm() {
		
		setTitle("Snake");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 650);
		setLocationRelativeTo(null);
		setFocusable(true);

		getContentPane().setFocusable(true);

		GameAction game = new GameAction();
		game.setBounds(0, 0, playWidth, playHeight);
		game.setFocusable(true);
		
		btnStart = new JButton("Start");
		btnPause = new JButton("Pause");
		btnStop = new JButton("Stop");
		btnSettings = new JButton("Settings");
		setButtonsEnabled("Init");
		
		labelScore = new JLabel("<html>Score: 0</html>");
		labelScore.setVerticalAlignment(SwingConstants.TOP);
		labelScore.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		scrollPaneGame = new ScrollPane();
		scrollPaneGame.setSize(600, 600);
		scrollPaneGame.add(game);
		
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, labelScore, 14, SpringLayout.SOUTH, btnStop);
		springLayout.putConstraint(SpringLayout.WEST, labelScore, -96, SpringLayout.EAST, btnStart);
		springLayout.putConstraint(SpringLayout.SOUTH, labelScore, 56, SpringLayout.SOUTH, btnStop);
		springLayout.putConstraint(SpringLayout.EAST, labelScore, 0, SpringLayout.EAST, btnStart);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPaneGame, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPaneGame, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneGame, -15, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPaneGame, -37, SpringLayout.WEST, btnStart);
		springLayout.putConstraint(SpringLayout.WEST, btnSettings, -117, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnStop, 6, SpringLayout.SOUTH, btnPause);
		springLayout.putConstraint(SpringLayout.WEST, btnStop, -117, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnPause, -117, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnSettings, -21, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnStop, -21, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnPause, 6, SpringLayout.SOUTH, btnStart);
		springLayout.putConstraint(SpringLayout.EAST, btnPause, -21, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnStart, -117, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnSettings, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnStart, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnStart, -21, SpringLayout.EAST, getContentPane());
		
		getContentPane().setLayout(springLayout);
		getContentPane().add(btnStart);
		getContentPane().add(btnPause);
		getContentPane().add(btnStop);
		
		getContentPane().add(btnSettings);
		
		getContentPane().add(labelScore);
		getContentPane().add(scrollPaneGame);
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.Start();
				setButtonsEnabled("Start");
			}
		});
		
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.Pause();
				setButtonsEnabled("Pause");
			}
		});
		
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.Stop();
				setButtonsEnabled("Stop");
			}
		});
		
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				settingsForm setF = new settingsForm();
				setF.setVisible(true);
			}
		});
	
	}
	
		
	public static void setButtonsEnabled(String mode){
		
		if (mode == "Init") {
			btnPause.setEnabled(false);
			btnStop.setEnabled(false);
		} else if (mode == "Start") {
			btnStart.setEnabled(false);
			btnPause.setEnabled(true);
			btnStop.setEnabled(true);
			btnSettings.setEnabled(false);
		} else if (mode == "Stop") {
			btnStart.setEnabled(true);
			btnPause.setEnabled(false);
			btnStop.setEnabled(false);
			btnSettings.setEnabled(true);
		} else if (mode == "Pause") {
			btnStart.setEnabled(true);
			btnPause.setEnabled(false);
			btnStop.setEnabled(true);
		} 
	}

}
