package com.cg.paymentwallet.repo;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.cg.paymentwallet.beans.Customer;
import com.cg.paymentwallet.exception.IPaymentWalletException;
import com.cg.paymentwallet.exception.PaymentWalletException;
import com.cg.paymentwallet.util.PaymentWalletUtil;


public class WalletRepoImpl implements WalletRepo {
	//private static Map<String, Customer> data=null;
	private static List<String> myList=null;
	Connection con =null;
	private EntityManager entityManager;
	
 public WalletRepoImpl() {

	 entityManager=JPAUtil.getEntityManager();
}
	static {
		//data=new HashMap<String ,Customer>();
		myList=new ArrayList<String>();
	}

	


	public void addTransaction(String mobileNo,String transactions) {
		try {
			String sql="INSERT INTO transactions values(?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);

			
			pstmt.setString(1,mobileNo);
			pstmt.setString(2, transactions);
			pstmt.executeUpdate();
			
		}catch (Exception e) {
			
		}
		myList.add(transactions);
			
		}
		
	public List<String> printTransactions(String mobileNo){
		List<String> transaction = new ArrayList<String>();
		try {
			String sql="SELECT * FROM transactions WHERE mobile =?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,mobileNo);
			ResultSet res=pstmt.executeQuery();
			while(res.next()) {
				transaction.add(res.getString(2));
			}
			
		}catch (Exception e) {
		
		}
		return transaction;
	}


	public void commitTransaction() {
	entityManager.getTransaction().commit();
		
	}


	public void beginTransaction() {
		entityManager.getTransaction().begin();
		
	}

	public void save(Customer customer) {
	entityManager.persist(customer);
		
	}

	public Customer findOne(String moblieNo) {
		Customer customer=entityManager.find(Customer.class, moblieNo);
		return customer;
	}

	public void updateWallet(String mobileNo, Customer customer) {
	entityManager.merge(customer);
		
	}
	public boolean checkMobile(String mobile) throws PaymentWalletException{
		boolean status= false;
		Customer customer=entityManager.find(Customer.class, mobile);
		if(customer!=null) {
			status=true;
		}
		else {
			throw new PaymentWalletException(IPaymentWalletException.Message4);
			
		}
		return status;
		
	}
	}

	


