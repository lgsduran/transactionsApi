package br.com.alura.transactionsApi.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class TransactionFile {

    private String bancoOrigem;
    private String agenciaOrigem;
    private String contaOrigem;
    private String bancoDestino;
    private String AgenciaDestino;
    private String contaDestino;
    private Double valorTransacao;
    private String dataHoraTransacao;
	
}
