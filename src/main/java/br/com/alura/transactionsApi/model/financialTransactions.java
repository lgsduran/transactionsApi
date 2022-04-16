package br.com.alura.transactionsApi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@Entity
@Table(	name = "financial_transactions")
public class financialTransactions {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "banco_origin")
    private String bancoOrigin;

    @Column(name = "agencia_origin")
    private int agenciaOrigin;

    @Column(name = "conta_origin")
    private int contaOrigin;

    @Column(name = "conta_destino")
    private String bancoDestino;

    @Column(name = "agencia_destino")
    private int AgenciaDestino;

    @Column(name = "conta_destino")
    private int contaDestino;

    @Column(name = "valor_transacao")
    private Double valorTransacao;

    @Column(name = "data_hora_transacao")
    private String dataHoraTransacao; 
    
}
