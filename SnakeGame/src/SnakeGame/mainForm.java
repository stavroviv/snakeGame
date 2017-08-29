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

public class mainForm extends JFrame {

	private static final long serialVersionUID = 6573850909783955564L;
	public static int block=15, playHeight=600, playWidth=600;
	public static int greenFroggs=20, redFroggs=5, blueFroggs=1, snakeSize=15;
	public static int totalAnimals =  greenFroggs + redFroggs + blueFroggs + 1;
	public static JLabel labelScore;
	public static ScrollPane scrollPaneGame;
	private JButton btnStart,btnPause,btnStop,btnSettings;
	public static double froggProbability=0.8;
	
	public mainForm() {
		
		setTitle("Snake");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 654);
		setLocationRelativeTo(null);
		setFocusable(true);

		getContentPane().setFocusable(true);

		GameAction game = new GameAction();
		game.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				Direction dirSn = GameAction.snake.dir;
				if (arg0.getButton()==1) {
					if (dirSn==Direction.Right)	GameAction.snake.dir=Direction.Up;
					if (dirSn==Direction.Left)	GameAction.snake.dir=Direction.Down;
					if (dirSn==Direction.Up)	GameAction.snake.dir=Direction.Left;
					if (dirSn==Direction.Down)	GameAction.snake.dir=Direction.Right;
				} else if (arg0.getButton()==3){
					if (dirSn==Direction.Right)	GameAction.snake.dir=Direction.Down;
					if (dirSn==Direction.Left)	GameAction.snake.dir=Direction.Up;
					if (dirSn==Direction.Up)	GameAction.snake.dir=Direction.Right;
					if (dirSn==Direction.Down)	GameAction.snake.dir=Direction.Left;
				}
			}
		});
	
		game.setBounds(0, 0, 600, 600);
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
		scrollPaneGame.setSize(300, 300);
		scrollPaneGame.add(game);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(602)
							.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(602)
							.addComponent(btnPause, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(601)
							.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(604)
							.addComponent(labelScore, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(601)
							.addComponent(btnSettings, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPaneGame, GroupLayout.PREFERRED_SIZE, 593, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(btnPause, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(labelScore, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addGap(394)
					.addComponent(btnSettings, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
				.addComponent(scrollPaneGame, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
		);
		getContentPane().setLayout(groupLayout);
		
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
				GameAction.Stop();
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
		
	public void setButtonsEnabled(String mode){
		
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
