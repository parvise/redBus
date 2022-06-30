package com.demo.busBookingApp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "Bank_Account")
@DynamicUpdate
public class BankAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6764736627718479188L;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "Full_Name", length = 128, nullable = false)
	private String fullName;

	@Column(name = "Balance", nullable = false)
	private double balance;

	@Version
	@Column(name = "VERSION")
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "BankAccount [id=" + id + ", fullName=" + fullName + ", balance=" + balance + ", version=" + version
				+ "]";
	}

}
