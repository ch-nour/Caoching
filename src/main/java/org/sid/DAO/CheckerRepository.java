package org.sid.DAO;

import org.sid.entities.Checker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CheckerRepository extends JpaRepository<Checker, Long>{

	@Query("select c from Checker c where c.code = :x")
	public Checker getByCode(@Param("x") String code);
}
