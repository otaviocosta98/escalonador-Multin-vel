package usjt.esc.memoria;

import java.util.ArrayList;
import java.util.List;

import com.usjt.model.Processo;

public class Particao {

	private Integer tamanho;
	private Integer tamanhoAtual;
	private List<Processo> processos;

	public Particao() {

	}

	public Particao(Integer tamanho) {
		this.tamanho = tamanho;
		this.tamanhoAtual = tamanho;
		processos = new ArrayList<>();
	}

	public Integer getTamanhoAtual() {
		return tamanhoAtual;
	}

	public void setTamanhoAtual(Integer tamanhoAtual) {
		this.tamanhoAtual = tamanhoAtual;
	}

	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

	public Processo addProcesso(Processo p) {
		if (p.getTempo() < this.getTamanhoAtual()) {
			this.processos.add(p);
			return null;
		} else {
			return p;
		}

	}

	public Processo validaEspaco(Processo p) {
		if (p.getTempo() < this.getTamanhoAtual()) {
			return null;
		} else {
			return p;
		}

	}

}
