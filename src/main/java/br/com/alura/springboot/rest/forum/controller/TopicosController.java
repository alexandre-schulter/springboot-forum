package br.com.alura.springboot.rest.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.springboot.rest.forum.controller.dto.TopicoDTO;
import br.com.alura.springboot.rest.forum.controller.dto.TopicoDetalhadoDTO;
import br.com.alura.springboot.rest.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.springboot.rest.forum.controller.form.TopicoForm;
import br.com.alura.springboot.rest.forum.modelo.Topico;
import br.com.alura.springboot.rest.forum.repository.CursoRepository;
import br.com.alura.springboot.rest.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	//idealmente usar cache pra entidades raramente atualizadas
	//entidades muito atualizadas o desempenho pode cair por causa da necessidade de atualizar e invalidar o cache toda vez
	@Cacheable(value = "listaDeTopicos")
	public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso, 
			/*@RequestParam int pagina, @RequestParam int qtde, @RequestParam String ordenacao*/
			@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		
		//Pageable paginacao = PageRequest.of(pagina, qtde, Direction.DESC, ordenacao);
		
		if(nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDTO.from(topicos);
		}else {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
			return TopicoDTO.from(topicos);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TopicoDetalhadoDTO> detalhar(@PathVariable Long id) {
		//return new TopicoDetalhadoDTO(topicoRepository.getById(id));
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) 
			return ResponseEntity.ok(new TopicoDetalhadoDTO(topico.get()));
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.toEntity(cursoRepository);
		//Topico topico = Topico.from(form);
		
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		Optional<Topico> topicoExistente = topicoRepository.findById(id);
		if(topicoExistente.isPresent()) {
			Topico topicoAtualizado = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDTO(topicoAtualizado));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> topicoExistente = topicoRepository.findById(id);
		if(topicoExistente.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();	
		}
		return ResponseEntity.notFound().build();
	}
	
}
	