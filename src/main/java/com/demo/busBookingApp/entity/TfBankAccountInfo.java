package com.demo.busBookingApp.entity;

public class TfBankAccountInfo {

	private Long fromAccId;
	private Long toAccId;
	private double amount;
	private Long versionNo;
	private double balance;
	private Long toVersionNo;

	public Long getFromAccId() {
		return fromAccId;
	}

	public void setFromAccId(Long fromAccId) {
		this.fromAccId = fromAccId;
	}

	public Long getToAccId() {
		return toAccId;
	}

	public void setToAccId(Long toAccId) {
		this.toAccId = toAccId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Long getToVersionNo() {
		return toVersionNo;
	}

	public void setToVersionNo(Long toVersionNo) {
		this.toVersionNo = toVersionNo;
	}

	@Override
	public String toString() {
		return "TfBankAccountInfo [fromAccId=" + fromAccId + ", toAccId=" + toAccId + ", amount=" + amount
				+ ", versionNo=" + versionNo + ", balance=" + balance + ", toVersionNo=" + toVersionNo + "]";
	}

}
