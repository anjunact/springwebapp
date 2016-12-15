package com.springcookbook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springcookbook.entity.Post;
import com.springcookbook.entity.User;

@Repository
@Transactional
public class UserDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void delete(User user) {
		String sql = "delete from user where id=?";
		getJdbcTemplate().update(sql, user.getId());
	}
	 public long countMinorUsers() {
	     String sql = "select count(*) from age < 18";
	     return jdbcTemplate.queryForObject(sql, Long.class);
	}
//	 public void add(List<User> userList) {
//	     String sql = "insert into user (first_name, age) values (?, ?)";
//	     List<Object[]> userRows = new ArrayList<Object[]>();
//	     for (User user : userList) {
//	           userRows.add(new Object[] {user.getFirstName(),
//	   user.getAge()});
//	}
//	      jdbcTemplate.batchUpdate(sql, userRows);
//	   }
	public void save(User user) {
		if (user.getId() == null) {
			add(user);
		} else {
			update(user);
		}
	}

	public void add(User user) {
		String sql = "insert into user (first_name, age) values (?, ?)";
		jdbcTemplate.update(sql, user.getFirstName(), user.getAge());
	}

	public User findById(Long id) {
		String sql = "select * from user where id=?";
		User user = jdbcTemplate.queryForObject(sql, new Object[] { id }, new UserMapper());
		return user;
	}

	// public List<User> findAll() {
	// String sql = "select * from user";
	// List<User> userList = jdbcTemplate.query(sql, new UserMapper());
	// return userList;
	// }

	private class UserMapper implements RowMapper<User> {
		public User mapRow(ResultSet row, int rowNum) throws SQLException {
			User user = new User();
			user.setId(row.getLong("id"));
			user.setFirstName(row.getString("first_name"));
			user.setAge(row.getInt("age"));
			return user;
		}
	}

	public List<User> findAll() {
		String sql = "select u.id, u.first_name, u.age, p.id as p_id, p.title as p_title,p.date as p_date from user u left join post p on p.user_id = u.id order by u.id asc, p.date desc";
		return jdbcTemplate.query(sql, new UserWithPosts());
	}

	private class UserWithPosts implements ResultSetExtractor<List<User>> {
		public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Long, User> userMap = new ConcurrentHashMap<Long, User>();
			User u = null;
			while (rs.next()) {
				// user already in map?
				Long id = rs.getLong("id");
				u = userMap.get(id);
				// if not, add it
				if (u == null) {
					u = new User();
					u.setId(id);
					u.setFirstName(rs.getString("first_name"));
					u.setAge(rs.getInt("age"));
					userMap.put(id, u);
				}
				// create post if there's one
				Long postId = rs.getLong("p_id");
				if (postId > 0) {
					System.out.println("add post id=" + postId);
					Post p = new Post();
					p.setId(postId);
					p.setTitle(rs.getString("p_title"));
					p.setDate(rs.getDate("p_date"));
					p.setUser(u);
					// u.getPosts().add(p);
				}
			}
			return new LinkedList<User>(userMap.values());
		}
	}

	public void update(User user) {
		String sql = "update user set first_name=?, age=? where id=?";
		jdbcTemplate.update(sql, user.getFirstName(), user.getAge(), user.getId());
	}
}
