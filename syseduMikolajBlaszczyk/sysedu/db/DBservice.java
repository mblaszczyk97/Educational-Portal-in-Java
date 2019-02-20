package sysedu.db;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sysedu.domain.Admin;
import sysedu.domain.Materialy;
import sysedu.domain.Nauczyciel;
import sysedu.domain.Ocena;
import sysedu.domain.Oddzial;
import sysedu.domain.Przedmiot;
import sysedu.domain.PrzedmiotOddzial;
import sysedu.domain.Sprawdziany;
import sysedu.domain.Uczen;

public class DBservice {

	static Connection connection = null;
	public static PreparedStatement st = null;
	public static ResultSet rs = null;
	
	public static java.sql.Connection getConnection() {
		if(connection==null) {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connection = java.sql.DriverManager.getConnection("jdbc:sqlserver://mblaszczyk.database.windows.net:1433;database=portalEdukacyjny;user=mblaszczyk@mblaszczyk;password=Gornicza20;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return connection;
	}
	
	public static Uczen LoginUczen(String uname, String pass) {
		Uczen uczen = null;
		String sql="SELECT uczniowie.imie, uczniowie.uczenid FROM Uczniowie where Login=? and Haslo=?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			
			DBservice.st.setString(1, uname);
			DBservice.st.setString(2, pass);
			uczen = new Uczen(uname);
			DBservice.rs=st.executeQuery();
			
			if (rs.next()) {
				int id = rs.getInt(2);
				String imie = rs.getString(1);
				uczen.setId(id);
				uczen.setImie(imie);
			}else return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
		
		return uczen;
	}
	
	public static Nauczyciel LoginNauczyciel(String uname, String pass) {
		Nauczyciel nauczyciel = null;
		String sql="SELECT NauczycielID, Imie, Nazwisko FROM Nauczyciele where Login=? and Haslo=?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			
			DBservice.st.setString(1, uname);
			DBservice.st.setString(2, pass);
			nauczyciel = new Nauczyciel(uname);
			DBservice.rs=st.executeQuery();
			
			if (rs.next()) {
				int id = rs.getInt(1);
				String imie = rs.getString(2);
				String nazwisko = rs.getString(3);
				nauczyciel.setId(id);
				nauczyciel.setImie(imie);
				nauczyciel.setNazwisko(nazwisko);
			}else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
		
		return nauczyciel;
	}
	
	public static Admin LoginAdmin(String uname, String pass) {
		Admin admin = null;
		String sql="SELECT AdminID, Imie, Nazwisko, Telefon FROM Admin where Login=? and Haslo=?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			
			DBservice.st.setString(1, uname);
			DBservice.st.setString(2, pass);
			admin = new Admin(uname);
			DBservice.rs=st.executeQuery();
			
			if (rs.next()) {
				int id = rs.getInt(1);
				String imie = rs.getString(2);
				String nazwisko = rs.getString(3);
				String telefon = rs.getString(4);
				admin.setId(id);
				admin.setImie(imie);
				admin.setNazwisko(nazwisko);
				admin.setNrTelefonu(telefon);
			}else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
		
		return admin;
	}

	/**
	 * Szukamy jakich przedmiotów i klas uczy nauczyciel.
	 * @nauczyciel - obiekt nauczyciela dla którego sprawdzamy zapytanie w bazie danych
	 */
	public static void getPrzedmiotOddzial(Nauczyciel nauczyciel) {
		String sql=
				"SELECT Przedmioty.PrzedmiotID, Przedmioty.Nazwa, Oddzialy.OddzialID, Oddzialy.Nazwa FROM Nauczyciele " + 
				"JOIN Oddzial_Przedmiot ON Nauczyciele.NauczycielID = Oddzial_Przedmiot.NauczycielID " + 
				"JOIN Przedmioty ON Przedmioty.PrzedmiotID = Oddzial_Przedmiot.PrzedmiotID " + 
				"JOIN Oddzialy ON Oddzialy.OddzialID = Oddzial_Przedmiot.OddzialID " + 
				"where Nauczyciele.NauczycielId = ? ORDER BY Oddzialy.Nazwa ASC";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			
			DBservice.st.setInt(1, nauczyciel.getId());
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				PrzedmiotOddzial przedmiotOddzial = new PrzedmiotOddzial();
				przedmiotOddzial.setPrzedmiotID(rs.getInt(1));
				przedmiotOddzial.setNazwaPrzedmiotu(rs.getString(2));
				przedmiotOddzial.setOddzialID(rs.getInt(3));
				przedmiotOddzial.setNazwaOddzialu(rs.getString(4));
				nauczyciel.addPrzedmiot(przedmiotOddzial);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * £apiemy klasê do której uczêszcza uczeñ
	 * @uczen - Uczeñ dla którego szukamy klasy w
	 * bzie danych
	 */
	public static Oddzial getOddzial(Uczen uczen) {
		Oddzial oddzial = null;
		String sql="SELECT oddzialy.oddzialid, oddzialy.nazwa FROM Uczniowie JOIN oddzialy ON Uczniowie.OddzialID = Oddzialy.OddzialID where Uczniowie.uczenid = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			
			DBservice.st.setInt(1, uczen.getId());
			DBservice.rs=st.executeQuery();
			if (rs.next()) {
				String nazwa = rs.getString(2);
				int id = rs.getInt(1);
				oddzial = new Oddzial(nazwa);
				oddzial.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
		
		
		return oddzial;
	}
	
	/**
	 * Wy³¹pujemy z bazy danych przedmnioty przypisane do danej klasy
	 * @oddzial - klasa w szkole
	 */
	public static void setPrzedmioty(Oddzial oddzial) {
		String sql="SELECT Oddzialy.OddzialID, Oddzialy.Nazwa, Rok, Przedmioty.Nazwa, Przedmioty.PrzedmiotID FROM Oddzialy JOIN Oddzial_Przedmiot ON Oddzial_Przedmiot.OddzialID = Oddzialy.OddzialID JOIN Przedmioty ON Przedmioty.PrzedmiotID = Oddzial_Przedmiot.PrzedmiotID WHERE oddzialy.nazwa = ? ORDER BY Przedmioty.Nazwa ASC";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setString(1, oddzial.getNazwa());
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(5);
				String nazwa = rs.getString(4);
				Przedmiot przedmiot = new Przedmiot(nazwa);
				przedmiot.setId(id);
				oddzial.addPrzedmiot(przedmiot);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
			return;
		}		
		
		
		return;
	}
	
	public static List<Uczen> getUczniowie() {
		String sql="SELECT UczenID, Imie, Nazwisko, OddzialID FROM Uczniowie ORDER BY Uczniowie.Nazwisko ASC";
		List<Uczen> listaUczniow = new ArrayList<Uczen>();
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);;
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String imie = rs.getString(2);
				String nazwisko = rs.getString(3);
				int oddzialID = rs.getInt(4);
				Uczen uczen = new Uczen(id, imie, nazwisko, oddzialID);
				listaUczniow.add(uczen);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}		
		return listaUczniow;
	}

	/**
	 * Pobieramy listê uczniów dla danej klasy o id
	 * @idOddzial - id klasy dla której pobieramy listê uczniów
	 */
	public static List<Uczen> getListaUczniow(int idOddzial) {
		String sql="SELECT * FROM Uczniowie " + 
				"JOIN Oddzialy ON Oddzialy.OddzialID = Uczniowie.OddzialID WHERE Oddzialy.OddzialID = ? ORDER BY Uczniowie.Nazwisko ASC";
		List<Uczen> listaUczniow = new ArrayList<Uczen>();
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			
			DBservice.st.setInt(1, idOddzial);
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String imie = rs.getString(2);
				String nazwisko = rs.getString(3);
				Uczen uczen = new Uczen(id, imie, nazwisko);
				listaUczniow.add(uczen);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}		
		
		
		return listaUczniow;
	}

	public static List<Oddzial> getListaOddzialow() {
		String sql="SELECT ODDZIALID, NAZWA, ROK FROM ODDZIALY ORDER BY ODDZIALY.NAZWA ASC";
		List<Oddzial> listaOddzialow = new ArrayList<Oddzial>();
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String nazwa = rs.getString(2);
				String rok = rs.getString(3);
				Oddzial oddzial = new Oddzial(nazwa, id, rok);
				listaOddzialow.add(oddzial);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}		
		
		
		return listaOddzialow;
	}
	
	public static List<Nauczyciel> getListaNauczycieli() {
		String sql="SELECT NauczycielID, Imie, Nazwisko FROM NAUCZYCIELE ORDER BY NAUCZYCIELE.NAZWISKO ASC";
		List<Nauczyciel> listaNauczycieli = new ArrayList<Nauczyciel>();
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String imie = rs.getString(2);
				String nazwisko = rs.getString(3);
				Nauczyciel nauczyciel = new Nauczyciel(id, imie, nazwisko);
				listaNauczycieli.add(nauczyciel);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}		
		
		
		return listaNauczycieli;
	}

	/**
	 * Pobieramy listê nauczycieli ucz¹cych danego przedmiotu w danej klasie
	 * @oddzialid - id klasy
	 * @przedmiotid- id przedmiotu
	 */
	public static List<Nauczyciel> getListaNauczycieliKlasyPrzedmiotu(int oddzialid, int przedmiotid) {
		String sql="SELECT * FROM Nauczyciele " + 
				"JOIN ODDZIAL_PRZEDMIOT on Nauczyciele.NauczycielID = Oddzial_Przedmiot.NauczycielID " + 
				"WHERE OddzialID=? AND PrzedmiotID=? ORDER BY NAUCZYCIELE.NAZWISKO ASC";
		List<Nauczyciel> listaNauczycieli = new ArrayList<Nauczyciel>();
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setInt(1, oddzialid);
			DBservice.st.setInt(2, przedmiotid);
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String imie = rs.getString(2);
				String nazwisko = rs.getString(3);
				Nauczyciel nauczyciel = new Nauczyciel(id, imie, nazwisko);
				listaNauczycieli.add(nauczyciel);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}		
		
		
		return listaNauczycieli;
	}

	public static List<Przedmiot> getListaPrzedmiotow() {
		String sql="SELECT PrzedmiotID, Nazwa FROM PRZEDMIOTY ORDER BY Przedmioty.NAZWA ASC";
		List<Przedmiot> listaPrzedmiotow = new ArrayList<Przedmiot>();
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String nazwa = rs.getString(2);
				Przedmiot oddzial = new Przedmiot(nazwa, id);
				listaPrzedmiotow.add(oddzial);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}		
		
		
		return listaPrzedmiotow;
	}

	public static List<Przedmiot> getPrzedmiotyOddzialu(Oddzial oddzial) {
		String sql="SELECT DISTINCT przedmioty.nazwa, przedmioty.przedmiotid FROM ODDZIALY " + 
				"JOIN Oddzial_Przedmiot on Oddzialy.OddzialID=Oddzial_Przedmiot.OddzialId " + 
				"JOIN Przedmioty on Przedmioty.PrzedmiotID=Oddzial_Przedmiot.PrzedmiotID " + 
				"WHERE Oddzialy.nazwa= ? ";
		List<Przedmiot> listaPrzedmiotow = new ArrayList<Przedmiot>();
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setString(1, oddzial.getNazwa());
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(2);
				String nazwa = rs.getString(1);
				Przedmiot przedmiot = new Przedmiot(nazwa, id);
				listaPrzedmiotow.add(przedmiot);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}		
		
		
		return listaPrzedmiotow;
	}
	
	public static List<Nauczyciel> getNauczycielePrzedmiotu(Przedmiot przedmiot) {
		String sql="SELECT nauczyciele.imie, nauczyciele.nazwisko, nauczyciele.nauczycielid FROM nauczyciele " + 
				"JOIN Oddzial_Przedmiot on nauczyciele.nauczycielid=Oddzial_Przedmiot.nauczycielid " + 
				"JOIN Przedmioty on Przedmioty.PrzedmiotID=Oddzial_Przedmiot.PrzedmiotID " + 
				"WHERE przedmioty.nazwa= ? ";
		List<Nauczyciel> listaNauczycieli = new ArrayList<Nauczyciel>();
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setString(1, przedmiot.getNazwa());
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(3);
				String imie = rs.getString(1);
				String nazwisko = rs.getString(2);
				Nauczyciel nauczyciel = new Nauczyciel(id,imie,nazwisko);
				listaNauczycieli.add(nauczyciel);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}		
		
		
		return listaNauczycieli;
	}
	
	
	
	
	/**
	 * Bierzemy listê ocen dla ucznia z danego przedmiotu
	 * @przedmiot - obiekt przedmiotu z którego chcemy oceny
	 * @uczen - obiekt ucznia dla którego chcemy oceny
	 */
	public static List<Ocena> getOceny(Przedmiot przedmiot, Uczen uczen) {
		List<Ocena> lista = new ArrayList<Ocena>();
		//String sql="SELECT ocena, typ, komentarz FROM Uczniowie JOIN Oddzialy ON Oddzialy.OddzialID = Uczniowie.OddzialID JOIN Oddzial_Przedmiot ON Uczniowie.OddzialID = Oddzial_Przedmiot.OddzialID JOIN Przedmioty ON Przedmioty.PrzedmiotID = Oddzial_Przedmiot.PrzedmiotID JOIN Oceny ON Oceny.PrzedmiotID = Przedmioty.PrzedmiotID  WHERE Uczniowie.UczenID = ? AND Przedmioty.PrzedmiotID = ?";
		String sql="SELECT ocena, typ, komentarz, ocenaid FROM Oceny WHERE UczenID = ? AND PrzedmiotID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			
			DBservice.st.setInt(1, uczen.getId());
			DBservice.st.setInt(2, przedmiot.getId());
			PreparedStatement s = DBservice.st;
			
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				String ocenast = rs.getString(1);
				String typ = rs.getString(2);
				String komentarz = rs.getString(3);
				int id = rs.getInt(4);
				Ocena ocena = new Ocena(Float.valueOf(ocenast), typ, komentarz);
				ocena.setId(id);
				lista.add(ocena);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
			return lista;
		}		
		
		
		return lista;
	}
	
	/**
	 * Pobieramy z bazy danych listê materia³ów z danego przedmiotu
	 * @przedmiot - obiekt przedmiotu dla którego pobieramy listê
	 * materia³ów
	 */
	public static List<Materialy> getMaterialy(Przedmiot przedmiot) {
		List<Materialy> lista = new ArrayList<Materialy>();
		String sql="SELECT url, materialy.nazwa, materialy.przedmiotid FROM przedmioty JOIN materialy ON przedmioty.przedmiotid = materialy.przedmiotid WHERE przedmioty.nazwa = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setString(1, przedmiot.getNazwa());
			PreparedStatement s = DBservice.st;
			
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				String url = rs.getString(1);
				String nazwa = rs.getString(2);
				Materialy material1 = new Materialy(url, nazwa);
				lista.add(material1);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}		
		
		
		return lista;
	}
	/**
	 * Pobieramy z bazy danych listê materia³ów z danego przedmiotu
	 * @przedmiot - nazwa przedmiotu dla którego pobieramy listê
	 * materia³ów
	 */
	public static List<Materialy> getMaterialy(String przedmiot) {
		List<Materialy> lista = new ArrayList<Materialy>();
		String sql="SELECT url, materialy.nazwa, materialy.przedmiotid FROM przedmioty JOIN materialy ON przedmioty.przedmiotid = materialy.przedmiotid WHERE przedmioty.nazwa = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setString(1, przedmiot);
			PreparedStatement s = DBservice.st;
			
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				String url = rs.getString(1);
				String nazwa = rs.getString(2);
				Materialy material1 = new Materialy(url, nazwa);
				lista.add(material1);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}		
		
		
		return lista;
	}

	public static List<Sprawdziany> getSprawdziany(Przedmiot przedmiot) {
		List<Sprawdziany> lista = new ArrayList<Sprawdziany>();
		String sql="SELECT url, Sprawdziany.nazwa, Sprawdziany.przedmiotid FROM przedmioty JOIN Sprawdziany ON przedmioty.przedmiotid = Sprawdziany.przedmiotid WHERE przedmioty.nazwa = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setString(1, przedmiot.getNazwa());
			PreparedStatement s = DBservice.st;
			
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				String url = rs.getString(1);
				String nazwa = rs.getString(2);
				Sprawdziany material1 = new Sprawdziany(url, nazwa);
				lista.add(material1);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}		
		
		
		return lista;
	}

	public static List<Sprawdziany> getSprawdziany(String nazwaPrzedmiotu) {
		List<Sprawdziany> lista = new ArrayList<Sprawdziany>();
		String sql="SELECT url, Sprawdziany.nazwa, Sprawdziany.przedmiotid FROM przedmioty JOIN Sprawdziany ON przedmioty.przedmiotid = Sprawdziany.przedmiotid WHERE przedmioty.nazwa = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setString(1, nazwaPrzedmiotu);
			PreparedStatement s = DBservice.st;
			
			DBservice.rs=st.executeQuery();
			while (rs.next()) {
				String url = rs.getString(1);
				String nazwa = rs.getString(2);
				Sprawdziany material1 = new Sprawdziany(url, nazwa);
				lista.add(material1);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}		
		
		
		return lista;
	}

	/**
	 * Pobieramy z bazy danych listê materia³ów z danego przedmiotu
	 * @ocena - obiekt oceny który dodajemy do bazy danych
	 */
	public static boolean dodajOceneUcznia(Ocena ocena) {
		String sql="INSERT INTO Oceny VALUES (?, ?, ?, ?, ?)";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setInt(1, (int) ocena.getOcena());
			DBservice.st.setString(2,  ocena.getTyp());
			DBservice.st.setString(3,  ocena.getKomentarz());
			DBservice.st.setInt(4, ocena.getIdPrzedmiot());
			DBservice.st.setInt(5, ocena.getIdUczen());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
		
	}
	
	public static boolean dodajNauczyciela(Nauczyciel nauczyciel) {
		String sql="INSERT INTO Nauczyciele VALUES (?, ?, ?, ?)";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setString(1,  nauczyciel.getImie());
			DBservice.st.setString(2,  nauczyciel.getNazwisko());
			DBservice.st.setString(3, "123");//has³o tymczasowe dla u³atwienia poruszania siê dla uzytku: generatorLosowychHasel()
			DBservice.st.setString(4, nauczyciel.getImie().charAt(0)+nauczyciel.getNazwisko());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
		
	}
	
	public static boolean dodajUcznia(Uczen created) {
		String sql="INSERT INTO Uczniowie VALUES (?, ?, ?, ?,?)";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setString(1,  created.getImie());
			DBservice.st.setString(2,  created.getNazwisko());
			DBservice.st.setString(3, "123");//has³o tymczasowe dla u³atwienia poruszania siê dla uzytku: generatorLosowychHasel()
			DBservice.st.setString(5, created.getImie().charAt(0)+created.getNazwisko());
			DBservice.st.setString(4,  null);
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public String generatorLosowychHasel() {
	    byte[] tablica = new byte[12];
	    new Random().nextBytes(tablica);
	    String haslo = new String(tablica, Charset.forName("UTF-8"));
	    return haslo;
	}
	
	public static boolean dodajUczniaKlasy(Uczen uczen, int oddzialid) {
		String sql="Update Uczniowie SET OddzialID = ? WHERE uczenID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setInt(1, oddzialid);
			DBservice.st.setInt(2, uczen.getId());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
		
	}

	public static boolean dodajPrzedmiotKlasy(Przedmiot selectedPrzedmiot, int id) {
		String sql="INSERT INTO Oddzial_Przedmiot VALUES (?,?,null)";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setInt(1, selectedPrzedmiot.getId());
			DBservice.st.setInt(2, id);
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean dodajNaczycielaPrzedmiotuKlasy(Nauczyciel selectedNauczyciel, int przedmiotid, int oddzialid) {
		String sql="UPDATE Oddzial_Przedmiot SET NauczycielID=? WHERE PrzedmiotID=? AND OddzialID=?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setInt(2, przedmiotid);
			DBservice.st.setInt(3, oddzialid);
			DBservice.st.setInt(1, selectedNauczyciel.getId());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean dodajKlase(Oddzial created) {
		String sql="INSERT INTO Oddzialy VALUES (?, ?)";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setString(1,  created.getNazwa());
			DBservice.st.setString(2,  created.getRok());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean dodajPrzedmiot(Przedmiot created) {
		String sql="INSERT INTO Przedmioty VALUES (?)";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setString(1,  created.getNazwa());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean dodajMaterial(Materialy material) {
		String sql="INSERT INTO Materialy VALUES (?, ?, ?)";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
	
			DBservice.st.setString(1,  material.getNazwa());
			DBservice.st.setString(2,  material.getUrl());
			DBservice.st.setInt(3, material.getPrzedmiotID());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
		
	}

	public static boolean dodajSprawdzian(Sprawdziany sprawdzian) {
		String sql="INSERT INTO Sprawdziany VALUES (?, ?, ?)";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
	
			DBservice.st.setString(1,  sprawdzian.getNazwa());
			DBservice.st.setString(2,  sprawdzian.getUrl());
			DBservice.st.setInt(3, sprawdzian.getPrzedmiotID());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	/**
	 * Updejt oceny ucznia na inn¹
	 * @ocena - obiekt oceny który dodajemy do bazy danych
	 */
	public static boolean zamienOceneUcznia(Ocena ocena) {
		String sql="UPDATE Oceny SET Ocena = ?, Typ = ?, Komentarz = ? WHERE OcenaID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setInt(1, (int) ocena.getOcena());
			DBservice.st.setString(2,  ocena.getTyp());
			DBservice.st.setString(3,  ocena.getKomentarz());
			DBservice.st.setInt(4, ocena.getId());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
		
	}
	
	public static boolean zmienKlasa(Oddzial selectedOddzial) {
		String sql="UPDATE Oddzialy SET Nazwa = ?, Rok = ? WHERE OddzialID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setString(1, selectedOddzial.getNazwa());
			DBservice.st.setString(2,  selectedOddzial.getRok());
			DBservice.st.setInt(3,  selectedOddzial.getId());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean zmienPrzedmiot(Przedmiot selectedPrzedmiot) {
		String sql="UPDATE Przedmioty SET Nazwa = ? WHERE PrzedmiotID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setString(1, selectedPrzedmiot.getNazwa());
			DBservice.st.setInt(2,  selectedPrzedmiot.getId());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean zmienNauczyciela(Nauczyciel nauczyciel) {
		String sql="UPDATE Nauczyciele SET Imie = ?, Nazwisko = ?, Haslo = ?, Login = ? WHERE NauczycielID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setString(1,  nauczyciel.getImie());
			DBservice.st.setString(2,  nauczyciel.getNazwisko());
			DBservice.st.setString(3, nauczyciel.getHaslo());
			DBservice.st.setString(4, nauczyciel.getLogin());
			DBservice.st.setInt(5, nauczyciel.getId());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
		
	}

	public static boolean zmienUcznia(Uczen selectedUczen) {
		String sql="UPDATE Uczniowie SET Imie = ?, Nazwisko = ?, Haslo = ?, Login = ? WHERE UczenID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);

			DBservice.st.setString(1,  selectedUczen.getImie());
			DBservice.st.setString(2,  selectedUczen.getNazwisko());
			DBservice.st.setString(3, selectedUczen.getHaslo());
			DBservice.st.setString(4, selectedUczen.getLogin());
			DBservice.st.setInt(5, selectedUczen.getId());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	/**
	 * Usuwamy ocenê z bazy danych
	 * @ocena - obiekt oceny który usuwamy z bazy danych
	 */
	public static boolean usunOceneUcznia(Ocena ocena) {
		String sql="DELETE FROM Oceny WHERE OcenaID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setInt(1, ocena.getId());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean usunMaterial(Materialy material) {
		String sql="DELETE FROM Materialy WHERE Nazwa = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setString(1, material.getNazwa());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean usunSprawdzian(Sprawdziany sprawdzian) {
		String sql="DELETE FROM Sprawdziany WHERE Nazwa = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setString(1, sprawdzian.getNazwa());
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean usunNauczyciela(int id) {
		String sql="DELETE FROM NAuczyciele WHERE NauczycielID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setInt(1, id);
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean usunUcznia(int id) {
		String sql="DELETE FROM Uczniowie WHERE UczenID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setInt(1, id);
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean usunPrzedmiot(int id) {
		String sql="DELETE FROM Przedmioty WHERE PrzedmiotID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setInt(1, id);
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean usunUczenKlasa(int iduczen) {
		String sql="Update Uczniowie Set OddzialID = null WHERE UczenID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setInt(1, iduczen);
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}
	
	public static boolean usunPrzedmiotKlasa(int idprzedmiot, int idoddzial) {
		String sql="DELETE FROM Oddzial_Przedmiot WHERE PrzedmiotID = ? AND OddzialID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setInt(1, idprzedmiot);
			DBservice.st.setInt(2, idoddzial);
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean usunNauczycielPrzedmiotuKlasy(int idprzedmiot, int idoddzial, int idnauczyciel) {
		String sql="DELETE FROM Oddzial_Przedmiot WHERE PrzedmiotID = ? AND OddzialID = ? AND NauczycielID = ?";
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareStatement(sql);
			DBservice.st.setInt(1, idprzedmiot);
			DBservice.st.setInt(2, idoddzial);
			DBservice.st.setInt(3, idnauczyciel);
			PreparedStatement s = DBservice.st;
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}		
	}

	public static boolean usunKlase(int id) {
		Connection con = getConnection();
		try {
			DBservice.st=con.prepareCall(
			        "{call removeOddzial(?)}",
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_READ_ONLY);
			DBservice.st.setInt(1, id);
			PreparedStatement s = DBservice.st;
			st.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	
}
