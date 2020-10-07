package com.spacegeecks.data;

import java.util.Set;

import com.spacegeecks.beans.Transaction;
import com.spacegeecks.beans.TransactionStatus;
import com.spacegeecks.beans.User;

public interface TransactionDAO {

	public int addToCart(User u, Transaction t);
	public boolean purchaseCart(User u);

	public Set<Transaction> findCartByUser(User u);

	public TransactionStatus findTStatusByName(String name);
	public TransactionStatus findTStatusById(int id);
	
}
