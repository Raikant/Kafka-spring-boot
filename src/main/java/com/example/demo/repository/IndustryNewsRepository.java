package com.example.demo.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.IndustryNews;

@Repository
public interface IndustryNewsRepository extends JpaRepository<IndustryNews, Long> {

	@Transactional
	@Modifying
	@Query(value = "delete from IndustryNews")
	void deleteAllEntities();

}
