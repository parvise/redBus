package com.demo.busBookingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.busBookingApp.dao.BankAccountDAO;
import com.demo.busBookingApp.entity.BankAccount;
import com.demo.busBookingApp.entity.TfBankAccountInfo;

@Service
public class BankAccountService {

	@Autowired
	private BankAccountDAO dao;

	@Cacheable(value = "bankCache")
	public Iterable<BankAccount> listAll() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return dao.findAll();
	}

	@Cacheable(value = "bankCache", key = "#id", unless = "#result==null")
	public BankAccount findById(Long id) {
		System.out.println("Calling find" + id);
		return dao.findOne(id);
	}

	@Cacheable(value = "bankCache", key = "#fullName", unless = "#result==null")
	public BankAccount findByName(String fullName) {
		return dao.findByName(fullName);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendMoney(TfBankAccountInfo info) throws Exception {
		BankAccount fromAcnt = convertBeanToEntity(info.getFromAccId(), -info.getAmount(), info.getVersionNo());
		fromAcnt = dao.save(fromAcnt);
		System.out.println("Debitted From account -->" + fromAcnt);
		BankAccount toAcnt = convertBeanToEntity(info.getToAccId(), info.getAmount(), info.getToVersionNo());
		toAcnt = dao.save(toAcnt);
		System.out.println("Credit To this account -->" + toAcnt);
	}

	@CachePut(value = "bankCache", key = "#bankAccount.id", unless = "#result==null")
	public BankAccount saveAmount(BankAccount bankAccount) {
		System.out.println("Saves " + bankAccount.getId());
		return dao.save(bankAccount);
	}

	private BankAccount convertBeanToEntity(Long id, double amount, Long versionNo) throws Exception {
		BankAccount account = findById(id);
		if (account.getBalance() + amount < 0) {
			throw new Exception("The money in the account '" + id + "' is not enough (" + account.getBalance() + ")");
		}

		if (account.getVersion() != versionNo) {
			throw new Exception("The money in the account '" + id + "' is updated some one");
		}

		account.setBalance(account.getBalance() + amount);
		return account;
	}
}
