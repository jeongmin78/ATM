package org.kpu.atm.bank;

public class Account {

	private int nID; //계좌번호
	private int nBalance; //계좌잔고
	private String strAccountName; //고객명
	private String strPassword; //계좌 비밀번호

	public Account(int id, int money, String name, String password) {
		nID = id;
		nBalance = money;
		strAccountName = name;
		strPassword = password;
		System.out.println(strAccountName+"님 "+nID+"번 계좌번호가 정상적으로 개설되었습니다. 감사합니다");
	}
	boolean authenticate(int id, String password) {
		if (nID == id && strPassword.equals(password))
		{
			return true;
		}
		else
			return false;
	}
	public boolean updatePasswd(String oldPasswd, String newPasswd) {
		if (oldPasswd.equals(newPasswd) || newPasswd.equals(strPassword))
			return false;
		else {
			strPassword = newPasswd;
			return true;
		}
	}
	int deposit(int money) {
		nBalance += money;
		return nBalance;
	}
	public int widraw(int money) {
		nBalance -= money;
		return nBalance;
	}
	public int getnID() { 
		return nID;
	}
	public int getnBalance() {
		return nBalance;
	}
	public String getStrAccountName() {
		return strAccountName;
	}
	
}
