package com.spacegeecks.services;

import java.util.Set;

import com.spacegeecks.beans.Transaction;
import com.spacegeecks.beans.TransactionStatus;
import com.spacegeecks.beans.User;
import com.spacegeecks.data.TransactionDAO;

public class TransactionService {
	
	private TransactionDAO transDAO;
	
	public TransactionService(TransactionDAO td) {
		transDAO = td;
	}

	public int addToCart(User u, Transaction t) {
		return transDAO.addToCart(u, t);
	}
	
	public boolean purchaseCart(User u) {
		return transDAO.purchaseCart(u);
	}

	public Set<Transaction> findCartByUser(User u) {
		return transDAO.findCartByUser(u);
	}

	public TransactionStatus findTStatusByName(String name) {
		return transDAO.findTStatusByName(name);
	}
	public TransactionStatus findTStatusById(int id) {
		return transDAO.findTStatusById(id);
	}
}
