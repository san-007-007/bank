package com.bank;

import java.util.*;
import java.io.*;
import javax.swing.*;

public class Account{
	String user;
	String pass;
	double balance;
	String pincode;
	String phone;
	
	public Account(String user,String pass,double balance,String phone,String pincode){
		this.user=user;this.pass=pass;this.balance=balance;this.phone=phone;this.pincode=pincode;
	}
	
	public static HashMap<String,Account> getAccounts(){
		HashMap<String,Account> accs=new HashMap<>();
		try(BufferedReader br=new BufferedReader(new FileReader("accounts.txt"))){
			String line;
			while((line=br.readLine())!=null){
				Account acc=fromString(line);
				accs.put(acc.user,acc);
			}
		}catch(Exception e){
			//e.printStackTrace();
			createSaveFile();
			
		}
		return accs;
	}
	public static Account fromString(String s){
		String[] parts=s.split(" ");
		double bal=Double.parseDouble(parts[2]);
		Account acc=new Account(parts[0],parts[1],bal,parts[3],parts[4]);
		return acc;
	}
	
	@Override
	public String toString(){
		return user+" "+pass+" "+balance+" "+phone+" "+pincode;
	}
	
	public static void addAccount(Account acc){
		try(BufferedWriter bw=new BufferedWriter(new FileWriter("accounts.txt",true))){//Opening in Append mode to avoid erasure older data
			bw.write(acc.toString());
			bw.newLine();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void removeAccount(Account acc){
		HashMap<String,Account> oldData=getAccounts();
		if(oldData.containsKey(acc.user)){
			oldData.remove(acc.user);
		}

		try(BufferedWriter bw=new BufferedWriter(new FileWriter("accounts.txt"))){
			for(Account account:oldData.values()){
				bw.write(account.toString());
				bw.newLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try(BufferedReader br=new BufferedReader(new FileReader("transactions.txt"));
		BufferedWriter bw=new BufferedWriter(new FileWriter("transactions.txt"))){
			String line;ArrayList<String>temp=new ArrayList<>();
			while((line=br.readLine())!=null){
				if(line.split(" ")[0].equals(acc.user)){
					temp.add(line);
				}
			}
			br.close();
			for(String s:temp){
				bw.write(s);bw.newLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public static void createSaveFile() {
		try(BufferedWriter bw=new BufferedWriter(new FileWriter("accounts.txt"))){
			bw.write("");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
