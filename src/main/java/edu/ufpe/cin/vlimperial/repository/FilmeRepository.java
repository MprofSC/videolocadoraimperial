package edu.ufpe.cin.vlimperial.repository;

import edu.ufpe.cin.vlimperial.domain.Filme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Filme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

}
