package edu.ufpe.cin.vlimperial.repository;

import edu.ufpe.cin.vlimperial.domain.Locacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Locacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

    @Query(value = "select distinct locacao from Locacao locacao left join fetch locacao.itemLocados",
        countQuery = "select count(distinct locacao) from Locacao locacao")
    Page<Locacao> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct locacao from Locacao locacao left join fetch locacao.itemLocados")
    List<Locacao> findAllWithEagerRelationships();

    @Query("select locacao from Locacao locacao left join fetch locacao.itemLocados where locacao.id =:id")
    Optional<Locacao> findOneWithEagerRelationships(@Param("id") Long id);

}
