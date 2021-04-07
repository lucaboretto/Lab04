package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	//completamento automatico
	public Studente getStudenteByMatricola(int matricola){
		String sql = "SELECT cognome, nome "
				+ "FROM studente "
				+ "WHERE matricola = ?";
		Studente s = null;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st  = conn.prepareStatement(sql);
			st.setInt(1,  matricola);
			ResultSet rs = st.executeQuery();
			rs.first();
				int matr = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String cds = rs.getString("CDS");
				
				s = new Studente(matr, cognome, nome, cds);
			
			conn.close();
			return s;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	public List<Studente> getTuttiGliStudenti(){
		String sql = "SELECT * "
				+ "FROM studente ";
		List<Studente> studenti = new ArrayList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st  = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				int matr = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String cds = rs.getString("CDS");
				
				Studente s = new Studente(matr, cognome, nome, cds);
				studenti.add(s);
			}
			conn.close();
			return studenti;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
}

