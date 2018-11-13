package org.sid.DAO;

import org.sid.entities.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestRepository extends JpaRepository<Test, String>{
	
	
	@Query("select t from Test t")
	public Page<Test> listTest(Pageable pageable);

}
