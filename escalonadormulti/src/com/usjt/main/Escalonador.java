package com.usjt.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.usjt.model.Processo;

public class Escalonador {
	
	static Integer uu = 0;
	
	public static void main(String[] args) {
		
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
		
		processosFila.sort((p1, p2) -> p1.getTempoChegada() - p2.getTempoChegada());
		
		for(Processo processo : processosFila) {
			if(processo.getTempoRestante() > 0) {
				Integer conta = processo.getTempoRestante() - quantum;
				processo.setTempoParada(conta < 0 ? quantum : conta);
			}
		}
		
		processaNivel(processosFila, quantum, 1);
		processosFila = processosFila.stream().filter(processo -> "Processado".equals(processo.getStatus()) && processo.getTempoRestante() > 0).collect(Collectors.toList());
		processosFila.forEach(processo -> processo.setStatus("Fora"));
		
		System.out.println("");
		processaNivel(processosFila, quantum, 2);
		processosFila = processosFila.stream().filter(processo -> "Processado".equals(processo.getStatus()) && processo.getTempoRestante() > 0).collect(Collectors.toList());
		processosFila.forEach(processo -> processo.setStatus("Fora"));
		
		uu++;
		System.out.println("");
		processaNivel(processosFila, quantum, 3);
		processosFila = processosFila.stream().filter(processo -> "Processado".equals(processo.getStatus()) && processo.getTempoRestante() > 0).collect(Collectors.toList());
		processosFila.forEach(processo -> processo.setStatus("Fora"));

		uu++;
		System.out.println("");
		System.out.println("Round Robin");
		System.out.println("-------------------------------------------------");
		processaRoundRobin(processosFila, quantum);
		
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
}
