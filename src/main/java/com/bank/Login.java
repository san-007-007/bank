package com.bank;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Login extends JFrame implements ActionListener{
	JTextField t1;
	JPasswordField t2;
	JLabel l,l1,l2;
	JButton forgotPass,createAccount,submit;
	static HashMap<String,Account> logins=new HashMap<>();
	public Login(){
		setTitle("Login");
		setSize(400,400);
		JPanel mainPanel=new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(Color.black);
		add(mainPanel,BorderLayout.CENTER);
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;   // column position
        gbc.gridy = 0;   // row position
		gbc.gridwidth=2;//for label
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing around elements
        gbc.anchor = GridBagConstraints.CENTER;
		
		l=new JLabel("ACCOUNT LOGIN");
		l1=new JLabel("Username");l2=new JLabel("Password");
		t1=new JTextField();t2=new JPasswordField();
		t1.setPreferredSize(new Dimension(100,20));t2.setPreferredSize(new Dimension(100,20));
		
		//add keylistener
		t1.addKeyListener(new KAdapter());
		t2.addKeyListener(new KAdapter());
		
		createAccount=new JButton("Create Account??");
		forgotPass=new JButton("Forgot Password??");
		submit=new JButton("Submit");
		
		//button aesthetics
		buttonAesthetics(createAccount,forgotPass,submit);
		
		//addActionListener
		createAccount.addActionListener(this);
		forgotPass.addActionListener(this);
		submit.addActionListener(this);
		
		mainPanel.add(l,gbc);
		
		gbc.gridwidth=1;
		gbc.gridy+=1;gbc.gridx=1;
		mainPanel.add(l1,gbc);
		gbc.gridx=2;gbc.gridwidth=2;
		mainPanel.add(t1,gbc);
		gbc.gridy+=1;gbc.gridx=1;gbc.gridwidth=1;
		mainPanel.add(l2,gbc);
		gbc.gridx=2;gbc.gridwidth=2;
		mainPanel.add(t2,gbc);
		
		gbc.gridy+=1;gbc.gridx=1;gbc.gridwidth=1;
		mainPanel.add(forgotPass,gbc);gbc.gridx+=1;
		mainPanel.add(createAccount,gbc);gbc.gridy+=1;gbc.gridx=1;
		mainPanel.add(submit,gbc);
		
		
		l.setForeground(Color.cyan);l1.setForeground(Color.white);l2.setForeground(Color.white);
		setVisible(true);
	}
	
	public static boolean isValid(String user,String pass){
		boolean flag=false;
		if(logins.containsKey(user)){
			if(pass.equals(logins.get(user).pass)){
				flag=true;
			}
		}
		return flag;
	}
		
	public class KAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int k=e.getKeyCode();
			switch(k){
				case KeyEvent.VK_ENTER:
				submit.doClick();
				break;
				
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		String cmmd=e.getActionCommand();
		switch(cmmd){
			case "Submit":
			logins=Account.getAccounts();
			if(t1.getText().isEmpty() || t2.getText().isEmpty()){
				JOptionPane.showMessageDialog(this,"Enter both username and password!!");
			}
			else if(isValid(t1.getText(),t2.getText())){
				//JOptionPane.showMessageDialog(null,"LOGIN SUCCESSFUL");
				new Banking(logins.get(t1.getText()));
			}else{
				JOptionPane.showMessageDialog(this,"LOGIN FAILED");
			}
			break;
			case "Forgot Password??":
			new Forgot();
			break;
			case "Create Account??":
			new Create();
			break;
		}
	}
	public void buttonAesthetics(JButton... buttons){
		for(JButton button:buttons){
			button.setOpaque(true);
			button.setFocusPainted(false);//remove focus border
			button.setBackground(Color.cyan);//bg color
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
}
