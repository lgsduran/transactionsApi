package br.com.alura.transactionsApi.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@Entity
@Table(	name = "transaction")
public class Transaction {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "banco_origem")
    private String bancoOrigem;

    @Column(name = "agencia_origem")
    private String agenciaOrigem;

    @Column(name = "conta_origem")
    private String contaOrigem;

    @Column(name = "banco_destino")
    private String bancoDestino;

    @Column(name = "agencia_destino")
    private String AgenciaDestino;

    @Column(name = "conta_destino")
    private String contaDestino;

    @Column(name = "valor_transacao")
    private Double valorTransacao;

    @Column(name = "data_hora_transacao")
    private String dataHoraTransacao;
    
    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;	
}
