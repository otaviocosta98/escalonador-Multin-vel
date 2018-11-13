package com.usjt.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.usjt.model.Processo;

import usjt.esc.memoria.Memoria;
import usjt.esc.memoria.Particao;

public class Main {
	
	private static Integer TAMANHO_FIXO_MEMORIA = 100;
	private static Integer TAMANHO_FIXO_P1 = 10;
	private static Integer TAMANHO_FIXO_P2 = 40;
	private static Integer TAMANHO_FIXO_P3 = 30;
	private static Integer TAMANHO_FIXO_P4 = 20;
	private static Integer uu = 0;

	public static void main(String[] args) {
		List<Memoria> memorias = new ArrayList<>();

		Particao part1 = new Particao(TAMANHO_FIXO_P1);
		Particao part2 = new Particao(TAMANHO_FIXO_P2);
		Particao part3 = new Particao(TAMANHO_FIXO_P3);
		Particao part4 = new Particao(TAMANHO_FIXO_P4);
		List<Particao> particoes = new ArrayList<>();
		particoes.add(part1);
		particoes.add(part2);
		particoes.add(part3);
		particoes.add(part4);

		Memoria memoria1 = new Memoria(TAMANHO_FIXO_MEMORIA, particoes);
		memorias.add(memoria1);

		JOptionPane.showMessageDialog(null,
				"Tamanho: " + TAMANHO_FIXO_MEMORIA + "\n" + "Qtde Particoes: 4 \n" + "Tamanho p1: " + TAMANHO_FIXO_P1
						+ "\n" + "Tamanho p2: " + TAMANHO_FIXO_P2 + "\n" + "Tamanho p3: " + TAMANHO_FIXO_P3 + "\n"
						+ "Tamanho p4: " + TAMANHO_FIXO_P4 + "\n" + "Particao variavel: Worst_Fit ",
				"Memoria", JOptionPane.INFORMATION_MESSAGE);

		alocaProcessos(memoria1);
		if(memoria1 != null) {
			System.out.println("PROCESSADOR EMULADO COM SUCESSO! ");
		}
	}
	
	public static void alocaProcessos(Memoria memoria) {
		Integer quantProcessos = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a quantidade de processos:"));
		List<Processo> processosFila = new ArrayList<>(); 
		for(int i = 0; i < quantProcessos; i++) {
			String nomeProcesso = JOptionPane.showInputDialog(null, "Digite o nome do processo:");
			Integer tempoProcesso = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a duração do processo " + nomeProcesso + " (em milissegundos):"));
			Integer tempoChegada = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o instante de chegada do processo " + nomeProcesso + " (em milissegundos):"));
			Processo processo = new Processo(nomeProcesso, tempoProcesso, tempoChegada);
			processosFila.add(processo);
		}
		Integer quantum = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o valor do quantum:"));
		
		System.out.println("------------------ Tabela de processos --------------------");
		System.out.println("");
		System.out.println("Processo		| Tempo			| Chegada");
		for (Processo Processo : processosFila) {
			System.out.println(Processo.getNome() + "			| " + Processo.getTempoRestante() + "			| " + Processo.getTempoChegada());
		}
		System.out.println("");
		System.out.println("Quantum usado = " + quantum);
		
		System.out.println("\n Iniciando Worst Fit ...");
		memoria.worstFit(processosFila);
		
		List<Processo> processos = new ArrayList<>();
		
		for (Particao p : memoria.getParticoes()) { // recupera os processos das particoes para serem processados
			if (!p.getProcessos().isEmpty()) {
				processos.addAll(p.getProcessos());
			}
		}
		
		processos.sort((p1, p2) -> p1.getTempoChegada() - p2.getTempoChegada());
		
		for(Processo processo : processos) {
			if(processo.getTempoRestante() > 0) {
				Integer conta = processo.getTempoRestante() - quantum;
				processo.setTempoParada(conta < 0 ? quantum : conta);
			}
		}
		
		processaNivel(processos, quantum, 1);
		uu++;
		processaNivel(voltaFila(processos), quantum, 2);
		uu++;
		processaNivel(voltaFila(processos), quantum, 3);
		uu++;
		System.out.println("Round Robin");
		System.out.println("-------------------------------------------------");
		processaRoundRobin(voltaFila(processos), quantum);
	}
	
	private static void processaNivel(List<Processo> processosFila, Integer quantum, Integer nivel) {
		System.out.println("Nível " + nivel);
		System.out.println("-------------------------------------------------");
		for (int t = 0; t < 100; t++, uu++) {
			System.out.println("uu " + uu);
			for (Processo processo : processosFila) {
				if(processo.getTempoRestante() > 0 && processo.getTempoChegada() <= uu && "Fora".equals(processo.getStatus())) {
					processo.setStatus("Pronto");
				}
			}
			System.out.println("-------------------------------------------------");
			for (Processo processo : processosFila) {
				if("Pronto".equals(processo.getStatus())) {
					processo.setTempoRestante(processo.getTempoRestante() - 1);
					System.out.println(processo.toString());
					if(processo.getTempoRestante().equals(0) || processo.getTempoRestante().equals(processo.getTempoParada())) {
						processo.setStatus("Processado");
						Integer conta = processo.getTempoRestante() - quantum;
						processo.setTempoParada(conta < 0 ? quantum : conta);
					}
					break;
				}
			}
			System.out.println("-------------------------------------------------");
			if(processosFila.size() == processosFila.stream().filter(p -> "Processado".equals(p.getStatus())).collect(Collectors.toList()).size()) {
				break;
			}
		}
		System.out.println("");
	}
	
	private static void processaRoundRobin(List<Processo> processosFila, Integer quantum) {
		for (; ; uu++) {
			System.out.println("uu " + uu);
			for (Processo processo : processosFila) {
				if(processo.getTempoRestante() > 0 && processo.getTempoChegada() <= uu && "Fora".equals(processo.getStatus())) {
					processo.setStatus("Pronto");
				}
			}
			System.out.println("-------------------------------------------------");
			for (Processo processo : processosFila) {
				if("Pronto".equals(processo.getStatus())) {
					processo.setTempoRestante(processo.getTempoRestante() - 1);
					System.out.println(processo.toString());
					if(processo.getTempoRestante().equals(0) || processo.getTempoRestante().equals(processo.getTempoParada())) {
						processo.setStatus("Processado");
						Integer conta = processo.getTempoRestante() - quantum;
						processo.setTempoParada(conta < 0 ? quantum : conta);
					}
					break;
				}
			}
			System.out.println("-------------------------------------------------");
			if(processosFila.size() == processosFila.stream().filter(p -> "Processado".equals(p.getStatus())).collect(Collectors.toList()).size()) {
				break;
			}
		}
		List<Processo> proxLista = processosFila.stream().filter(processo -> "Processado".equals(processo.getStatus()) && processo.getTempoRestante() > 0).collect(Collectors.toList());
		proxLista.forEach(processo -> processo.setStatus("Fora"));
		if(!proxLista.isEmpty()) {
			processaRoundRobin(proxLista, quantum);
			}
	}
	
	private static List<Processo> voltaFila(List<Processo> processosFila) {
		processosFila = processosFila.stream().filter(processo -> "Processado".equals(processo.getStatus()) && processo.getTempoRestante() > 0).collect(Collectors.toList());
		processosFila.forEach(processo -> processo.setStatus("Fora"));
		return processosFila;
	}
}
