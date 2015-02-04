package com.github.nagaseyasuhito.sample.eclipselink.partitioning.api;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.IntStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import lombok.SneakyThrows;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.nagaseyasuhito.sample.eclipselink.partitioning.entity.User;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

public class UserResourceST {

	private Client client;
	private URL url;

	@Before
	@SneakyThrows
	public void before() {
		this.url = new URL(ResourceBundle.getBundle("stress-test").getString("url"));
		this.client = ClientBuilder.newClient();
	}

	@After
	public void after() {
		this.client.close();
	}

	@Test
	public void stressTest() throws Exception {
		IntStream.range(0, 1000).forEach(this::serialStressTest);
	}

	private void serialStressTest(int i) {
		try {
			String name = UUID.randomUUID().toString();

			WebTarget target = this.client.target(this.url.toURI()).path("/api/user").path(name).queryParam("password", "password");
			User user = target.request().method("POST").readEntity(User.class);
			assertThat(user.getName(), is(name));

			target = this.client.target(this.url.toURI()).path("/api/user").path(name);
			user = target.request().method("GET").readEntity(User.class);
			assertThat(user.getName(), is(name));

			target = this.client.target(this.url.toURI()).path("/api/user").path(name);
			user = target.request().method("DELETE").readEntity(User.class);
			assertThat(user.getName(), is(name));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
