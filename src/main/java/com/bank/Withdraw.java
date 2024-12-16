package com.bank;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Withdraw extends JFrame implements ActionListener{
	JTextField t1;
	JLabel l,l1;
	JButton cancel,deposit;
	AccountOperations accOp;
	public Withdraw(AccountOperations accOp){
		this.accOp=accOp;
		setTitle("Account Withdraw");
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
		
		l=new JLabel("ACCOUNT WITHDRAW");
		l1=new JLabel("Enter Withdraw Amount");
		t1=new JTextField();
		t1.setPreferredSize(new Dimension(100,20));
		
		//add keylistener
		t1.addKeyListener(new KAdapter());
		
		cancel=new JButton("Cancel");
		deposit=new JButton("Withdraw");
		
		//button aesthetics
		buttonAesthetics(cancel,deposit);
		
		//addActionListener
		cancel.addActionListener(this);
		deposit.addActionListener(this);
		
		mainPanel.add(l,gbc);
		
		gbc.gridwidth=1;
		gbc.gridy+=1;gbc.gridx=1;
		mainPanel.add(l1,gbc);
		gbc.gridx=2;gbc.gridwidth=2;
		mainPanel.add(t1,gbc);
		
		gbc.gridy+=1;gbc.gridx=1;gbc.gridwidth=1;
		mainPanel.add(cancel,gbc);gbc.gridx+=1;
		mainPanel.add(deposit,gbc);
		
		l.setForeground(Color.cyan);l1.setForeground(Color.white);
		setVisible(true);
	}
	
	public class KAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int k=e.getKeyCode();
			switch(k){
				case KeyEvent.VK_ENTER:
				deposit.doClick();
				break;
				
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		String cmmd=e.getActionCommand();
		switch(cmmd){
			case "Withdraw":
			try{
				double amt=Double.parseDouble(t1.getText());
				if(amt<=0){
					JOptionPane.showMessageDialog(this,"The withdrawal amount must be greater than 0.");
				}else if(accOp.withdraw(amt)){
					JOptionPane.showMessageDialog(this,amt+" withdrawn from "+accOp.acc.user+" Account");
				}else{
					JOptionPane.showMessageDialog(this,"Insufficient Balance: "+accOp.acc.balance);
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this,ex.getMessage());
			}
			break;
			case "Cancel":
			this.dispose();
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
