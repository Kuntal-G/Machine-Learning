package org.mini.watson.service;

import java.io.IOException;

import javax.swing.JFrame;

import org.mini.watson.ui.WatsonInterface;
public class WatsonService {
	public static void main(String[] args) throws IOException, Exception {
		JFrame frame = new WatsonInterface();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); //adjust components
		frame.setResizable(false);//user can not resize the window
		frame.setLocationRelativeTo(null);//center the JFrame
		frame.setVisible(true);//turns the user interface visible
	}
}