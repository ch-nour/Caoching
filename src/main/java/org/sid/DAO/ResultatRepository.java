package org.sid.DAO;

import org.sid.entities.Resultat;
import org.sid.entities.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultatRepository extends JpaRepository<Resultat, Long>{


	@Query("select r from Resultat r")
	public Page<Resultat> listResultat(Pageable pageable);
}
