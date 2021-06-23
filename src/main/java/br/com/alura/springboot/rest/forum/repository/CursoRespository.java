package br.com.alura.springboot.rest.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.springboot.rest.forum.modelo.Curso;

public interface CursoRespository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nome);

}
