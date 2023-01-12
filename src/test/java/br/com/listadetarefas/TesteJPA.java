package br.com.listadetarefas;

import javax.persistence.Persistence;


public class TesteJPA {

	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("lista-de-tarefas");
//		String version = FacesContext.class.getPackage().getImplementationVersion();
//		System.out.println("version: " + version);
	}
	
}
