package com.github.nagaseyasuhito.sample.eclipselink.partitioning.dao;

import com.github.nagaseyasuhito.sample.eclipselink.partitioning.entity.User;
import com.github.nagaseyasuhito.sample.eclipselink.partitioning.entity.User_;

public class UserDao extends BaseDao<User, Long> {
	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}

	public User findByName(String name) {
		return this.getSingleResult((b, q, r) -> q.select(r).where(b.equal(r.get(User_.name), name)));
	}
}
