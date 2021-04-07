package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class IscrizioneDAO {
	
	public boolean isIscrittoAlCorso(int matricola, String codins){
		String sql = "SELECT * "
				+ "FROM iscrizione "
				+ "WHERE matricola = ? "
				+ "AND codins = ?";
		
		boolean iscritto = false;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st;
			st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			st.setString(2, codins);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				if(rs.getString("codins")!= null && ((Integer)rs.getInt("matricola"))!=null)
					iscritto = true; 
			}
			conn.close();
			return iscritto;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void doIscrivi(int matricola, String codins) {
		String sql = "INSERT INTO iscrizione(matricola, codins) "
				+ "VALUES(?, ?)";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st;
			st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			st.setString(2, codins);
			ResultSet rs = st.executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
}
