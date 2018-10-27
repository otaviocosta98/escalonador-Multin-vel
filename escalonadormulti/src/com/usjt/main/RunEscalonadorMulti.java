package com.usjt.main;

import javax.swing.JOptionPane;

import com.usjt.model.ProcessoDTO;

public class RunEscalonadorMulti {

	public static void main(String[] args) {

		Integer quantum = 2;
		Integer niveis = 3;
		Integer processosRestantes = 0;
		
		Integer quantProcessos = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a quantidade de processos:"));
		ProcessoDTO[] processosFila = new ProcessoDTO[quantProcessos]; 
		for(int i = 0; i < quantProcessos; i++) {
			String nomeProcesso = JOptionPane.showInputDialog(null, "Digite o nome do processo:");
			Integer tempoProcesso = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o tempo do processo:"));
			ProcessoDTO processo = new ProcessoDTO(nomeProcesso, tempoProcesso);
			processosFila[i] = processo;
		}
		
		processosRestantes = quantProcessos;
		
		for(int i = 1; i <= niveis; i++) {
			System.out.println("-------------------------------------------------");
			System.out.println("Nível " + i);
			for(int j = 0; j < quantProcessos; j++) {
				System.out.println("");
				if(!processosFila[j].getTempoRestante().equals(0)) {
					System.out.println("Processo " + processosFila[j].getNmProcesso());
					System.out.println("Tempo Atual: " + processosFila[j].getTempoRestante());
					Integer conta = processosFila[j].getTempoRestante() - quantum;
					processosFila[j].setTempoRestante(conta < 0 ? 0 : conta);
					if(processosFila[j].getTempoRestante().equals(0)) {
						processosRestantes--;
					}
					System.out.println("Tempo Restante: " + processosFila[j].getTempoRestante());
				}
			}
			System.out.println("-------------------------------------------------");
		}
		
		while(processosRestantes > 0)
		for(int j = 0; j < quantProcessos; j++) {
			if(processosFila[j].getTempoRestante() > 0) {
				System.out.println("Processo " + processosFila[j].getNmProcesso());
				System.out.println("Tempo Atual: " + processosFila[j].getTempoRestante());
				Integer conta = processosFila[j].getTempoRestante() - quantum;
				processosFila[j].setTempoRestante(conta < 0 ? 0 : conta);
				System.out.println("Tempo Restante: " + processosFila[j].getTempoRestante());
			} else {
				processosRestantes--;
			}
		}

	}
}
