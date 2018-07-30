package com.cg.paymentwallet.beans;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="cust_details")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
@Column(name="name",length=20)
	
	private String name;
@Id
@Column(name="mobile",length=20)
	private String mobileNo;
@Column(name="balance",length=20)
@Embedded
	private Wallet wallet;
	public Customer() {
		wallet =  new Wallet();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public BigDecimal getWallet() {
		return wallet.getBalance();
	}
	
	public void setWallet(BigDecimal amount) {
		wallet.setBalance(amount);
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", mobileNo=" + mobileNo + ", wallet=" + wallet + "]";
	}
	
	
	
	
}
