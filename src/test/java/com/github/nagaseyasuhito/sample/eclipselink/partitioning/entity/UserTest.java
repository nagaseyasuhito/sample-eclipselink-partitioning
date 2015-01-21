package com.github.nagaseyasuhito.sample.eclipselink.partitioning.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lombok.Cleanup;

import org.junit.Test;

public class UserTest {

	@Test
	public void persistSuccess() throws Exception {
		@Cleanup
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sample-eclipselink-partitioning");

		@Cleanup
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();
		for (int i = 0; i < 100; i++) {
			User user = new User();
			user.setName(String.format("name%03d", i));
			user.setPassword("password");
			entityManager.persist(user);
		}
		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		System.out.println(entityManager.createQuery("from User u where u.id = :id", User.class).setParameter("id", 10).getResultList());
		entityManager.getTransaction().commit();
	}
}
