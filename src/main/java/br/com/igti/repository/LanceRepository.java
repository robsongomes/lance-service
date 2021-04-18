package br.com.igti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.igti.model.Lance;

public interface LanceRepository extends JpaRepository<Lance, Long> {

	public List<Lance> findByLoteId(Long loteId);
}
