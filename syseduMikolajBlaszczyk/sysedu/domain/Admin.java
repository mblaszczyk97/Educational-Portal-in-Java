package sysedu.domain;

import java.util.ArrayList;
import java.util.List;

public class Admin {
	private int id;
	private String imie;
	private String nazwisko;
	private String nrTelefonu;
	
	
	public String getNrTelefonu() {
		return nrTelefonu;
	}
	public void setNrTelefonu(String nrTelefonu) {
		this.nrTelefonu = nrTelefonu;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public Admin(String uname) {
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

	
}
