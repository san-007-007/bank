package com.bank;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Banking extends JFrame implements ActionListener{
	Account acc;
	JButton deposit,withdraw,currentBalance,accountStmt,logout,close,transfer;
	AccountOperations mngAcc;
	public Banking(Account acc){
		this.acc=acc;
		this.mngAcc=new AccountOperations(acc);
		setTitle(acc.user+" Bank Account");
		setSize(400,400);
		
		JPanel mainPanel=new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(Color.black);
		add(mainPanel,BorderLayout.CENTER);
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;   // column position
        gbc.gridy = 0;   // row position
		gbc.gridwidth=2;//for label
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing around elements
        gbc.anchor = GridBagConstraints.CENTER;
		
		deposit=new JButton("Deposit");
		withdraw=new JButton("Withdraw");
		currentBalance=new JButton("Current Balance");
		accountStmt=new JButton("Account Statement");
		close=new JButton("Account Closure");
		logout=new JButton("Log Out");
		transfer=new JButton("Amount Transfer");
		
		//addActionListener
		deposit.addActionListener(this);withdraw.addActionListener(this);
		currentBalance.addActionListener(this);accountStmt.addActionListener(this);
		close.addActionListener(this);logout.addActionListener(this);
		transfer.addActionListener(this);
		
		//button aesthetics
		buttonAesthetics(deposit,withdraw,currentBalance,accountStmt,logout,close,transfer);
		
		JLabel l=new JLabel("HAPPY BANKING");l.setForeground(Color.yellow);
		
		mainPanel.add(l,gbc);
		gbc.gridwidth=1;gbc.gridy+=2;gbc.gridx=1;
		
		mainPanel.add(deposit,gbc);gbc.gridx=2;
		mainPanel.add(withdraw,gbc);gbc.gridy+=1;gbc.gridx=1;
		mainPanel.add(currentBalance,gbc);gbc.gridx=2;
		mainPanel.add(accountStmt,gbc);gbc.gridy+=1;gbc.gridx=1;
		mainPanel.add(close,gbc);gbc.gridx=2;
		mainPanel.add(transfer,gbc);gbc.gridy+=1;gbc.gridx=1;
		mainPanel.add(logout,gbc);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		String cmmd=e.getActionCommand();
		
		switch(cmmd){
			case "Log Out":
			System.exit(0);
			break;
			case "Deposit":
			new Deposit(mngAcc);
			break;
			case "Withdraw":
			new Withdraw(mngAcc);
			break;
			case "Current Balance":
			new CurrentBalance(mngAcc);
			break;
			case "Account Statement":
			new TransactionHistory(mngAcc);
			break;
			case "Amount Transfer":
			new AmountTransfer(mngAcc);
			break;
			case "Account Closure":
			int option = JOptionPane.showConfirmDialog(this,
			"Are you sure you want to close your account?\nThis action cannot be undone.","Account Closure",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
			
			if(option==JOptionPane.YES_OPTION){
				Account.removeAccount(acc);
				JOptionPane.showMessageDialog(this, "Account closure done.");
				this.dispose();
			}else{
				JOptionPane.showMessageDialog(this, "Account closure canceled.");
			}
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
