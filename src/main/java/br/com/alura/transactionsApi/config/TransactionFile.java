package br.com.alura.transactionsApi.config;

import lombok.Data;
import lombok.NoArgsConstructor;


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
	public String getBancoOrigem() {
		return bancoOrigem;
	}
	public void setBancoOrigem(String bancoOrigem) {
		this.bancoOrigem = bancoOrigem;
	}
	public String getAgenciaOrigem() {
		return agenciaOrigem;
	}
	public void setAgenciaOrigem(String agenciaOrigem) {
		this.agenciaOrigem = agenciaOrigem;
	}
	public String getContaOrigem() {
		return contaOrigem;
	}
	public void setContaOrigem(String contaOrigem) {
		this.contaOrigem = contaOrigem;
	}
	public String getBancoDestino() {
		return bancoDestino;
	}
	public void setBancoDestino(String bancoDestino) {
		this.bancoDestino = bancoDestino;
	}
	public String getAgenciaDestino() {
		return AgenciaDestino;
	}
	public void setAgenciaDestino(String agenciaDestino) {
		AgenciaDestino = agenciaDestino;
	}
	public String getContaDestino() {
		return contaDestino;
	}
	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}
	public Double getValorTransacao() {
		return valorTransacao;
	}
	public void setValorTransacao(Double valorTransacao) {
		this.valorTransacao = valorTransacao;
	}
	public String getDataHoraTransacao() {
		return dataHoraTransacao;
	}
	public void setDataHoraTransacao(String dataHoraTransacao) {
		this.dataHoraTransacao = dataHoraTransacao;
	}
    
    
    
}
