package com.spacegeecks.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.spacegeecks.beans.Transaction;
import com.spacegeecks.beans.TransactionStatus;
import com.spacegeecks.beans.User;
import com.spacegeecks.utils.ConnectionUtil;

public class TransactionPostgres implements TransactionDAO {
	
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public int addToCart(User u, Transaction t) {
		int id = 0;

		TransactionStatus ts = findTStatusByName("open");
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO cart_transactions VALUES "
					+ "(default, ?, ?, ?, ?, ?, ?, ?)";
			String[] keys = {"id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setDouble(1, t.getPrice());
			pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setInt(3, ts.getId());
			pstmt.setInt(4, u.getUserId());
			pstmt.setInt(5, t.getListingId());
			pstmt.setString(6, t.getTitle());
			pstmt.setString(7,t.getImage());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				id = rs.getInt(1);
				t.setId(id);
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;

	}

	@Override
	public boolean purchaseCart(User u) {

		TransactionStatus tOpen = findTStatusByName("open");
		TransactionStatus tSale = findTStatusByName("sale");
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "UPDATE cart_transactions " +
						"SET status = ? " + 
						"WHERE user_id = ? AND status = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tSale.getId());
			pstmt.setInt(2, u.getUserId());
			pstmt.setInt(3, tOpen.getId());
			
			System.out.println(pstmt.toString());
			
			int rows = pstmt.executeUpdate();
			
			if (rows > 0) {
				conn.commit();
			} else {
				conn.rollback();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}


	@Override
	public Set<Transaction> findCartByUser(User u) {
		HashSet<Transaction> transactions = null;
		TransactionStatus ts = findTStatusByName("open");
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM cart_transactions " +
						"WHERE user_id = ? AND status = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, u.getUserId());
			pstmt.setInt(2, ts.getId());
			
			ResultSet rs = pstmt.executeQuery();
			
			transactions = new HashSet<>();
			
			while(rs.next()) {
				Transaction t = new Transaction();
				t.setId(rs.getInt("id"));
				t.setStatus(ts);
				t.setPrice(rs.getDouble("price"));
				t.setTimeStamp(rs.getTimestamp("time_stamp"));
				t.setUserId(u.getUserId());
				t.setListingId(rs.getInt("listing_id"));
				t.setTitle(rs.getString("title"));
				t.setImage(rs.getString("image"));
								
				transactions.add(t);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		return transactions;
	}

	@Override
	public TransactionStatus findTStatusByName(String name) {
		TransactionStatus ts = null;
		
		try(Connection conn = cu.getConnection()) {
			String sql = "select * from transaction_status where name = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ts = new TransactionStatus();
				ts.setId(rs.getInt("id"));
				ts.setName(name);
				
				System.out.println(ts.getName() + " - " + ts.getId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ts;
	}
	
	@Override
	public TransactionStatus findTStatusById(int id) {
		TransactionStatus ts = null;
		
		try(Connection conn = cu.getConnection()) {
			String sql = "select * from transaction_status where id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ts = new TransactionStatus();
				ts.setId(id);
				ts.setName(rs.getString("name"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ts;
	}

}
