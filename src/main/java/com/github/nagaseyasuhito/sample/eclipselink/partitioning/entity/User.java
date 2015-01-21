package com.github.nagaseyasuhito.sample.eclipselink.partitioning.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.eclipse.persistence.annotations.HashPartitioning;
import org.eclipse.persistence.annotations.Partitioned;

@Entity
@Data
@EqualsAndHashCode
@Partitioned("hashPartitioningById")
@HashPartitioning(
	name = "hashPartitioningById",
	partitionColumn = @Column(name = "id"),
	connectionPools = { "partition0", "partition1", "partition2", "partition3", "partition4", "partition5", "partition6", "partition7" })
public class User implements Serializable {
	private static final long serialVersionUID = 9138208428859166924L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String password;
}
