package org.kpu.atm.helpdesk;

import java.util.Scanner;

import org.kpu.atm.bank.Account;


public class CustomerSvc {
	private Account[] acctArray;
	private int nCurrentAcctNum;
	
	Scanner scanner = new Scanner(System.in);

	public CustomerSvc(Account[] acctArray, int nCurrentAcctNum) {
		this.acctArray = acctArray;
		this.nCurrentAcctNum = nCurrentAcctNum;
	}
	
	public void updatePasswdReq() {
		System.out.print("신규 비밀번호 입력: ");
		String newpassword = scanner.nextLine();
		if(acctArray[nCurrentAcctNum].updatePasswd("", newpassword)==true) {
			System.out.println("비밀번호를 수정하였습니다.");
		}
		else
			System.out.println("비밀번호를 수정하지 못했습니다.");
	}

}
