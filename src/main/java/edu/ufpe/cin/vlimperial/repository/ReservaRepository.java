package edu.ufpe.cin.vlimperial.repository;

import edu.ufpe.cin.vlimperial.domain.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Reserva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query(value = "select distinct reserva from Reserva reserva left join fetch reserva.midiaDesejadas",
        countQuery = "select count(distinct reserva) from Reserva reserva")
    Page<Reserva> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct reserva from Reserva reserva left join fetch reserva.midiaDesejadas")
    List<Reserva> findAllWithEagerRelationships();

    @Query("select reserva from Reserva reserva left join fetch reserva.midiaDesejadas where reserva.id =:id")
    Optional<Reserva> findOneWithEagerRelationships(@Param("id") Long id);

}
