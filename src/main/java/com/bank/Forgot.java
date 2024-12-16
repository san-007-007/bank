package com.bank;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Forgot extends JFrame implements ActionListener{
	JTextField t1;
	JPasswordField t2;
	JLabel l,l1,l2,msg;
	JButton change,cancel;
	public Forgot(){
		setTitle("Forgot Password");
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
		
		l=new JLabel("CHANGE PASSWORD");msg=new JLabel("");
		l1=new JLabel("Username");l2=new JLabel("New Password");
		t1=new JTextField();t2=new JPasswordField();
		t1.setPreferredSize(new Dimension(100,20));t2.setPreferredSize(new Dimension(100,20));
		
		//add keylistener
		t1.addKeyListener(new KAdapter());
		t2.addKeyListener(new KAdapter());
		
		change=new JButton("Change");
		cancel=new JButton("Cancel");
		
		//button aesthetics
		buttonAesthetics(cancel,change);
		
		//addActionListener
		change.addActionListener(this);
		cancel.addActionListener(this);
		
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
		mainPanel.add(cancel,gbc);gbc.gridx+=1;
		mainPanel.add(change,gbc);gbc.gridy+=1;
		
		mainPanel.add(msg);
		
		l.setForeground(Color.cyan);l1.setForeground(Color.white);l2.setForeground(Color.white);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		String cmmd=e.getActionCommand();
		if(cmmd.equals("Change")){
			Login.logins=Account.getAccounts();
			if(Login.logins.containsKey(t1.getText())){
				if(t2.getText().contains(" ")){
					JOptionPane.showMessageDialog(this,"Password can\'t contain space");
				}else if (t2.getText().length() < 6){
					JOptionPane.showMessageDialog(this, "Password must be at least 6 characters");
				}else{
					Account acc=Login.logins.get(t1.getText());
					acc.pass=t2.getText();
					Account.addAccount(acc);
					JOptionPane.showMessageDialog(this,"Password change successful");
				}
			}else{
				if(t1.getText().isEmpty() || t2.getText().isEmpty()){
					JOptionPane.showMessageDialog(this,"Fill details");
				}else{
					JOptionPane.showMessageDialog(this,"Username does not exist to change Password");
				}
			}
		}
		if(cmmd.equals("Cancel")){
			this.dispose();
		}
	}
	
	public class KAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int k=e.getKeyCode();
			switch(k){
				case KeyEvent.VK_ENTER:
				change.doClick();
				break;
				
			}
		}
		public void keyTyped(KeyEvent e){
			if(t2.getText().contains(" ")){
				msg.setText("Password must not contain space");msg.setForeground(Color.red);
			}
			else if (t2.getText().length() < 6) {
				msg.setText("Password must be at least 6 characters");msg.setForeground(Color.red);
				return;
			}else{
				msg.setText("");
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