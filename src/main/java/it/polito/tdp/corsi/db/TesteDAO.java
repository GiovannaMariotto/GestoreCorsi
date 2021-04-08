package it.polito.tdp.corsi.db;

import it.polito.tdp.corsi.model.Corso;

public class TesteDAO {

	public static void main(String[] args) {
		
		CorsoDAO dao = new CorsoDAO();
		System.out.println(dao.getStudentiByCorso(new Corso("01KSUPG", null, null, null)));
		
		//System.out.println(dao.getCorsiByPeriodo(1));
	}
	
	
}