package com.bank;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Create extends JFrame implements ActionListener{
	JLabel l,l1,l2,l3,l4,msg;
	JTextField t1,t2,t3,t4;
	JButton create,cancel;
	public Create(){
		setTitle("Create Account");
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
		
		l=new JLabel("CREATE ACCOUNT");
		l1=new JLabel("Username");l2=new JLabel("Password");
		l3=new JLabel("Phone no");l4=new JLabel("Pincode");
		msg=new JLabel("");
		
		t1=new JTextField();t2=new JTextField();t3=new JTextField();t4=new JTextField();
		
		t1.setPreferredSize(new Dimension(100,20));t2.setPreferredSize(new Dimension(100,20));
		t3.setPreferredSize(new Dimension(100,20));t4.setPreferredSize(new Dimension(100,20));
		
		create=new JButton("Create");cancel=new JButton("Cancel");
		
		//addKeyListener
		t1.addKeyListener(new KAdapter());t2.addKeyListener(new KAdapter());
		t3.addKeyListener(new KAdapter());t4.addKeyListener(new KAdapter());
		
		//button aesthetics
		buttonAesthetics(cancel,create);
		
		//addActionListener
		cancel.addActionListener(this);create.addActionListener(this);
		
		mainPanel.add(l,gbc);
		gbc.gridx=1;gbc.gridy+=1;gbc.gridwidth=1;
		mainPanel.add(l1,gbc);gbc.gridwidth=2;gbc.gridx=2;
		mainPanel.add(t1,gbc);gbc.gridwidth=1;gbc.gridx=1;gbc.gridy+=1;
		
		mainPanel.add(l2,gbc);gbc.gridwidth=2;gbc.gridx=2;
		mainPanel.add(t2,gbc);gbc.gridwidth=1;gbc.gridx=1;gbc.gridy+=1;
		
		mainPanel.add(l3,gbc);gbc.gridwidth=2;gbc.gridx=2;
		mainPanel.add(t3,gbc);gbc.gridwidth=1;gbc.gridx=1;gbc.gridy+=1;
		
		mainPanel.add(l4,gbc);gbc.gridwidth=2;gbc.gridx=2;
		mainPanel.add(t4,gbc);gbc.gridwidth=1;gbc.gridx=1;gbc.gridy+=1;
		
		mainPanel.add(cancel,gbc);gbc.gridx=2;
		mainPanel.add(create,gbc);gbc.gridx=0;gbc.gridy+=1;
		
		mainPanel.add(msg);
		l.setForeground(Color.cyan);l1.setForeground(Color.white);
		l2.setForeground(Color.white);l3.setForeground(Color.white);
		l4.setForeground(Color.white);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		String cmmd = e.getActionCommand();
		Login.logins = Account.getAccounts();
    
		if (cmmd.equals("Create")) {
			String username = t1.getText();
			String phone = t3.getText();
			String password = t2.getText();
			String pincode = t4.getText();
        
			// Check if any field is empty
			if (username.isEmpty() || password.isEmpty() || phone.isEmpty() || pincode.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Enter all details");
				return;
			}
        
			// Check if username already exists
			if (Login.logins.containsKey(username)) {
				JOptionPane.showMessageDialog(this, "Account already exists");
				return;
			}
        
			// Validate phone number (should be 10 digits)
			if (!phone.matches("\\d{10}")) {
				JOptionPane.showMessageDialog(this, "Invalid phone number");
				return;
			}
        
			// Validate password (example: at least 6 characters)
			if (password.length() < 6) {
				JOptionPane.showMessageDialog(this, "Password must be at least 6 characters");
				return;
			}
			
			if(password.contains(" ")){
				msg.setText("Password must not contain space");
				msg.setForeground(Color.RED);
			}
        
			// Validate pincode (exactly 6 digits)
			if (!pincode.matches("\\d{6}")) {
				JOptionPane.showMessageDialog(this, "Invalid pincode");
				return;
			}
        
			// If all checks pass, create the account
			Account acc = new Account(username, password, 0, phone, pincode);
			Account.addAccount(acc);
			JOptionPane.showMessageDialog(this, "Account created successfully");
		}
    
		if (cmmd.equals("Cancel")) {
			this.dispose();
		}
	}
	
	public class KAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int k=e.getKeyCode();
			switch(k){
				case KeyEvent.VK_ENTER:
				create.doClick();
				break;
				
			}
		
		}
		public void keyTyped(KeyEvent e){
			Login.logins = Account.getAccounts();
			String username = t1.getText();
			String phone = t3.getText();
			String password = t2.getText();
			String pincode = t4.getText();
        
			// Check username existence
			if (Login.logins.containsKey(username)) {
				msg.setText("Username already exists");
				msg.setForeground(Color.RED);
			}
			// Check phone number validity
			else if (!phone.matches("\\d{10}") && !phone.isEmpty()) { // Check if phone is exactly 10 digits
				msg.setText("Invalid phone number");
				msg.setForeground(Color.RED);
			}
			// Check password validity (minimum 6 characters)
			else if (password.length() < 6 && !password.isEmpty()) {
				msg.setText("Password must be at least 6 characters");
				msg.setForeground(Color.RED);
			}
			else if(password.contains(" ")){
				msg.setText("Password must not contain space");
				msg.setForeground(Color.RED);
			}
			// Check pincode validity (exactly 6 digits)
			else if (!pincode.matches("\\d{6}") && !pincode.isEmpty()) {
				msg.setText("Invalid pincode(must have 6 digits)");
				msg.setForeground(Color.RED);
			}
			else {
				msg.setText(""); // Clear msg
			}
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

