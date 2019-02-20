package sysedu.domain;

import java.util.ArrayList;
import java.util.List;

public class Materialy {
	
	private int przedmiotID;
	private String url;
	private String nazwa;
	private List<Materialy> materialy = new ArrayList<Materialy>();
	
	public Materialy(String url, String nazwa) {
		super();
		this.url = url;
		this.nazwa = nazwa;
	}
	
	public Materialy(String url, String nazwa, int przedmiotID) {
		super();
		this.url = url;
		this.nazwa = nazwa;
		this.przedmiotID = przedmiotID;
	}
	
	
	public int getPrzedmiotID() {
		return przedmiotID;
	}



	public void setPrzedmiotID(int przedmiotID) {
		this.przedmiotID = przedmiotID;
	}



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	
	public List<Materialy> getMaterialy() {
		return materialy;
	}

	@Override
	public String toString() {
		return nazwa;
	}

	
}
