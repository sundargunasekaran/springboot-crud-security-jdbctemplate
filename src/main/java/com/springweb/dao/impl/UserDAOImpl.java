package com.springweb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springweb.dao.UserDAO;
import com.springweb.model.UserModel;
@Transactional
@Repository
public class UserDAOImpl implements UserDAO{
	
	private SimpleJdbcInsert insert;
	
	 @Autowired
	 private JdbcTemplate webJdbcTemplate;
	 
	 @Autowired
	 private AuthenticationManager authenticationManager;
	 
	 public void setDataSource(DataSource dataSource) {
	        this.webJdbcTemplate = new JdbcTemplate(dataSource);
	        this.insert = 
	                new SimpleJdbcInsert(dataSource)
	                 .withTableName("loc_users")
	                 .usingGeneratedKeyColumns("user_id");
	    }

	@Override
	public List<UserModel> getAllUserDetails() {
		try{
			List<UserModel> userModelList = new ArrayList<UserModel>();
			 String sql = "SELECT lu.user_id,lu.user_name,lu.password,lu.email_id,lr.role_id,group_concat(lr.role_name) as role_name FROM  loc_users lu "+
						"left join loc_user_roles lur on lur.user_id = lu.user_id "+
						"left join loc_roles lr on lr.role_id = lur.role_id group by lu.user_id ";


			List<Map<String, Object>> rows = webJdbcTemplate.queryForList(sql );
			Map<String, String> objMap = new HashMap<String, String>();
			for (Map row : rows) {
				UserModel usermodel = new UserModel();
				usermodel.setUserId((int)row.get("user_id"));
				usermodel.setUsername(row.get("user_name").toString());
				usermodel.setEmailId(row.get("email_id").toString());
				usermodel.setRole(row.get("role_name") != null && !row.get("role_name").toString().trim().equals("") ? row.get("role_name").toString() : "");
				userModelList.add(usermodel);
			}
			return userModelList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserModel findUserId(UserModel user) {
        String sql = "SELECT lu.user_id,lu.user_name,lu.password,lu.email_id,lr.role_id,group_concat(lr.role_name) as role_name FROM  loc_users lu "+
						"left join loc_user_roles lur on lur.user_id = lu.user_id "+
						"left join loc_roles lr on lr.role_id = lur.role_id ";
		try{
			    String whereSql = "  ";
			    Map<String,Object> qryMap = new LinkedHashMap<String,Object>();
		        ArrayList<Object> paramList = new ArrayList<Object>();
		        if(user.getUsername() != null && !user.getUsername().trim().equals("")){
		        	qryMap.put(" lu.user_name = ? ", user.getUsername());
		        }
		        if(user.getPassword() != null && !user.getPassword().trim().equals("")){
		        	qryMap.put(" lu.password = ? ", user.getPassword());
		        }
		        if(user.getUserId() > 0 ){
		        	qryMap.put(" lu.user_id = ? ", user.getUserId());
		        }
		        Map<String,ArrayList<Object>> retmap = this.getWhereClause(qryMap);
		        for (Map.Entry<String, ArrayList<Object>> entry : retmap.entrySet()) {
				    whereSql = entry.getKey();
				    paramList = entry.getValue();
				}
		        sql += whereSql;
		        String groupBy = " group by lu.user_id ";
		        //if(paramList.size() > 0){
		        	Object[] param = paramList.toArray(new Object[paramList.size()]);
		        	UserModel userModel = webJdbcTemplate.queryForObject(
		                    sql+groupBy,
		                    param,
		                    new RowMapper<UserModel>() {
		                        public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		                        	UserModel bean = new UserModel();
		                            bean.setUserId(rs.getInt("user_id")); 
		                            bean.setUsername(rs.getString("user_name"));
		                            bean.setPassword(rs.getString("password"));
		                            bean.setEmailId(rs.getString("email_id"));
		                            bean.setRole(rs.getString("role_name"));
		                            /*List<String> list = Arrays.asList(rs.getString("role_name"));
		                            String result = String.join(",", list);*/
		                            return bean;
		                        }
		                    });
		        	return userModel;
		        /*}else{
		        	 return null;
		        }*/
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
      
        
    }

	@Override
	public void addUser(UserModel userModel) {
		String query = "INSERT INTO loc_users( user_name, email_id, password, created_date) VALUES(?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		webJdbcTemplate.update(
		    new PreparedStatementCreator() {
		    	@Override
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"user_name","email_id","password","created_date"});
		            ps.setString(1, userModel.getUsername());
		            ps.setString(2, userModel.getEmailId());
		            ps.setString(3, userModel.getPassword());
		            ps.setDate(4, getCurrentDate());
		            return ps;
		        }

				
		    },
		    keyHolder);
		String rolequery = "INSERT INTO loc_user_roles( user_id, role_id) VALUES(?, ?)";
		int ret = webJdbcTemplate.update(rolequery, keyHolder.getKey(), userModel.getRoleId());
	}

	@Override
	public void updateUser(UserModel userModel) {
		String query = "UPDATE loc_users SET user_name=?, email_id=?, password=? WHERE user_id=?";
		String rolequery = "";
		int ret = webJdbcTemplate.update(query, userModel.getUsername(), userModel.getEmailId(), userModel.getPassword(), userModel.getUserId());
		if(ret > 0){
			String sql = "SELECT role_id FROM loc_user_roles WHERE user_id = ?";
			try{
				String roleId = (String) webJdbcTemplate.queryForObject(
			            sql, new Object[] {userModel.getUserId() }, String.class);
				rolequery = "update loc_user_roles set role_id = ? where user_id = ?";
			}catch(EmptyResultDataAccessException e){
				rolequery = "INSERT INTO loc_user_roles( role_id,user_id) VALUES(?, ?)";
			}
		   /* String roleId = (String) webJdbcTemplate.queryForObject(
		            sql, new Object[] {userModel.getUserId() }, String.class);
		    if(roleId != null){
		    	rolequery = "update loc_user_roles set role_id = ? where user_id = ?";
		    }else{
		    	rolequery = "INSERT INTO loc_user_roles( role_id,user_id) VALUES(?, ?)";
		    }*/
		    ret = webJdbcTemplate.update(rolequery, userModel.getRoleId(),userModel.getUserId());
		}
		
	}

	@Override
	public void deleteUser(int id) {
		String query = "DELETE FROM loc_user_roles WHERE user_id=?";
		webJdbcTemplate.update(query, id);
		String query1 = "DELETE FROM loc_users WHERE user_id=?";
		webJdbcTemplate.update(query1, id);
		
	}

	public boolean validateUserLogin(UserModel user) {
		//UserModel u = this.findUserId(user);
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		if(authentication.getPrincipal() != null ){
			return true;
		}
		/*if(u != null && u.getUsername() != null && !u.getUsername().trim().equals("")){
			
		}*/
		return false;
	}

	private Map<String,ArrayList<Object>> getWhereClause(Map<String, Object> queryMap) {
		ArrayList<Object> param = new ArrayList<Object>();
		Map<String,ArrayList<Object>> plm = new LinkedHashMap<String,ArrayList<Object>>();
		int i = 1;
		StringBuffer whereClause = new StringBuffer ("");
		for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
		   // System.out.println("--> "+entry.getKey()+"----"+entry.getValue());
		    if (queryMap.size() <= 0)
				return null;
		    if(i == 1 && queryMap.size() >= 1){
		    	whereClause.append (" WHERE ").append (entry.getKey());
				param.add((Object) queryMap.get(entry.getKey()));
		    }else{
		    	whereClause.append (" AND ").append(entry.getKey());
				param.add((Object) queryMap.get(entry.getKey()));
		    }
			
			i++;
		}
		plm.put(whereClause.toString(), param);
		return plm;
	}
	
	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}

}
