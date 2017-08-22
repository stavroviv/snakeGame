package SnakeGame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JFormattedTextField;

public class settingsForm extends JFrame {

	private JPanel contentPane;
	private JTextField height;
	private JTextField width;
	private JTextField bloskSize;
	private JTextField snakeSize;
	private JTextField greenFroggs;
	private JTextField redFroggs;
	private JTextField blueFroggs;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					settingsForm frame = new settingsForm();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public settingsForm() {
		
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setResizable(false);
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 353, 197);
		setLocationRelativeTo(null);
		setFocusable(true);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Height");
		lblNewLabel.setBounds(19, 35, 51, 20);
		
		contentPane.add(lblNewLabel);
		
		height = new JTextField();
		height.setBounds(80, 35, 40, 20);
		height.setColumns(10);
		height.setText("" + mainForm.playHeight);
		contentPane.add(height);
		
		JLabel lblWeight = new JLabel("Width");
		lblWeight.setBounds(19, 60, 51, 20);
		contentPane.add(lblWeight);
		
		width = new JTextField();
		width.setBounds(80, 60, 40, 20);
		width.setColumns(10);
		width.setText("" + mainForm.playWidth);
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
		bloskSize.setText("" + mainForm.block);
		contentPane.add(bloskSize);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setBounds(199, 136, 76, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(118, 136, 71, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainForm.block = Integer.parseInt(bloskSize.getText());
				mainForm.playHeight = Integer.parseInt(height.getText());
				mainForm.playWidth = Integer.parseInt(width.getText());
				
				mainForm.snakeSize = Integer.parseInt(snakeSize.getText());
				mainForm.greenFroggs = Integer.parseInt(greenFroggs.getText());
				mainForm.redFroggs = Integer.parseInt(redFroggs.getText());
				mainForm.blueFroggs = Integer.parseInt(blueFroggs.getText());
				
				dispose();
			}
		});
		contentPane.add(btnNewButton);
		
		snakeSize = new JTextField();
		snakeSize.setBounds(302, 35, 36, 20);
		contentPane.add(snakeSize);
		snakeSize.setText("" + mainForm.snakeSize);
		snakeSize.setColumns(10);
		
		greenFroggs = new JTextField();
		greenFroggs.setBounds(196, 35, 40, 20);
		greenFroggs.setText("" + mainForm.greenFroggs);
		contentPane.add(greenFroggs);
		greenFroggs.setColumns(10);
		
		redFroggs = new JTextField();
		redFroggs.setBounds(196, 60, 40, 20);
		redFroggs.setText("" + mainForm.redFroggs);
		contentPane.add(redFroggs);
		redFroggs.setColumns(10);
		
		blueFroggs = new JTextField();
		blueFroggs.setBounds(196, 85, 40, 20);
		blueFroggs.setText("" + mainForm.blueFroggs);
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
		
		JLabel lblSnakeSize = new JLabel("<html>Snake<p>size</html>");
		lblSnakeSize.setBounds(262, 30, 36, 34);
		contentPane.add(lblSnakeSize);
		
		JLabel label = new JLabel("");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Game field", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		label.setBounds(10, 10, 125, 110);
		contentPane.add(label);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setBorder(new TitledBorder(null, "Froggs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblNewLabel_1.setBounds(140, 10, 120, 110);
		contentPane.add(lblNewLabel_1);
	}
}
