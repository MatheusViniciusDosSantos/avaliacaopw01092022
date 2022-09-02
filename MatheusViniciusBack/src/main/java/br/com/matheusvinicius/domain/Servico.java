package br.com.matheusvinicius.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Table(name = "servico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Servico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Nome do cliente do serviço", example = "José da Silva")
	private String nomeCliente;
	
	@Schema(description = "Descrição do serviço", example = "Cortar grama")
	private String descricao;
	
	@Schema(description = "Valor do serviço", example = "100.00")
	private Double valorServico;
	
	@Schema(description = "Valor pago do serviço", example = "70.00")
	private Double valorPago;
	
	@Schema(description = "Status do serviço", example = "pendente, realizado ou cancelado")
	private String status;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de início do serviço. Gerado na criação de um novo serviço")
	private Date dataInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de termino do serviço. Gerado na finalização do serviço")
	private Date dataTermino;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Pagamento do serviço.")
	private Date dataPagamento;
	
	public Servico() {}

}
