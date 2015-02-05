package com.github.nagaseyasuhito.sample.eclipselink.partitioning;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lombok.Cleanup;

import org.junit.Test;

import com.github.nagaseyasuhito.sample.eclipselink.partitioning.entity.User;

public class PartitioningTest {
	@Test
	public void partitioningSuccess() throws Exception {
		@Cleanup
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sample-eclipselink-partitioning-test");

		@Cleanup
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();
		for (int i = 0; i < 16; i++) {
			User user = new User();
			user.setName(String.format("name%03d", i));
			user.setPassword("password");
			entityManager.persist(user);
			entityManager.flush();
		}
		entityManager.getTransaction().commit();

		entityManagerFactory.getCache().evictAll();
		entityManager.clear();

		entityManager.getTransaction().begin();
		entityManager.createQuery("from User u where u.id = :id", User.class).setParameter("id", 3L).getSingleResult();
		entityManager.createQuery("from User u where u.name = :name", User.class).setParameter("name", "name004").getSingleResult();
		entityManager.getTransaction().commit();
	}
}
