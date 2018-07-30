package com.cg.paymentwallet.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.cg.paymentwallet.beans.Customer;
import com.cg.paymentwallet.exception.PaymentWalletException;

public interface WalletService {
	
	public void createAccount(Customer customer);
	public Customer showBalance(String moblineNo);
	public Customer findOne(String moblieNo);
	public Customer fundTranfer(String sourceMobile, String targetMobile, BigDecimal amount);
	public Customer deposit(String mobileNo,BigDecimal amount);
	public Customer withdraw(String mobileNo,BigDecimal amount);
	public boolean validate(String name, String moblieNo, BigDecimal amount) throws PaymentWalletException;
	public List<String> printTransactions(String mobileNo);
	public boolean validateBalance(BigDecimal balance ,String mobile) throws PaymentWalletException;
	public boolean checkMobile(String mobile) throws PaymentWalletException;

}
