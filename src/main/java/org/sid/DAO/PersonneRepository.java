package org.sid.DAO;

import org.sid.entities.Candidat;
import org.sid.entities.Formateur;
import org.sid.entities.Personne;
import org.sid.entities.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface PersonneRepository extends JpaRepository<Personne, Long> {

	@Query("select q from Candidat q")
	public Page<Candidat> listCandidat(Pageable pageable);
	
	@Query("select p from Personne p where p.id = :x")
	public Candidat findCandidat(@Param("x") Long codeCandidat);
    
	@Modifying
	@Query("UPDATE Formateur f SET f.active	 = :active WHERE f.id = :id")
    public void activate(@Param("active") boolean state, @Param("id") Long id);
	
	@Query("select p from Formateur p where p.id = :x")
	public Formateur findFormateur(@Param("x") Long codeFormateur);
	
	@Query("select p from Personne p where p.email = :email")
	public Personne findFormateurByMail(@Param("email") String email);
	
	@Query("select q from Formateur q")
	public Page<Formateur> listFormateur(Pageable pageable);
	
	@Modifying
	@Query("UPDATE Formateur f SET f.password = :pwd WHERE f.id = :id")
	public void modpass(@Param("id")Long id,@Param("pwd") String pwd);
	
	//@Query("select count(p.id) from Personne p where p.email = :email")
	@Query("select case when (count(scen) > 0)  then true else false end from Personne scen where scen.email = :email")
	public boolean ifexist(@Param("email")String email);
	
}
