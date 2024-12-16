package com.bank;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;

public class TransactionHistory extends JFrame implements ActionListener{
	JTextArea ta;
	JLabel l,l1;
	JButton download,ok;
	ArrayList<String> transactions;
	AccountOperations accOp;
	public TransactionHistory(AccountOperations accOp){
		JScrollPane sp=new JScrollPane();
		this.accOp=accOp;
		setTitle(accOp.acc.user+" Account Statement");
		setSize(800,600);
		JPanel mainPanel=new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(Color.black);
		add(sp,BorderLayout.CENTER);
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;   // column position
        gbc.gridy = 0;   // row position
		gbc.gridwidth=2;//for label
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing around elements
        gbc.anchor = GridBagConstraints.CENTER;
		
		l=new JLabel("ACCOUNT STATEMENT");
		ta=new JTextArea();ta.setBackground(Color.black);
		ta.setForeground(Color.yellow);ta.setEditable(false);
		
		//dumping details into textArea
		transactions=AccountOperations.entryRead();
		ta.setText(String.format("%-30s%-25s%-30s%-40s%n","Transaction Type", "Transaction Amount", "Current Balance", "Transaction Date & Time"));
		transactions.forEach(s->{
			if(s.split(" ")[0].equals(accOp.acc.user)){
				String type=s.split(" ")[1];
				String amt=s.split(" ")[2];
				String bal=s.split(" ")[3];
				String date=String.join(" ", Arrays.copyOfRange(s.split(" "), 4, 9));
				
				double amtDouble = Double.parseDouble(amt);double balDouble = Double.parseDouble(bal);
				ta.append(String.format("%-40s%-40s%-40s%-30s%n", type, String.format("%.2f", amtDouble), String.format("%.2f", balDouble), date));
			}
		});
		
		ok=new JButton("OK");
		download=new JButton("Download Statement");
		
		//button aesthetics
		buttonAesthetics(ok,download);
		
		//addActionListener
		ok.addActionListener(this);
		download.addActionListener(this);
		
		mainPanel.add(l,gbc);
		
		gbc.gridwidth=10;
		gbc.gridy+=1;gbc.gridx=1;
		
		mainPanel.add(ta,gbc);
		
		gbc.gridy+=1;gbc.gridx=1;gbc.gridwidth=1;
		mainPanel.add(ok,gbc);gbc.gridx+=1;
		mainPanel.add(download,gbc);
		
		l.setForeground(Color.cyan);
		sp.setViewportView(mainPanel);
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
			case "Download Statement":
			try{
				String text = String.format(
					    "%-50s%n%nUsername: %-20s Phone no: %-15s Pincode: %-10s%n%n",
					    "Account Statement", accOp.acc.user, accOp.acc.phone, accOp.acc.pincode
					) + 
					ta.getText();
				saveTextToPDF(text,System.getProperty("user.home") + "/Downloads/Account Statement ("+accOp.acc.user+").pdf");
				JOptionPane.showMessageDialog(this,"Account Statement.pdf downloaded at Downloads");
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this,ex.getMessage());
			}
			break;
			case "OK":
			this.dispose();
			break;
		}
	}
	
	public static void saveTextToPDF(String text, String filePath) throws IOException {
		// Create a new PDF document
	    PDDocument document = new PDDocument();
	    
	    // Create a new page and add it to the document
	    PDPage page = new PDPage();
	    document.addPage(page);

	    // Create a content stream to write content to the page
	    PDPageContentStream contentStream = new PDPageContentStream(document, page);

	    // Set the font and starting position for text
	    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
	    contentStream.beginText();
	    contentStream.newLineAtOffset(50, 750); // Starting position (x, y)

	    // Remove any carriage return characters (\r) and replace them with spaces
	    text = text.replace("\r", "");  // Remove \r carriage return characters

	    // Split the text into lines based on line breaks
	    String[] lines = text.split("\n");

	    // Loop through each line of text
	    for (String line : lines) {
	        // Write the line of text to the PDF document
	        contentStream.showText(line);

	        // Move to the next line (decrease y position)
	        contentStream.newLineAtOffset(0, -15); // Adjust vertical spacing as needed
	    }

	    // End writing the text
	    contentStream.endText();

	    // Close the content stream
	    contentStream.close();

	    // Save the document to a file
	    document.save(filePath);

	    // Close the document
	    document.close();
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