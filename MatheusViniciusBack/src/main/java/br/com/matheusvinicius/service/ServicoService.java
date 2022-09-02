package br.com.matheusvinicius.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.matheusvinicius.domain.Servico;
import br.com.matheusvinicius.exception.BadResourceException;
import br.com.matheusvinicius.exception.ResourceAlreadyExistsException;
import br.com.matheusvinicius.exception.ResourceNotFoundException;
import br.com.matheusvinicius.repository.ServicoRepository;

@Service
public class ServicoService {
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	private boolean existsById(Long id) {
		return servicoRepository.existsById(id);
	}
	
	public Servico findById(Long id) throws ResourceNotFoundException {
		Servico servico = servicoRepository.findById(id).orElse(null);
		
		if(servico == null) {
			throw new ResourceNotFoundException("Servico não encontrado com o id: " + id);
		} else {
			return servico;
		}
	}
	
	public Page<Servico> findAll(Pageable pageable) {
		return servicoRepository.findAll(pageable);
	}
	
	public Page<Servico> findAllByStatus(String status, Pageable page) {
		Page<Servico> servicos = servicoRepository.findByStatus(status, page);
		return servicos;
	}
	
	public Servico save(Servico servico) throws BadResourceException, ResourceAlreadyExistsException {
		if(!StringUtils.isEmpty(servico.getNomeCliente())) {
			if(existsById(servico.getId())) {
				throw new ResourceAlreadyExistsException("Servico com id: " + servico.getId() + " já existe.");
			}
			servico = definirStatus(servico);
			Servico servicoNovo = servicoRepository.save(servico);			
			return servicoNovo;
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar Serviço");
			exe.addErrorMessage("Servico esta vazio ou nulo");
			throw exe;
		}
	}
	
	public void update(Servico servico) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(servico.getNomeCliente())) {
			if (!existsById(servico.getId())) {
				throw new ResourceNotFoundException("Servico não encontrado com o id: " + servico.getId());
			}
			servico = definirStatus(servico);
			servicoRepository.save(servico);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar Serviço");
			exe.addErrorMessage("Servico esta vazio ou nulo");
			throw exe;
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Servico não encontrado com o id: " + id);
		} else {
			servicoRepository.deleteById(id);
		}
	
	}  public Long count() {
		return servicoRepository.count();
	}
	
	public Servico definirStatus(Servico servico) {
		if (servico.getValorPago() == null || servico.getValorPago() == 0.0) {
			servico.setStatus("pendente");
		} else if (servico.getValorPago() > 0.0) {
			servico.setStatus("realizado");
			if (servico.getDataPagamento() == null) {
				servico.setDataPagamento(Calendar.getInstance().getTime());
			}
		} else {
			servico.setStatus("cancelado");
			servico.setValorServico(null);
		}
		return servico;
	}
	
}
