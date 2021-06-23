package br.com.alura.springboot.rest.forum.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.springboot.rest.forum.modelo.StatusTopico;
import br.com.alura.springboot.rest.forum.modelo.Topico;

public class TopicoDetalhadoDTO extends TopicoDTO {

	private String nomeAutor;
	private StatusTopico status;
	private List<RespostaDTO> respostas;
	
	public TopicoDetalhadoDTO(Topico topico) {
		super(topico);
		this.nomeAutor = topico.getAutor().getNome();
		this.status = topico.getStatus();
		this.respostas = new ArrayList<>();
		this.respostas.addAll(topico.getRespostas().stream().map(RespostaDTO::new).collect(Collectors.toList()));		
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public List<RespostaDTO> getRespostas() {
		return respostas;
	}

	
}
