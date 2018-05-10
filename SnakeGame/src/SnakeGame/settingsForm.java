package SnakeGame;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


public class settingsForm extends JDialog {

	private static final long serialVersionUID = 3770120313513569298L;
	private JPanel contentPane;
	private JTextField height,width,bloskSize,snakeSize,snakeDelay;
	private JTextField greenFroggs, redFroggs,blueFroggs;
	private JTextField froggProb;
	private JCheckBox doubleBufferedCheck;
	private JCheckBox printImages;
	
	public settingsForm() {
		
		setModal(true);
		setResizable(false);
		setTitle("Settings");
		setBounds(100, 100, 400, 220);
		setLocationRelativeTo(null);
		setFocusable(true);
				
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Prob.");
		lblNewLabel_3.setBounds(151, 113, 40, 14);
		contentPane.add(lblNewLabel_3);
		
		froggProb = new JTextField();
		froggProb.setBounds(196, 110, 40, 20);
		froggProb.setText("" + GlobalVars.froggProbability);
		contentPane.add(froggProb);
		froggProb.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Height");
		lblNewLabel.setBounds(19, 35, 51, 20);
		
		contentPane.add(lblNewLabel);
		
		height = new JTextField();
		height.setBounds(80, 35, 40, 20);
		height.setColumns(10);
		height.setText("" + GlobalVars.Height);
		contentPane.add(height);
		
		JLabel lblWeight = new JLabel("Width");
		lblWeight.setBounds(19, 60, 51, 20);
		contentPane.add(lblWeight);
		
		width = new JTextField();
		width.setBounds(80, 60, 40, 20);
		width.setColumns(10);
		width.setText("" + GlobalVars.Width);
		contentPane.add(width);
		
		JLabel lblBlockSize = new JLabel("Block size");
		lblBlockSize.setBounds(19, 83, 63, 24);
		contentPane.add(lblBlockSize);
		
		JLabel label_2 = new JLabel("");
		label_2.setBounds(92, 60, 227, 0);
		contentPane.add(label_2);
		
		JLabel label_4 = new JLabel("");
		label_4.setBounds(92, 88, 227, 0);
		contentPane.add(label_4);
		
		bloskSize = new JTextField();
		bloskSize.setBounds(80, 85, 40, 20);
		bloskSize.setColumns(10);
		bloskSize.setText("" + GlobalVars.block);
		contentPane.add(bloskSize);
				
		snakeSize = new JTextField();
		snakeSize.setBounds(317, 35, 36, 20);
		contentPane.add(snakeSize);
		snakeSize.setText("" + GlobalVars.snakeSize);
		snakeSize.setColumns(10);
		
		greenFroggs = new JTextField();
		greenFroggs.setBounds(196, 35, 40, 20);
		greenFroggs.setText("" + GlobalVars.greenFroggs);
		contentPane.add(greenFroggs);
		greenFroggs.setColumns(10);
		
		redFroggs = new JTextField();
		redFroggs.setBounds(196, 60, 40, 20);
		redFroggs.setText("" + GlobalVars.redFroggs);
		contentPane.add(redFroggs);
		redFroggs.setColumns(10);
		
		blueFroggs = new JTextField();
		blueFroggs.setBounds(196, 85, 40, 20);
		blueFroggs.setText("" + GlobalVars.blueFroggs);
		contentPane.add(blueFroggs);
		blueFroggs.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Green");
		lblNewLabel_2.setLabelFor(greenFroggs);
		lblNewLabel_2.setBounds(151, 38, 40, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblRed = new JLabel("Red");
		lblRed.setBounds(151, 63, 32, 14);
		contentPane.add(lblRed);
		
		JLabel lblBlue = new JLabel("Blue");
		lblBlue.setBounds(151, 88, 38, 14);
		contentPane.add(lblBlue);
		
		JLabel lblSnakeSize = new JLabel("<html>Size</html>");
		lblSnakeSize.setBounds(270, 38, 37, 14);
		contentPane.add(lblSnakeSize);
		
		JLabel label = new JLabel("");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Game field", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		label.setBounds(10, 10, 125, 134);
		contentPane.add(label);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setBorder(new TitledBorder(null, "Froggs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblNewLabel_1.setBounds(140, 10, 120, 134);
		contentPane.add(lblNewLabel_1);
		
		doubleBufferedCheck = new JCheckBox("<html>Double buffer</html>");
		doubleBufferedCheck.setBounds(270, 88, 103, 14);
		doubleBufferedCheck.setSelected(GlobalVars.DoubleBuffered);
		contentPane.add(doubleBufferedCheck);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(118, 155, 71, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GlobalVars.block 		= Integer.parseInt(bloskSize.getText());
				GlobalVars.Height 		= Integer.parseInt(height.getText());
				GlobalVars.Width		= Integer.parseInt(width.getText());
				GlobalVars.snakeSize 	= Integer.parseInt(snakeSize.getText());
				GlobalVars.snakeDelay 	= Integer.parseInt(snakeDelay.getText());
				
				GlobalVars.greenFroggs = Integer.parseInt(greenFroggs.getText());
				GlobalVars.redFroggs   = Integer.parseInt(redFroggs.getText());
				GlobalVars.blueFroggs  = Integer.parseInt(blueFroggs.getText());
				
				GlobalVars.froggProbability = Double.parseDouble(froggProb.getText());
				GlobalVars.DoubleBuffered = doubleBufferedCheck.isSelected();
				GlobalVars.printImages = printImages.isSelected();
				
				GlobalVars.refreshVars();
				
				mainForm.game.setBounds(0,0, GlobalVars.playWidth,GlobalVars.playHeight);	
				mainForm.game.repaint();

				dispose();
			}
		});
		
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setBounds(200, 155, 76, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(btnNewButton_1);
		
		JLabel lblSnake = new JLabel("");
		lblSnake.setVerticalAlignment(SwingConstants.TOP);
		lblSnake.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Snake", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblSnake.setBounds(262, 10, 103, 76);
		contentPane.add(lblSnake);
		
		JLabel label_1 = new JLabel("<html>Delay</html>");
		label_1.setBounds(270, 63, 37, 14);
		contentPane.add(label_1);
		
		snakeDelay = new JTextField();
		snakeDelay.setText("" + GlobalVars.snakeDelay);
		snakeDelay.setColumns(10);
		snakeDelay.setBounds(317, 60, 36, 20);
		contentPane.add(snakeDelay);
		
		printImages = new JCheckBox("Print images");
		printImages.setSelected(GlobalVars.printImages);
		printImages.setBounds(270, 113, 103, 14);
		contentPane.add(printImages);
		
	}
}
