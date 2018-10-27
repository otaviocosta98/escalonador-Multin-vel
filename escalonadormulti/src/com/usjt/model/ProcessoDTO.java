package com.usjt.model;

public class ProcessoDTO {

	private String nmProcesso;
	private Integer timeProcesso;
	private Integer tempoRestante;

	public ProcessoDTO(String nmProcesso, Integer timeProcesso) {
		this.nmProcesso = nmProcesso;
		this.timeProcesso = timeProcesso;
		this.tempoRestante = timeProcesso;
	}

	public String getNmProcesso() {
		return nmProcesso;
	}

	public void setNmProcesso(String nmProcesso) {
		this.nmProcesso = nmProcesso;
	}

	public Integer getTimeProcesso() {
		return timeProcesso;
	}

	public void setTimeProcesso(Integer timeProcesso) {
		this.timeProcesso = timeProcesso;
	}

	public Integer getTempoRestante() {
		return tempoRestante;
	}

	public void setTempoRestante(Integer tempoRestante) {
		this.tempoRestante = tempoRestante;
	}

	@Override
	public String toString() {
		return "ProcessoDTO [nmProcesso=" + nmProcesso + ", timeProcesso=" + timeProcesso + ", tempoRestante="
				+ tempoRestante + "]";
	}

}
