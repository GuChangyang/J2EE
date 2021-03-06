package com.itheima.dao;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.domain.User;
import com.itheima.util.DaoUtils;

public class UserDaoImpl implements UserDao {

	public void addUser(User user,Connection conn) {
		String sql = "insert into users values(null,?,?,?,?,?,?,?,null)";
		try{
			QueryRunner runner = new QueryRunner();
			runner.update(conn,sql,user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),user.getRole(),user.getState(),user.getActivecode());
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public User findUserByName(String username, Connection conn) {
		String sql = "select * from users where username = ?";
		try{
			QueryRunner runner = new QueryRunner();
			return runner.query(conn,sql, new BeanHandler<User>(User.class),username);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void delUser(int id) {
		String sql = "delete from users where id = ?";
		try{
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			runner.update(sql,id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}

	public User findUserByActivecode(String activecode) {
		String sql = "select * from users where activecode = ?";
		try{
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class),activecode);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void updateState(int id, int state) {
		String sql = "update users set state = ? where id=?";
		try{
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			runner.update(sql,state,id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}

	public User finUserByNameAndPsw(String username, String password) {
		String sql = "select * from users where username = ? and password = ?";
		try{
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class) ,username ,password);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
