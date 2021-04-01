package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {

	//Ogni tabella: una classe java (buona norma)
	
	//Pattern ORM (ogni tabella: creamo una classe Java Bean)
	//Nel nostro programma: studenti e corso
	
	public  List<Corso> getCorsiByPeriodo(Integer periodo){
		String sql ="SELECT * FROM corso WHERE pd=?";
		List<Corso> result = new ArrayList<Corso>();
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement pt =  conn.prepareStatement(sql);
			pt.setInt(1,periodo);
			ResultSet rs = pt.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				result.add(c);	
			}
			rs.close();
			pt.close();
			conn.close(); //chiudere la connessione è piu importante!
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}

	public Map<Corso,Integer> getIscrittiByPeriodo(Integer periodo){
		String sql="SELECT c.codins, c.nome, c.pd, COUNT(*) AS tot "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins=i.codins AND c.pd=? "
				+ "GROUP BY c.codins, c.nome, c.pd";
		Map<Corso, Integer> result = new HashMap<>();
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,periodo);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				Integer n = rs.getInt("tot");
				result.put(c, n);
			}
			rs.close();
			st.close();
			conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		return result;
		
		
	}
	
}
