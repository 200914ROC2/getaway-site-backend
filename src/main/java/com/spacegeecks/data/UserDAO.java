package com.spacegeecks.data;

import java.util.Set;

import com.spacegeecks.beans.*;

public interface UserDAO {
	
	public int createUser(User u);
	public Set<User> findUsers();
	public User findUserByTransaction(Transaction t);
	public Set<User> findUsersByRole(Role r);
	public User findUserByUsername(String username);
	public User findUserByEmail(String email);
	public User findUserByUserId(int userId);
	public void updateUser(User u);
	public void deleteUser(User u);

	public Set<Role> findRoles();
	public Role findRoleById(int id);
	public Role findRoleByName(String name);

}
