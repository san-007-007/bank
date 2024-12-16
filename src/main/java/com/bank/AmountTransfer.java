package com.bank;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class AmountTransfer extends JFrame implements ActionListener{
	JTextField t1,t2;
	JLabel l,l1,l2;
	JButton cancel,transfer;
	AccountOperations accOp;
	public AmountTransfer(AccountOperations accOp){
		this.accOp=accOp;
		setTitle("Amount Transfer");
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
		
		l=new JLabel("AMOUNT TRANSFER");
		l1=new JLabel("Username of Recipent");l2=new JLabel("Transfer Amount");
		t1=new JTextField();t2=new JTextField();
		t1.setPreferredSize(new Dimension(100,20));t2.setPreferredSize(new Dimension(100,20));
		
		cancel=new JButton("Cancel");
		transfer=new JButton("Transfer");
		
		t1.addKeyListener(new KAdapter());t2.addKeyListener(new KAdapter());
		
		//button aesthetics
		buttonAesthetics(cancel,transfer);
		
		//addActionListener
		cancel.addActionListener(this);transfer.addActionListener(this);
		
		mainPanel.add(l,gbc);
		
		gbc.gridwidth=1;
		gbc.gridy+=1;gbc.gridx=1;
		mainPanel.add(l1,gbc);
		gbc.gridx=2;gbc.gridwidth=2;
		mainPanel.add(t1,gbc);gbc.gridwidth=1;
		gbc.gridy+=1;gbc.gridx=1;
		mainPanel.add(l2,gbc);
		gbc.gridx=2;gbc.gridwidth=2;
		mainPanel.add(t2,gbc);
		
		gbc.gridy+=1;gbc.gridx=1;gbc.gridwidth=1;
		mainPanel.add(cancel,gbc);gbc.gridx+=1;
		mainPanel.add(transfer,gbc);
		
		l.setForeground(Color.cyan);l1.setForeground(Color.white);l2.setForeground(Color.white);
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e){
		String cmmd=e.getActionCommand();
		switch(cmmd){
			case "Cancel":
			this.dispose();
			break;
			case "Transfer":
			Login.logins=Account.getAccounts();
			if(t1.getText().isEmpty() || t2.getText().isEmpty()){
				JOptionPane.showMessageDialog(this,"Fill necessary Details");
			}else if(t1.getText().equals(accOp.acc.user)){
				JOptionPane.showMessageDialog(this,"Recipent user must be someone else..");
			}
			else if(Login.logins.containsKey(t1.getText())){
				try{
					double amt=Double.parseDouble(t2.getText());
					if(amt<=0){
						JOptionPane.showMessageDialog(this,"The Transfer amount must be greater than 0.");
					}else{
						if(accOp.withdraw(amt)){
							AccountOperations tr=new AccountOperations(Login.logins.get(t1.getText()));
							tr.deposit(amt);
							JOptionPane.showMessageDialog(this,"Amount transfered to "+t1.getText());
						}else{
							JOptionPane.showMessageDialog(this,"Insufficient Balance: "+accOp.acc.balance);
						}
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(this,ex.getMessage());
				}
			}else{
				JOptionPane.showMessageDialog(this,"No such Account user Exist");
			}
			break;
		}
	}
	
	public class KAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int k=e.getKeyCode();
			switch(k){
				case KeyEvent.VK_ENTER:
				transfer.doClick();
				break;
				
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
