package com.github.nagaseyasuhito.sample.eclipselink.partitioning.api;

import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
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
@RunAsClient
public class UserResourceIT {
	@Deployment
	public static Archive<?> createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class);
		war.addPackages(true, "com.github.nagaseyasuhito.sample.eclipselink.partitioning");
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		war.addAsResource("META-INF/persistence.xml");
		return war;
	}

	@ArquillianResource
	private URL url;

	@Test
	public void createSuccess() throws Exception {
		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(this.url.toURI()).path("/api/user").path("userResource-createSuccess").queryParam("password", "password");
		User user = target.request().method("POST").readEntity(User.class);
		assertThat(user.getName(), is("userResource-createSuccess"));
	}

	@Test
	public void showSuccess() throws Exception {
		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(this.url.toURI()).path("/api/user").path("userResource-showSuccess").queryParam("password", "password");
		target.request().method("POST").readEntity(User.class);

		target = client.target(this.url.toURI()).path("/api/user").path("userResource-showSuccess");
		User user = target.request().method("GET").readEntity(User.class);
		assertThat(user.getName(), is("userResource-showSuccess"));
	}

	@Test
	public void deleteSuccess() throws Exception {
		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(this.url.toURI()).path("/api/user").path("userResource-deleteSuccess").queryParam("password", "password");
		target.request().method("POST").readEntity(User.class);

		target = client.target(this.url.toURI()).path("/api/user").path("userResource-deleteSuccess");
		User user = target.request().method("DELETE").readEntity(User.class);
		assertThat(user.getName(), is("userResource-deleteSuccess"));
	}
}
