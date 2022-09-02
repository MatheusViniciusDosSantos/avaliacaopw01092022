package br.com.matheusvinicius.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.matheusvinicius.domain.Servico;
import br.com.matheusvinicius.exception.BadResourceException;
import br.com.matheusvinicius.exception.ResourceAlreadyExistsException;
import br.com.matheusvinicius.exception.ResourceNotFoundException;
import br.com.matheusvinicius.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
@Tag(name = "servico", description = "API de Serviço")
public class ServicoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ServicoService servicoService;
	
	@Operation(summary = "Busca servicos", description = "Buscar todos os serviços", tags = {"servico"})
	@GetMapping(value = "/servico", consumes = 
			MediaType.APPLICATION_JSON_VALUE, produces = 
				MediaType.APPLICATION_JSON_VALUE)

	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Page<Servico>> findAll(
			@Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
			 Pageable pageable)	{
		
		return ResponseEntity.ok(servicoService.findAll(pageable));
		
	}
	
	@Operation(summary = "Busca servicos pendentes", description = "Buscar todos os serviços pendentes", tags = {"servico"})
	@GetMapping(value = "/servicosStatus/{status}", consumes = 
			MediaType.APPLICATION_JSON_VALUE, produces = 
				MediaType.APPLICATION_JSON_VALUE)

	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Page<Servico>> findAllBysStatus(
			@PathVariable() String status,
			@Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
			 Pageable pageable)	{
		
		return ResponseEntity.ok(servicoService.findAllByStatus(status ,pageable));
		
	}
	
	@Operation(summary = "Busca ID", description = "Buscar servico por ID", tags = {"servico"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(schema = @Schema(implementation = Servico.class))),
			@ApiResponse(responseCode = "404", description = "Servico não encontrado")
	})
	@GetMapping(value = "/servico/{id}", produces =
			MediaType.APPLICATION_JSON_VALUE)

	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Servico> findServicoById(@PathVariable long id) {
		try {
			Servico servico = servicoService.findById(id);
			return ResponseEntity.ok(servico);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
	
	@Operation(summary = "Adicionar serviço", description = "Adicionar novo serviço informado no banco de dados", tags = {"servico"})
	@PostMapping(value = "/servico")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Servico> addServico(@RequestBody Servico servico) throws URISyntaxException {
		try {
			Servico novoServico = servicoService.save(servico);
			
			return ResponseEntity.created(new URI("/api/servico" + novoServico.getId())).body(servico);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@Operation(summary = "Alterar Serviço", description = "Alterar valores do serviço com id selecionado", tags = {"servico"})
	@PutMapping(value = "/servico")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Servico> updateServico(@Valid @RequestBody Servico servico) {
		try {
			servicoService.update(servico);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(summary = "Deletar serviço", description = "Deletar serviço com o ID informado", tags = {"servico"})
	@DeleteMapping(path = "/servico/{id}")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Void> deleteServicoById(@PathVariable long id) {
		try {
			servicoService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

}