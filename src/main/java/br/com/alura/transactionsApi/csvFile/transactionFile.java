package br.com.alura.transactionsApi.csvFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class transactionFile {

    private String bancoOrigin;
    private int agenciaOrigin;
    private int contaOrigin;
    private String bancoDestino;
    private int AgenciaDestino;
    private int contaDestino;
    private Double valorTransacao;
    private String dataHoraTransacao; 
    
}
