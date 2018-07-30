package com.cg.paymentwallet.pl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cg.paymentwallet.beans.Customer;
import com.cg.paymentwallet.exception.PaymentWalletException;
import com.cg.paymentwallet.service.WalletService;
import com.cg.paymentwallet.service.WalletServiceImpl;

public class Client {

	public static void main(String[] args) {
		int choice;
		int choice1;
		boolean result = false;
		WalletService service = new WalletServiceImpl();
		Scanner scanner = new Scanner(System.in);
		do {

			
			
			System.out.println("ENTER YOUR CHOICE");
			System.out.println("1.CREATE ACCOUNT");
			System.out.println("2.LOGIN");
			System.out.println("3.EXIT");
			choice = scanner.nextInt();
			Customer customer = new Customer();
			switch (choice) {
			case 1:
				try

				{
					System.out.println("***********CREATE ACCOUNT***********");
					System.out.println("Enter your name");

					String name = scanner.next();
					System.out.println("Enter mobile no");
					String moblieNo = scanner.next();
					System.out.println("Enter amount");
					BigDecimal amount = scanner.nextBigDecimal();

					result = service.validate(name, moblieNo, amount);
					if (result) {
						customer.setName(name);
						customer.setMobileNo(moblieNo);
						customer.setWallet(amount);
						service.createAccount(customer);
						System.out.println("Your account has been created");
						System.out.println("Name:"+customer.getName());
						System.out.println("MobileNumber:"+customer.getMobileNo());
						System.out.println("Balance:"+customer.getWallet());
					}
				} catch (PaymentWalletException e) {
					System.out.println(e.getMessage());
				}

				break;

			case 2:
				System.out.println("**********LOGIN***********");
				System.out.println("Enter mobile number");
				String mobile =scanner.next();
				try {
					if(service.checkMobile(mobile)) {
				
				do {
					System.out.println("1.Show Balance");
					System.out.println("2.Fund Tranfer");
					System.out.println("3.Deposit Amount");
					System.out.println("4.Withdraw Amount");
					System.out.println("5.Print Transactions");
					System.out.println("6.Exit");
					System.out.println("Enter your choice");
					
					choice1 = scanner.nextInt();
					switch (choice1) {
					case 1:
						System.out.println("******Check your balance******");
						//String mobileNo = scanner.next();
						Customer balanceShow = service.showBalance(mobile);
						System.out.println(balanceShow);
						break;

					case 2:
						System.out.println("******Fund Tranfer******");
						System.out.println("enter the sender mobile no");
						String mobileNo3 = scanner.next();
						System.out.println("enter the receiver mobileno");
						String mobileNo4 = scanner.next();
						System.out.println("enter the amount to be transferred");
						BigDecimal amount34 = scanner.nextBigDecimal();
						Customer transfer = service.fundTranfer(mobileNo3, mobileNo4, amount34);
						System.out.println(transfer);

						break;
						
					case 3:
						System.out.println("******Deposit amount******");
						//System.out.println("enter your mobile number");
						//String mobileNo1 = scanner.next();
						System.out.println("enter amount");
						BigDecimal amount1 = scanner.nextBigDecimal();
						
						Customer deposit = service.deposit(mobile, amount1);
						
						System.out.println(deposit);
						break;

					case 4:
						System.out.println("******Withdraw amount******");
						//System.out.println("enter your mobile number");
						//String mobileNo2 = scanner.next();
						System.out.println("enter amount");
						BigDecimal amount2 = scanner.nextBigDecimal();
						try {
							if(service.validateBalance(amount2, mobile)) {
								Customer withdraw = service.withdraw(mobile, amount2);
								System.out.println(withdraw);
							}
						} catch (PaymentWalletException e) {
							
							System.out.println(e.getMessage());
						}
						break;
					case 5:
						System.out.println("******Print Transactions******");
						//System.out.println("enter your mobile number");
						//String mobile=scanner.next();
						List<String> details=new ArrayList<String>();
						details=service.printTransactions(mobile);
						System.out.println(details);
					case 6:
						System.exit(0);
						
						

					
					}

				} while (choice1 != 6);
					}
				}catch (PaymentWalletException e) {
					System.out.println(e.getMessage());
					break;
				}

			case 3:
				System.exit(0);

			}

		} while (choice != 3);

	}

}
