package edu.ufpe.cin.vlimperial.repository;

import edu.ufpe.cin.vlimperial.domain.ItemFilme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemFilme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemFilmeRepository extends JpaRepository<ItemFilme, Long> {

}
