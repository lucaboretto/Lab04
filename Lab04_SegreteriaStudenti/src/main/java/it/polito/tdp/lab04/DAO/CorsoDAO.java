package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new ArrayList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				Corso corso = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				corsi.add(corso);
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		if(corso.getNome()!=null) {
			String sql = "SELECT i.matricola, cognome, nome, cds "
					+ "FROM studente s, iscrizione i "
					+ "WHERE s.matricola = i.matricola "
					+ "AND i.codins = ?";
			ArrayList <Studente> studenti = new ArrayList <Studente>();
			
			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, corso.getCodins());
				ResultSet rs = st.executeQuery();
				
				while(rs.next()) {
					int matricola = rs.getInt("matricola");
					String cognome = rs.getString("cognome");
					String nome = rs.getString("nome");
					String cds = rs.getString("CDS");
					Studente s = new Studente(matricola, cognome, nome, cds);
					studenti.add(s);
				}
				
				conn.close();
				return studenti;
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
		else
			return null;
	}
	
	public List<Corso> getCorsiByMatricola(int matricola){
		
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd "
				+ "	FROM iscrizione i, corso c "
				+ "	WHERE i.codins = c.codins "
				+ "	AND i.matricola = ?";
		
		ArrayList<Corso> corsiByMatricola = new ArrayList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st;
			st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String codins = rs.getString("codins");
				int crediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int pd = rs.getInt("pd");
				Corso c = new Corso(codins, crediti, nome, pd);
				corsiByMatricola.add(c);
			}
			conn.close();
			return corsiByMatricola;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
		return false;
	}

}
