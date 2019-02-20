package sysedu.domain;

import java.util.ArrayList;
import java.util.List;

public class Nauczyciel {
	
	private int id;
	private String imie;
	private String nazwisko;
	private List<PrzedmiotOddzial> przedmioty = new ArrayList<PrzedmiotOddzial>();
	private String haslo;
	private String login;
	
	public Nauczyciel(int id, String imie, String nazwisko) {
		super();
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
	}
	public Nauczyciel(String imie, String nazwisko) {
		super();
		this.imie = imie;
		this.nazwisko = nazwisko;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public Nauczyciel(String uname) {
		super();
		imie = new String("Goœæ");
	}
	public String getImie() {
		return imie;
	}
	public void setImie(String imie) {
		this.imie = imie;
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	public String getHaslo() {
		return haslo;
	}
	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public List<PrzedmiotOddzial> getPrzedmioty() {
		return przedmioty;
	}
	public void addPrzedmiot(PrzedmiotOddzial przedmiot) {
		przedmioty.add(przedmiot);
	}
	@Override
	public String toString() {
		return "nr: "+id +" "+imie+" "+nazwisko;
	}
	
	
}
