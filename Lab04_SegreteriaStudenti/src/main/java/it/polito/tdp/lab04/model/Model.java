package it.polito.tdp.lab04.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.IscrizioneDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;
import javafx.collections.ObservableList;

public class Model {
	CorsoDAO corsoDao = new CorsoDAO();
	StudenteDAO studenteDao = new StudenteDAO();
	IscrizioneDAO iscrizioneDao = new IscrizioneDAO();
	List <Corso> corsi = new ArrayList<Corso>(corsoDao.getTuttiICorsi());
	List <Studente> studenti = new ArrayList<Studente>(this.studenteDao.getTuttiGliStudenti());
	Map <Integer, Studente> studentiPerMatricola; 
	
	public ArrayList<String> getNomiCorsi() {
		ArrayList <String> nomi = new ArrayList <String>();
		for(Corso c: corsi) {
			nomi.add(c.getNome());
			
		}
		return nomi;
	}
	
	public Studente getStudenteByMatricola(int matricola){
		return studenteDao.getStudenteByMatricola(matricola);
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso){
		//ArrayList<Studente> studenti = new ArrayList<Studente>(corsoDao.getStudentiIscrittiAlCorso(corso));
		boolean trovato = false;
		for(Corso c: corsi) {
			if(c.equals(corso)) {
				corso.setCodins(c.getCodins());
				trovato = true;
			}
		}
		if(trovato)
			return corsoDao.getStudentiIscrittiAlCorso(corso);
		else
			return null;
	}
	
	public List<Corso> getCorsiByMatricola(int matricola){
		boolean trovato = false;
		for(Studente s: studenti) {
			if(s.getMatricola() == matricola){
				trovato = true;
			}
		}
		if(trovato)//la matricola esiste, devo aggiungere le informazioni ai corsi
			return corsoDao.getCorsiByMatricola(matricola);
		else
			return null;
	}
	
	public boolean isIscrittoAlCorso(int matricola, String nomeCorso) {
		
		for(Corso c: corsi) {
			if(c.getNome().compareTo(nomeCorso)==0)
				return iscrizioneDao.isIscrittoAlCorso(matricola, c.getCodins());
		}
		return false;
	}
	
	public int doIscrivi(int matricola, String nomeCorso) {
		/*
		 * 	1 = studente già iscritto al corso
		 * 	2 = studente correttamente iscritto
		 * 	3 = studente non esistente
		 */
		boolean studenteEsistente = false;
		for(Studente s: studenti) {
			if(s.getMatricola() == matricola)
				studenteEsistente = true;
		}
		
		if(studenteEsistente) {
			if(isIscrittoAlCorso(matricola, nomeCorso))
				return 1;
			for(Corso c: corsi) {
				if(c.getNome().compareTo(nomeCorso)==0) {
					iscrizioneDao.doIscrivi(matricola, c.getCodins());
					return 2;
				}
			}
		}
		return 3;
	}
	
}
