package com.bank;

import java.io.*;
import java.util.*;

public class AccountOperations{
	Account acc;
	static String transaction="transactions.txt";//text file storing Transaction History
	
	public AccountOperations(Account acc){
		this.acc=acc;
	}
	
	public void deposit(double amt){
		if(amt<0){
			amt=-amt;
		}
		acc.balance+=amt;
		Account.addAccount(acc);
		Login.logins=Account.getAccounts();
		transactionEntry(amt,"Deposit");
	}
	public boolean withdraw(double amt){
		if(amt>acc.balance){
			return false;
		}else{
			acc.balance-=amt;
			Account.addAccount(acc);
			Login.logins=Account.getAccounts();
			transactionEntry(amt,"Withdraw");
			return true;
		}
	}
	//simple interest
	public double si(double p,double r,double t){
		return (p*r*t)/100;
	}
	
	//compound interest
	public double ci(double p,double r,double t,int n){
		double A=p*Math.pow((1+(r/n)),n*t);
		return (A-p);
	}
	
	//Transaction entry into transaction.txt
	public void transactionEntry(double amt,String type){
		try(BufferedWriter bw=new BufferedWriter(new FileWriter(transaction,true))){
			String date=new Date().toString().replace(" ",",");
			String stmt=acc.user+" "+type+" "+amt+" "+acc.balance+" "+date;
			bw.write(stmt);bw.newLine();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// reading from transaction.txt
	public static ArrayList<String> entryRead(){
		ArrayList<String> infos=new ArrayList<>();
		try(BufferedReader br=new BufferedReader(new FileReader(transaction))){
			String line;
			while((line=br.readLine())!=null){
				String[] parts=line.split(" ");
				String[] dateParts=parts[4].split(",");
				String date=dateParts[2]+" "+dateParts[1]+" "+dateParts[5]+" "+dateParts[3]+" "+dateParts[4];
				String infoNeeded=parts[0]+" "+parts[1]+" "+parts[2]+" "+parts[3]+" "+date;
				infos.add(infoNeeded);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return infos;
	}
	
}
