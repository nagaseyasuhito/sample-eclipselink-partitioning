package com.github.nagaseyasuhito.sample.eclipselink.partitioning.dao;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.nagaseyasuhito.sample.eclipselink.partitioning.entity.User;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

@RunWith(Arquillian.class)
public class UserDaoIT {

	@Deployment
	public static Archive<?> createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class);
		war.addPackages(true, "com.github.nagaseyasuhito.sample.eclipselink.partitioning");
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		war.addAsResource("META-INF/persistence.xml");
		return war;
	}

	@Inject
	private UserDao userDao;

	@Test
	@Transactional
	public void persistSuccess() throws Exception {
		User user = new User();
		user.setName("userDao-persistSuccess");
		user.setPassword("password");
		this.userDao.persist(user);

		assertThat(user.getId(), is(not(nullValue())));
	}

	@Test
	@Transactional
	public void findByIdSuccess() throws Exception {
		User user = new User();
		user.setName("userDao-findByIdSuccess");
		user.setPassword("password");
		this.userDao.persist(user);

		assertThat(this.userDao.findById(user.getId()), is(user));
	}

	@Test
	@Transactional
	public void findByNameSuccess() throws Exception {
		User user = new User();
		user.setName("userDao-findByNameSuccess");
		user.setPassword("password");
		this.userDao.persist(user);

		assertThat(this.userDao.findByName("userDao-findByNameSuccess"), is(user));
	}

	@Test
	@Transactional
	public void removeSuccess() throws Exception {
		User user = new User();
		user.setName("userDao-removeSuccess");
		user.setPassword("password");
		this.userDao.persist(user);

		this.userDao.remove(user);
	}
}
