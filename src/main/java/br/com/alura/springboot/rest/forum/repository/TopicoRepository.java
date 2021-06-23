package br.com.alura.springboot.rest.forum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.springboot.rest.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	//Esse metodo nesse padrao findByClasseAtributoEtc. gera uma implementacao automaticamente
	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);

	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso")String nomeCurso);
}
