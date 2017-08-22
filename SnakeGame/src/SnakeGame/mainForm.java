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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class mainForm extends JFrame {

	public static int block=15, playHeight=600, playWidth=600;
	public static int greenFroggs=20, redFroggs=5, blueFroggs=1, snakeSize=3;
	public static JLabel labelScore;
	
	private JButton btnStart,btnPause,btnStop,btnSettings;

	public mainForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowIconified(WindowEvent arg0) {
				System.out.println("iconified ");
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
//				System.out.println("resised ");
			}
		});
				
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
				Direction dirSn = game.snake.dir;
				if (arg0.getButton()==1) {
					if (dirSn==Direction.Right)	game.snake.dir=Direction.Up;
					if (dirSn==Direction.Left)	game.snake.dir=Direction.Down;
					if (dirSn==Direction.Up)	game.snake.dir=Direction.Left;
					if (dirSn==Direction.Down)	game.snake.dir=Direction.Right;
				} else if (arg0.getButton()==3){
					if (dirSn==Direction.Right)	game.snake.dir=Direction.Down;
					if (dirSn==Direction.Left)	game.snake.dir=Direction.Up;
					if (dirSn==Direction.Up)	game.snake.dir=Direction.Right;
					if (dirSn==Direction.Down)	game.snake.dir=Direction.Left;
				}
			}
		});
	
		game.setBounds(7, 7, 580, 580);
		game.setFocusable(true);
		
		btnStart = new JButton("Start");
		btnPause = new JButton("Pause");
		btnStop = new JButton("Stop");
		btnSettings = new JButton("Settings");
		setButtonsEnabled("Init");
		
		labelScore = new JLabel("<html>Score: 0</html>");
		labelScore.setVerticalAlignment(SwingConstants.TOP);
		labelScore.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		ScrollPane s = new ScrollPane();
		s.setSize(594, 594);
		s.add(game);
				
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(s, GroupLayout.PREFERRED_SIZE, 594, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSettings, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(labelScore, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(601)
					.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addGap(10))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(601)
					.addComponent(btnPause, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(s, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPause, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(labelScore, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 394, Short.MAX_VALUE)
							.addComponent(btnSettings, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
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
