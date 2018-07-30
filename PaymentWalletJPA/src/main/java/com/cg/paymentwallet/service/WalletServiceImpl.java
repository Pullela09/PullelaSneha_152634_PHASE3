package com.cg.paymentwallet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.cg.paymentwallet.beans.Customer;
import com.cg.paymentwallet.exception.IPaymentWalletException;
import com.cg.paymentwallet.exception.PaymentWalletException;
import com.cg.paymentwallet.repo.WalletRepo;
import com.cg.paymentwallet.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {
	WalletRepo repo = null;

	public WalletServiceImpl() {

		repo = new WalletRepoImpl();
	}

	public void createAccount(Customer customer) {
		repo.beginTransaction();

		repo.save(customer);
		repo.commitTransaction();
	}

	public Customer showBalance(String moblineNo) {

		Customer customer = repo.findOne(moblineNo);
		return customer;
	}
	/*
	 * public List<String> printTransactions(){ return repo.viewTransaction();
	 * 
	 * }
	 */

	public Customer fundTranfer(String sourceMobile, String targetMobile, BigDecimal amount) {

		Customer senderFund = repo.findOne(sourceMobile);
		BigDecimal senderBalance = senderFund.getWallet().subtract(amount);
		senderFund.setWallet(senderBalance);
		repo.beginTransaction();
		repo.updateWallet(sourceMobile, senderFund);
		repo.commitTransaction();
		Customer receiverFund = repo.findOne(targetMobile);
		BigDecimal receiverBalance = receiverFund.getWallet().add(amount);
		receiverFund.setWallet(receiverBalance);
		repo.beginTransaction();
		repo.updateWallet(targetMobile, receiverFund);
		repo.commitTransaction();
		// repo.addTransaction("Deposited from :"+sourceMobile+"to:" +targetMobile+"
		// with the amount :" +amount);

		return repo.findOne(targetMobile);

	}

	public Customer deposit(String mobileNo, BigDecimal amount) {
		Customer customer = repo.findOne(mobileNo);

		BigDecimal depositAmt = customer.getWallet().add(amount);

		customer.setWallet(depositAmt);
		repo.beginTransaction();
		repo.updateWallet(mobileNo, customer);
		repo.commitTransaction();
		repo.addTransaction(mobileNo, "amountdeposited :" + amount + "balance:" + depositAmt+"at:" +LocalDateTime.now());

		return customer;

	}

	public Customer withdraw(String mobileNo, BigDecimal amount) {
		Customer customer = repo.findOne(mobileNo);

	

		BigDecimal withdrawAmt = customer.getWallet().subtract(amount);
		customer.setWallet(withdrawAmt);
		repo.beginTransaction();
		repo.updateWallet(mobileNo,customer);
		repo.commitTransaction();
	repo.addTransaction(mobileNo, "amountWithdrawn :" + amount + "balance:" + withdrawAmt+"at:" +LocalDateTime.now());
		return customer;

	}

	public List<String> printTransactions(String mobileNo) {
		return repo.printTransactions(mobileNo);

	}

	public boolean validate(String name, String moblieNo, BigDecimal amount) throws PaymentWalletException {
		boolean result = false;
		if ((name.trim().matches("[a-zA-z]*"))) {
			if ((moblieNo.matches("\\d{10}"))) {
				result = true;

			} else {
				throw new PaymentWalletException(IPaymentWalletException.Message2);
			}

		} else {
			throw new PaymentWalletException(IPaymentWalletException.Message1);
		}
		return result;

	}
	public boolean validateBalance(BigDecimal balance ,String mobile) throws PaymentWalletException{
		boolean result=false;
		BigDecimal amt=repo.findOne(mobile).getWallet();
		BigDecimal big=balance.max(amt);
		if(big.equals(amt)) {
			result=true;
		}else {
			throw new PaymentWalletException(IPaymentWalletException.Message3);
		}
		return result;
		
	}

	public Customer findOne(String moblieNo) {
		Customer customer=repo.findOne(moblieNo);
		return customer;
	}

	public boolean checkMobile(String mobile) throws PaymentWalletException {
		return repo.checkMobile(mobile);
	}


}
