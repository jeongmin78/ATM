package org.kpu.atm.bank;

import java.util.Scanner;
import org.kpu.atm.bank.Account;
import org.kpu.atm.helpdesk.CustomerSvc;
import org.kpu.atm.util.Statistics;

public class ATMachine {
	private Account[] accountArray; //고객계좌배열 참조변수
	private int nMachineBalance; //atm잔고
	private int nMaxAccountNum; //고객계좌 참조변수 배열크기
	private int nCurrentAccountNum; //개설된 고객계좌수
	private String strManagerPassword; //관리자비번

	public static final int BASE_ACCOUNT_ID = 100;
	
	Scanner scanner = new Scanner(System.in);

	public ATMachine(int size, int balance, String password) {
						//개설가능계좌, atm기기 잔금, 관리자암호
		nMaxAccountNum = size;
		nMachineBalance = balance;
		strManagerPassword = password;
		accountArray = new Account[nMaxAccountNum];
	}

	public void createAccount() {
		System.out.println("---------개설---------");
		System.out.print("이름 입력 : ");
		String name = scanner.nextLine();
		System.out.print("암호 입력 : ");
		String password = scanner.nextLine();
		accountArray[nCurrentAccountNum] = new Account(BASE_ACCOUNT_ID+nCurrentAccountNum, 0, name, password);
		nCurrentAccountNum++;
	}

	public void checkMoney() {
		try {
			System.out.println("---------조회---------");
			System.out.print("계좌번호 입력 : ");
			int id = Integer.parseInt(scanner.nextLine());
			System.out.print("비밀번호 입력 : ");
			String password = scanner.nextLine();
			
			for (int i=0; i<nCurrentAccountNum; i++) { 
				if(accountArray[i].authenticate(id, password)==true) {
					System.out.println("계좌 잔액 : " + accountArray[i].getnBalance());
				}
			}
		}
		catch (NumberFormatException e) {
			System.out.println("잘못된 입력입니다.");
		}
	}
	
	public void displayMenu() {
		System.out.println("---------------------");
		System.out.println("-      KPU bank     -");
		System.out.println("---------------------");
		System.out.println("1. 계좌 개설");
		System.out.println("2. 계좌 조회");
		System.out.println("3. 계좌 입금");
		System.out.println("4. 계좌 출금");
		System.out.println("5. 계좌 이체");
		System.out.println("6. 고객 센터");
		System.out.println("7. 고객 관리");
		System.out.println("9. 업무 종료");
	}

	public void depositMoney() {
		try {
			System.out.println("---------입금---------");
			System.out.print("계좌번호 입력: ");
			int id = Integer.parseInt(scanner.nextLine());
			System.out.print("비밀번호 입력: ");
			String password = scanner.nextLine();
			System.out.print("입금 액 입력: ");
			for (int i=0; i<nCurrentAccountNum; i++) { 
				if(accountArray[i].authenticate(id, password)==true) {
					int money = Integer.parseInt(scanner.nextLine());
					System.out.println("임금 후 잔액 : " + accountArray[i].deposit(money));
					nMachineBalance+=money;
				}
			}
		}
		catch (NumberFormatException e) {
			System.out.println("잘못된 입력입니다.");
		}
	}

	public void widrawMoney() { 
		try {
			System.out.println("---------출금---------");
			System.out.print("계좌번호 입력: ");
			int id = Integer.parseInt(scanner.nextLine());
			System.out.print("비밀번호 입력: ");
			String password = scanner.nextLine();
			for (int i=0; i<nCurrentAccountNum; i++) { 
				if(accountArray[i].authenticate(id, password)==true) {
					System.out.print("출금 액 입력: ");
					int money = Integer.parseInt(scanner.nextLine());
					if (accountArray[i].getnBalance()>= money) {
						System.out.println("출금 후 잔액 : " + accountArray[i].widraw(money));
						nMachineBalance-=money;
					}
					else
						System.out.println("계좌에 잔액이 부족합니다.");
				}
				else
					System.out.println("계좌번호와 비밀번호를 다시 확인하세요.");
			}
		}
		catch (NumberFormatException e) {
			System.out.println("잘못된 입력입니다.");
		}
	}
	
	public void transfer() {
		try {
			System.out.println("---------이체---------");
			System.out.print("계좌번호 입력: ");
			int id = Integer.parseInt(scanner.nextLine());
			System.out.print("비밀번호 입력: ");
			String password = scanner.nextLine();
			System.out.print("이체계좌 입력: ");
			int tfid = Integer.parseInt(scanner.nextLine());
			System.out.print("이체금액 입력: ");
			int tfmoney = Integer.parseInt(scanner.nextLine());
			for (int j=0; j<nCurrentAccountNum; j++) {
				if(accountArray[j].getnID() == tfid)
					break;
			}
			boolean tfidIs = false;
			for (int i=0; i<nCurrentAccountNum; i++) { 
				if(accountArray[i].authenticate(id, password)==true) {
					for(int j=0; j<nCurrentAccountNum; j++) {
						if(accountArray[j].getnID() == tfid) tfidIs = true;
						else tfidIs = false;
					}
					if (tfidIs == true) {
						accountArray[i].widraw(tfmoney);
						System.out.println("현재 잔액: "+ accountArray[i].getnBalance());
						accountArray[i].deposit(tfmoney);
						System.out.println("계좌 이체를 완료하였습니다.");
					}
				}
				else {
					System.out.println("계좌번호와 비밀번호를 다시 확인하세요.");
					tfidIs = true;
				}
			}
			if (tfidIs == false) {
				System.out.println("이체 계좌를 다시 확인하세요.");
			}
		}
		catch (NumberFormatException e) {
			System.out.println("잘못된 입력입니다.");
		}
	}
	public void requestSvc() {
		try {
			System.out.println("--------암호변경--------");
			System.out.print("계좌번호 입력: ");
			int id = Integer.parseInt(scanner.nextLine());
			System.out.print("비밀번호 입력: ");
			String password = scanner.nextLine();
			for (int i=0; i<nCurrentAccountNum; i++) { 
				if(accountArray[i].authenticate(id, password)==true) {
					CustomerSvc changePasswd = new CustomerSvc(accountArray, i);
					changePasswd.updatePasswdReq();
				}
			}
		}
		catch (NumberFormatException e) {
			System.out.println("잘못된 입력입니다.");
		}
	}
	
	public void managerMode() {
		try {
			System.out.println("--------고객관리--------");
			System.out.print("관리자 비밀번호 입력: ");
			String managerpasswd = scanner.nextLine();
			if(managerpasswd.equals(strManagerPassword)) {
				System.out.println("ATM 현금 잔고:    "+nMachineBalance);
				System.out.println("고객 잔고 총액:     "+Statistics.sum(accountArray,nCurrentAccountNum)+"원("+nCurrentAccountNum+"명)");
				System.out.println("고객 잔고 평균:     "+(int)Statistics.average(accountArray,nCurrentAccountNum)+"원");
				System.out.println("고객 잔고 최고:     "+Statistics.max(accountArray,nCurrentAccountNum)+"원");
				System.out.println("고객 계좌 현황(고객 잔고 내림 차순 정령)");
				Statistics.sort(accountArray, nCurrentAccountNum);
				for(int i=0; i<nCurrentAccountNum; i++) {
					System.out.println(accountArray[i].getStrAccountName()+"      "+accountArray[i].getnID()+"     "+accountArray[i].getnBalance()+"원");
				}
			}
			else {
				System.out.println("비밀번호가 일치하지 않습니다.");
			}
		}
		catch(ArithmeticException e) {
			System.out.println("고객이 존재하지 않습니다.");
		}
	}
}
