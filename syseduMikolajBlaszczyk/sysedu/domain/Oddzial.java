package sysedu.domain;

import java.util.ArrayList;
import java.util.List;


public class Oddzial {
	private String nazwa;
	private int id;
	private String rok;


	public Oddzial(String nazwa, int id, String rok) {
		super();
		this.nazwa = nazwa;
		this.id = id;
		this.rok = rok;
	}
	
	public Oddzial(String nazwa, String rok) {
		super();
		this.nazwa = nazwa;
		this.rok = rok;
	}



	private List<Przedmiot> przedmioty = new ArrayList<Przedmiot>();
	
	public String getRok() {
		return rok;
	}



	public void setRok(String rok) {
		this.rok = rok;
	}


	
	public Oddzial(String nazwa) {
		super();
		this.nazwa = nazwa;
	} 
	
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNazwa() {
		return nazwa;
	}



	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}



	public List<Przedmiot> getPrzedmioty() {
		return przedmioty;
	}



	public void addPrzedmiot(Przedmiot przedmiot) {
		przedmioty.add(przedmiot);
	}



	@Override
	public String toString() {
		return nazwa;
	}



	
	
}
