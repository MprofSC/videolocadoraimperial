package com.sde.videolocadoraimperial.repository;

import com.sde.videolocadoraimperial.domain.Filme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Filme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

}
