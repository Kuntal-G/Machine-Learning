package org.mini.watson.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.mini.watson.service.WatsonCsvMap;
import org.mini.watson.service.WatsonQuestionTokenizer;

public final class WatsonInterface extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private String dir = System.getProperty("user.dir");
	private String imagePath = /*dir+*/ "/home/kuntal/Downloads/watsonlogo.png";
	private String defaulTextMessage = "Insert your questions here";
	private JButton submitButton,openFileButton;
	private JPanel questionPanel,buttonsPanel,picturePanel;
	private JLabel pictureLabel;
	private JTextField inputTextField,outputTextField;
	private JFileChooser chooserFile;
	private WatsonQuestionTokenizer st ;
	private WatsonCsvMap cl ;
	public WatsonInterface() throws Exception
	{
		setTitle("Watson Junior");
		questionPanel = new JPanel();
		buttonsPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout( questionPanel, BoxLayout.PAGE_AXIS));
		instantiatePanel();
		getContentPane().add(questionPanel, BorderLayout.CENTER);
		getContentPane().add(buttonsPanel,BorderLayout.SOUTH);
		getContentPane().add(pictureLabel,BorderLayout.NORTH);
		getContentPane().setPreferredSize(new Dimension(600, 350));
	}
	public void instantiatePanel() throws IOException {
		BufferedImage myPicture = ImageIO.read(new File(imagePath));
		pictureLabel = new JLabel(new ImageIcon(myPicture));
		submitButton = new JButton("Submit");
		submitButton.setFocusable(false);
		submitButton.addActionListener(new submitButtonListen());
		submitButton.setEnabled(false);
		submitButton.setPreferredSize(new Dimension(150, 40));
		openFileButton = new JButton("Open CSV File");
		openFileButton.setFocusable(false);
		openFileButton.addActionListener(new openFileButton());
		openFileButton.setPreferredSize(new Dimension(150, 40));
		inputTextField = new JTextField(10);
		inputTextField.setText(defaulTextMessage);
		inputTextField.setEnabled(true);
		outputTextField = new JTextField(20);
		outputTextField.setEnabled(true);
		buttonsPanel.add(submitButton);
		buttonsPanel.add(openFileButton);
		questionPanel.add(new JLabel("Question: "));
		questionPanel.add(inputTextField);
		questionPanel.add(new JLabel("Answer: "));
		questionPanel.add(outputTextField);
	}
	class openFileButton implements ActionListener {
		//@Override
		public void actionPerformed(ActionEvent e) {
			chooserFile = new JFileChooser();
			int returned = chooserFile.showOpenDialog(null);
			if (returned == JFileChooser.APPROVE_OPTION)
			{
				File arquivo = chooserFile.getSelectedFile();
				cl = new WatsonCsvMap(chooserFile.getSelectedFile().getAbsolutePath(),',');
				submitButton.setEnabled(true);
			}
		}
	}
	public class submitButtonListen implements ActionListener {
		//@Override
		public void actionPerformed(ActionEvent e) {
			String question = inputTextField.getText();
			//INSERT THE SOLUTION OF THE EXERCISE HERE, IT TAKES ABOUT 3 LINES	OF CODE
			st = new WatsonQuestionTokenizer(question);
			try {
				st.run();
			} catch (Exception ex) {
				Logger.getLogger(WatsonInterface.class.getName()).log(Level.SEVERE, null, ex);
			}
			outputTextField.setText(st.findTheAnswer(cl));
		}
	}
	public void error() {
		JOptionPane.showMessageDialog(null, " You must enter a question!");
	}
}
