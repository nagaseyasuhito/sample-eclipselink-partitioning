package com.github.nagaseyasuhito.sample.eclipselink.partitioning.service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import com.github.nagaseyasuhito.sample.eclipselink.partitioning.dao.UserDao;
import com.github.nagaseyasuhito.sample.eclipselink.partitioning.entity.User;

@Transactional
public class UserService {
	@Inject
	private UserDao userDao;

	public User create(@NotNull String name, @NotNull String password) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		this.userDao.persist(user);

		return user;
	}

	public User show(@NotNull String name) {
		return this.userDao.findByName(name);
	}

	public User delete(@NotNull String name) {
		User user = this.userDao.findByName(name);
		this.userDao.remove(user);
		return user;
	}
}
