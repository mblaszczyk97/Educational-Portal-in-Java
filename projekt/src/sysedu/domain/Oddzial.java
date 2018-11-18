package sysedu.domain;

import java.util.ArrayList;
import java.util.List;


public class Oddzial {
	private String nazwa;
	
	private List<Przedmiot> przedmioty = new ArrayList<Przedmiot>();
	
	public Oddzial(String nazwa) {
		super();
		this.nazwa = nazwa;
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
		return "Oddzial [nazwa=" + nazwa + "]";
	}
	
}
