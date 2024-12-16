package com.bank;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Main extends JFrame implements ActionListener{
	JButton login,logOut;
	public Main(){
		setTitle("ABC BANK");
		setSize(400,400);
		JPanel mainPanel=new JPanel();mainPanel.setBackground(Color.black);
		add(mainPanel,BorderLayout.CENTER);
		mainPanel.setLayout(new GridBagLayout());
		JLabel l=new JLabel("WELCOME TO ABC BANK");l.setForeground(Color.yellow);
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;   // column position
        gbc.gridy = 0;   // row position
		gbc.gridwidth=2;//space for label
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing around buttons
        gbc.anchor = GridBagConstraints.CENTER;
		
		mainPanel.add(l,gbc);//Adding welcome label
		
		gbc.gridwidth=1;//for buttons
		gbc.gridx=1;

		logOut=new JButton("Log Out");logOut.setBackground(Color.cyan);logOut.setForeground(Color.black);
		login=new JButton("Login");login.setBackground(Color.cyan);login.setForeground(Color.black);
		
		//addActionListner
		login.addActionListener(this);logOut.addActionListener(this);
		
		//button aesthetics
		buttonAesthetics(logOut,login);
		
		gbc.gridy+=1;//increment row
		mainPanel.add(login, gbc);
		gbc.gridy+=1;
		mainPanel.add(logOut,gbc);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		String command=e.getActionCommand();
		switch(command){
			case "Login":
			//pop login frame
			new Login();
			break;
			case "Log Out":
			System.exit(0);
			break;
		}
	}
	
	public void buttonAesthetics(JButton... buttons){
		for(JButton button:buttons){
			button.setOpaque(true);
			button.setFocusPainted(false);//remove focus border
			button.setBackground(Color.cyan);//bg color
			button.setPreferredSize(new Dimension(100,25));
			button.addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent e) {
					button.setBackground(Color.yellow);//for hovering effect
				}
				public void mouseExited(MouseEvent e){
					button.setBackground(Color.cyan);
				}
			});
		}
	}
	public static void main(String...args){
		SwingUtilities.invokeLater(Main::new);
	}
}

