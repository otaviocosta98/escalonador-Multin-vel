package com.usjt.model;

public class Processo {

	private String nome;
	private Integer tempo;
	private Integer tempoParada;
	private Integer tempoRestante;
	private Integer tempoChegada;
	private String status;

	public Processo(String nome, Integer tempo, Integer tempoChegada) {
		this.nome = nome;
		this.tempo = tempo;
		this.tempoRestante = tempo;
		this.tempoChegada = tempoChegada;
		this.status = "Fora";
	}

	public Processo(String nome, Integer tempo) {
		this.nome = nome;
		this.tempo = tempo;
		this.tempoRestante = tempo;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public Integer getTempoParada() {
		return tempoParada;
	}

	public void setTempoParada(Integer tempoParada) {
		this.tempoParada = tempoParada;
	}

	public Integer getTempoRestante() {
		return tempoRestante;
	}

	public void setTempoRestante(Integer tempoRestante) {
		this.tempoRestante = tempoRestante;
	}

	public Integer getTempoChegada() {
		return tempoChegada;
	}

	public void setTempoChegada(Integer tempoChegada) {
		this.tempoChegada = tempoChegada;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Processo [nome=" + nome + ", tempo=" + tempo + ", tempoRestante=" + tempoRestante + "]";
	}

}
