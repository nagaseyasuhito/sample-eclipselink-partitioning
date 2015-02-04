package com.github.nagaseyasuhito.sample.eclipselink.partitioning.service;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
public class UserServiceIT {

	@Deployment
	public static Archive<?> createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class);
		war.addPackages(true, "com.github.nagaseyasuhito.sample.eclipselink.partitioning");
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		war.addAsResource("META-INF/persistence.xml");
		return war;
	}

	@Inject
	private UserService userService;

	@Test
	public void createSuccess() throws Exception {
		User user = this.userService.create("userService-createSuccess", "password");

		assertThat(user.getId(), is(not(nullValue())));
	}

	@Test
	public void showSuccess() throws Exception {
		User user = this.userService.create("userService-showSuccess", "password");

		assertThat(this.userService.show("userService-showSuccess"), is(user));
	}

	@Test
	public void deleteSuccess() throws Exception {
		User user = this.userService.create("userService-deleteSuccess", "password");

		assertThat(this.userService.delete("userService-deleteSuccess"), is(user));
	}
}
