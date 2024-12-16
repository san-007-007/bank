package com.bank;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CurrentBalance extends JFrame implements ActionListener{
	JLabel l,l1;
	JButton ok;
	AccountOperations accOp;
	public CurrentBalance(AccountOperations accOp){
		this.accOp=accOp;
		setTitle("Account Balance");
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
		
		l=new JLabel("ACCOUNT BALANCE");
		l1=new JLabel("Rupees "+accOp.acc.balance);
		
		ok=new JButton("OK");
		ok.setDefaultCapable(true);
        getRootPane().setDefaultButton(ok);
		
		mainPanel.addKeyListener(new KAdapter());
		
		//button aesthetics
		buttonAesthetics(ok);
		
		//addActionListener
		ok.addActionListener(this);
		
		mainPanel.add(l,gbc);
		
		gbc.gridwidth=1;
		gbc.gridy+=1;gbc.gridx=1;
		mainPanel.add(l1,gbc);
		
		gbc.gridy+=1;gbc.gridx=1;gbc.gridwidth=1;
		mainPanel.add(ok,gbc);gbc.gridx+=1;
		
		l.setForeground(Color.cyan);l1.setForeground(Color.yellow);
		setVisible(true);
	}
	
	public class KAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int k=e.getKeyCode();
			switch(k){
				case KeyEvent.VK_ENTER:
				ok.doClick();
				break;
				
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		String cmmd=e.getActionCommand();
		switch(cmmd){
			case "OK":
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
