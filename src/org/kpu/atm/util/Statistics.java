package org.kpu.atm.util;

import org.kpu.atm.bank.Account;

public class Statistics {
	private static int total;
	private static int avg;
	private static int max = 0;

	public static int sum(Account[] account, int size) {
		for(int i=0; i<size; i++) {
			total += account[i].getnBalance();
		}
		return total;
		
	}
	public static double average(Account[] account, int size) {
		avg = total/size;
		return avg;
	}
	
	public static int max(Account[] account, int size) {
		for(int i=0; i<size; i++) {
			max = Math.max(account[i].getnBalance(),max);
		}
		return max;
		
	}
	public static Account[] sort(Account[] account, int size) {
		Account temp;
		for (int i=0; i<size; i++) {
			temp = account[i];
			int j=i;
			while((j>0) && (account[j-1].getnBalance() < temp.getnBalance())) {
				account[j] = account[j-1];
				j--;
			}
			account[j] = temp;
		}
		return account;
	}
	
}
