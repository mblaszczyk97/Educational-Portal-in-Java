package sysedu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sysedu.domain.Ocena;
import sysedu.domain.Oddzial;
import sysedu.domain.Przedmiot;
import sysedu.domain.Uczen;

public class DBservice {
	static Connection connection = null;
	
	
	public static PreparedStatement st = null;
	public static ResultSet rs = null;
	
	public static Connection getConnection() {
		if(connection==null) {
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school?characterEncoding=utf-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return connection;
	}
	
	public static Uczen Login(String uname, String pass) {
		Uczen uczen = null;
		
		String sql="select * from uczniowie where imie=? and haslo=?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			
			DBservice.st.setString(1, uname);
			DBservice.st.setString(2, pass);
			uczen = new Uczen(uname);
			DBservice.rs=st.executeQuery();
			if (!rs.next()) {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
		
		return uczen;
	}
	
	public static Oddzial getOddzial(Uczen uczen) {
		Oddzial oddzial = null;
		String sql="SELECT * FROM uczniowie JOIN oddzialy ON uczniowie.oddzial_id = oddzialy.id where uczniowie.imie = ? ";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			
			DBservice.st.setString(1, uczen.imie);
			DBservice.rs=st.executeQuery();
			if (rs.next()) {
				String klasa = rs.getString(6);
				oddzial = new Oddzial(klasa);
				System.out.println("get oddzial: " + oddzial);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
		
		
		return oddzial;
	}
	
	public static void setPrzedmioty(Oddzial oddzial) {
		String sql="SELECT * FROM oddzialy JOIN przedmioty ON przedmioty.oddzial_id = oddzialy.id WHERE oddzialy.nazwa = ? ";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			
			DBservice.st.setString(1, oddzial.getNazwa());
			System.out.println("pobiera: " + oddzial.getNazwa());
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				String nazwa = rs.getString(5);
				Przedmiot przedmiot = new Przedmiot(nazwa);
				oddzial.addPrzedmiot(przedmiot);
				System.out.println("get przedmiot: " + nazwa);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}		
		
		
		return;
	}
	
	public static List<Ocena> getOceny(Przedmiot przedmiot, Uczen uczen) {
		List<Ocena> lista = new ArrayList<Ocena>();
		String sql="SELECT ocena, typ, komentarz FROM uczniowie JOIN oddzialy ON oddzialy.id = uczniowie.oddzial_id JOIN przedmioty ON przedmioty.oddzial_id = oddzialy.id JOIN oceny ON oceny.przedmiot_id = przedmioty.id  WHERE uczniowie.imie = ? AND przedmioty.nazwa = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			
			DBservice.st.setString(1, uczen.getImie());
			DBservice.st.setString(2, przedmiot.getNazwa());
			PreparedStatement s = DBservice.st;
			
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				String ocenast = rs.getString(1);
				String typ = rs.getString(2);
				String komentarz = rs.getString(3);
				Ocena ocena = new Ocena(Float.valueOf(ocenast), typ, komentarz);
				lista.add(ocena);
				System.out.println("ocena: " + ocena);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
		
		
		return lista;
	}
}
