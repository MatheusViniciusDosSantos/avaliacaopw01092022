package br.com.matheusvinicius.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.matheusvinicius.domain.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
	@Query(value = "select s from Servico s where s.status=?1")
	Page<Servico> findByStatus(String status, Pageable page);
	
	
	Page<Servico> findAll(Pageable page);
}

