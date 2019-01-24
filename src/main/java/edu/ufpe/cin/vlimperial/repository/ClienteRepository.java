package edu.ufpe.cin.vlimperial.repository;

import edu.ufpe.cin.vlimperial.domain.Cliente;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cliente entity.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	List<Cliente> findByCliente(Cliente cliente);
}
