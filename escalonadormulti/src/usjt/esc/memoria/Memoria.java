package usjt.esc.memoria;

import java.util.ArrayList;
import java.util.List;

import com.usjt.model.Processo;

public class Memoria {

	private static Integer TAMANHO_FIXO_MEMORIA = 100;

	private Integer tamanho;
	private List<Particao> particoes;
	private List<Processo> reservas;

	public Memoria() {
	}

	public Memoria(Integer tamanho, List<Particao> particoes) {
		this.tamanho = tamanho;
		this.particoes = particoes;
		reservas = new ArrayList<>();
	}

	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

	public List<Particao> getParticoes() {
		return particoes;
	}

	public void setParticoes(List<Particao> particoes) {
		this.particoes = particoes;
	}
	
	public void addReservas(Processo p ) {
		this.reservas.add(p);
	}
	
	public List<Processo> getReservas() {
		return reservas;
	}

	public void setReservas(List<Processo> reservas) {
		this.reservas = reservas;
	}

	// metodos de alocamento de processo no worstfit
	public void worstFit(List<Processo> processos) {
		// fazer uma iteracao com o tamanho da memoria, para alocar cada processo em sua
		// particao devida, ja q nao faremos runtime !
		for (int i = 0; i <= TAMANHO_FIXO_MEMORIA; i++) {

			for (Processo p : processos) { // verifica se alguns dos processos chegou em algum momento e aloca no
											// worstfit
				if (p.getTempoChegada() == i) {

					if ((this.particoes.get(0).getTamanhoAtual()
							- p.getTempo() > this.particoes.get(1).getTamanhoAtual() - p.getTempo())
							&& (this.particoes.get(0).getTamanhoAtual()
									- p.getTempo() > this.particoes.get(2).getTamanhoAtual() - p.getTempo())
							&& (this.particoes.get(0).getTamanhoAtual()
									- p.getTempo() > this.particoes.get(3).getTamanhoAtual() - p.getTempo())) {
						if (this.particoes.get(0).validaEspaco(p) == null) {

							this.particoes.get(0).addProcesso(p);
							this.particoes.get(0)
									.setTamanhoAtual(this.particoes.get(0).getTamanhoAtual() - p.getTempo());

							System.out.println(i + "- Processo " + p.getNome() + " chegou,"
									+ " e foi alocado na particao de tempo " + this.particoes.get(0).getTamanho());

						} else {
							deuErroReserva();
							this.addReservas(p);

						}

					} else if ((this.particoes.get(1).getTamanhoAtual()
							- p.getTempo() > this.particoes.get(0).getTamanhoAtual() - p.getTempo())
							&& (this.particoes.get(1).getTamanhoAtual()
									- p.getTempo() > this.particoes.get(2).getTamanhoAtual() - p.getTempo())
							&& (this.particoes.get(1).getTamanhoAtual()
									- p.getTempo() > this.particoes.get(3).getTamanhoAtual() - p.getTempo())) {
						if (this.particoes.get(1).validaEspaco(p) == null) {

							this.particoes.get(1).addProcesso(p);
							this.particoes.get(1)
									.setTamanhoAtual(this.particoes.get(1).getTamanhoAtual() - p.getTempo());

							System.out.println(i + "- Processo " + p.getNome() + " chegou,"
									+ " e foi alocado na particao de tempo " + this.particoes.get(1).getTamanho());

						} else {
							deuErroReserva();
							this.addReservas(p);

						}
					} else if ((this.particoes.get(2).getTamanhoAtual()
							- p.getTempo() > this.particoes.get(1).getTamanhoAtual() - p.getTempo())
							&& (this.particoes.get(2).getTamanhoAtual()
									- p.getTempo() > this.particoes.get(0).getTamanhoAtual() - p.getTempo())
							&& (this.particoes.get(2).getTamanhoAtual()
									- p.getTempo() > this.particoes.get(3).getTamanhoAtual() - p.getTempo())) {

						if (this.particoes.get(2).validaEspaco(p) == null) {

							this.particoes.get(2).addProcesso(p);
							this.particoes.get(2)
									.setTamanhoAtual(this.particoes.get(2).getTamanhoAtual() - p.getTempo());

							System.out.println(i + "- Processo " + p.getNome() + " chegou,"
									+ " e foi alocado na particao de tempo " + this.particoes.get(2).getTamanho());

						} else {
							deuErroReserva();
							this.addReservas(p);

						}

					} else if ((this.particoes.get(3).getTamanhoAtual()
							- p.getTempo() > this.particoes.get(1).getTamanhoAtual() - p.getTempo())
							&& (this.particoes.get(3).getTamanhoAtual()
									- p.getTempo() > this.particoes.get(2).getTamanhoAtual() - p.getTempo())
							&& (this.particoes.get(3).getTamanhoAtual()
									- p.getTempo() > this.particoes.get(0).getTamanhoAtual() - p.getTempo())) {

						if (this.particoes.get(3).validaEspaco(p) == null) {

							this.particoes.get(3).addProcesso(p);
							this.particoes.get(3)
									.setTamanhoAtual(this.particoes.get(3).getTamanhoAtual() - p.getTempo());

							System.out.println(i + "- Processo " + p.getNome() + " chegou,"
									+ " e foi alocado na particao de tempo " + this.particoes.get(3).getTamanho());

						} else {
							deuErroReserva();
							this.addReservas(p);
						}

					} else {
						deuErro();
					}

				}
			}
		}
	}

	private static void deuErro() {
		System.out.println("Ocorreu um erro na distribuicao de processos no Worst Fit!"); // msg ERRO
	}

	private static void deuErroReserva() {
		System.out.println("Alocacao de memoria cheia no Worst Fit, processo encaminhado para a fila de espera!"); // msg
																													// ERRO
	}

}
