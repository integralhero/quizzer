package quiz;

import java.util.ArrayList;

public class AccountManager {
	private ArrayList<Account> accounts;
	
	
	public AccountManager() {
		this.accounts = new ArrayList<Account>();
	}
	
	public boolean accountExists(String name) {
		for(Account a: accounts) {
			if((a.getName()).equals(name)) return true;
		}
		return false;
	}
	
	public boolean authenticate(String username, String query) {
		for(Account a: accounts) {
			if(a.authenticate(query)) return true;
		}
		return false;
	}
	
	public void printAccounts() {
		for(Account a: accounts) {
			System.out.println(a);
		}
	}
	public Account makeAccount(String username, String password) {
		Account newAccount = new Account(username, password);
		accounts.add(newAccount);
		return newAccount;
	}
	
	public class Account{
		private String username;
		private String pass;
		
		public Account(String username, String pass) {
			this.username = username;
			this.pass = pass;
		}
		
		public String getName() {
			return this.username;
		}
		
		public boolean authenticate(String query) {
			return query.equals(this.pass);
		}
		
		@Override
		public String toString() {
			return "{" + this.username + "," + this.pass + "}";
		}
	}
}
