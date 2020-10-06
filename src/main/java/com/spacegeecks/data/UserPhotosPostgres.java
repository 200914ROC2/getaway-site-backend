package com.spacegeecks.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.spacegeecks.beans.PhotoStatus;
import com.spacegeecks.beans.UserPhoto;
import com.spacegeecks.utils.ConnectionUtil;

public class UserPhotosPostgres implements UserPhotosDAO {

	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	@Override
	public Set<UserPhoto> getAllApprovedUserPhotos() {
		
		try(Connection conn = cu.getConnection()){
			HashSet<UserPhoto> userPhotos = new HashSet<UserPhoto>();
			String sql = "SELECT * from user_photo join photo_status where photo_status.name=approved";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				PhotoStatus status = this.getPhotoStatusByName("approved");
				
				UserPhoto userPhoto = new UserPhoto();
				userPhoto.setUserPhotoId(rs.getInt(rs.getInt("id")));
				userPhoto.setPhotoStatus(status);
				userPhoto.setPhotoCredit(rs.getString("photo_credit"));
				userPhoto.setUserId(rs.getInt("user_id"));
				
				userPhotos.add(userPhoto);
			}	
			return userPhotos;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Set<UserPhoto> getAllUnApprovedUserPhotos() {
		
		try(Connection conn = cu.getConnection()){
			HashSet<UserPhoto> userPhotos = new HashSet<UserPhoto>();
			String sql = "SELECT * from user_photo join photo_status where photo_status.name=unapproved";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				PhotoStatus status = this.getPhotoStatusByName("unapproved");
				
				UserPhoto userPhoto = new UserPhoto();
				userPhoto.setUserPhotoId(rs.getInt(rs.getInt("id")));
				userPhoto.setPhotoStatus(status);
				userPhoto.setPhotoCredit(rs.getString("photo_credit"));
				userPhoto.setUserId(rs.getInt("user_id"));
				
				userPhotos.add(userPhoto);
			}	
			return userPhotos;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public boolean updateUserPhoto(PhotoStatus photoStatus, int id) {
		
		try(Connection conn = cu.getConnection()) {
			String sql = "UPDATE user_photo set status = ? where id = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, photoStatus.getPhotoStatus());
			pst.setInt(2, id);
			
			int rs = pst.executeUpdate();
			if(rs >0 ) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int addUserPhoto(UserPhoto userPhoto) {
		int id;
		try(Connection conn = cu.getConnection()){
			String sql = "INSERT INTO user_photo VALUES (default, ?, ?, ?)";
			String[] keys = {"id"};
			PreparedStatement pst = conn.prepareStatement(sql, keys);
			pst.setString(2, userPhoto.getPhotoCredit());
			pst.setInt(3, userPhoto.getUserId());
			pst.setInt(4, userPhoto.getPhotoStatus().getPhotoStatusId());
			
			ResultSet rs = pst.getGeneratedKeys();
			
			if(rs.next()) {
				id = rs.getInt(1);
			} else {
				id = 0;}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return id;
	}
	
	public PhotoStatus getPhotoStatusByName(String name) {
		
		try(Connection conn = cu.getConnection()){
			String sql = "Select id from photo_status where name = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				PhotoStatus status = new PhotoStatus(rs.getInt("id"), name);
				return status;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

}
