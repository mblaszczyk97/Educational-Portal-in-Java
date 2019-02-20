package sysedu.domain;

public class PrzedmiotOddzial {
	private int PrzedmiotID;
	private int OddzialID;
	private String nazwaPrzedmiotu=null;
	private String nazwaOddzialu=null;
	public int getPrzedmiotID() {
		return PrzedmiotID;
	}
	public void setPrzedmiotID(int przedmiotID) {
		PrzedmiotID = przedmiotID;
	}
	public int getOddzialID() {
		return OddzialID;
	}
	public void setOddzialID(int oddzialID) {
		OddzialID = oddzialID;
	}
	public String getNazwaPrzedmiotu() {
		return nazwaPrzedmiotu;
	}
	public void setNazwaPrzedmiotu(String nazwaPrzedmiotu) {
		this.nazwaPrzedmiotu = nazwaPrzedmiotu;
	}
	public String getNazwaOddzialu() {
		return nazwaOddzialu;
	}
	public void setNazwaOddzialu(String nazwaOddzialu) {
		this.nazwaOddzialu = nazwaOddzialu;
	}
	@Override
	public String toString() {
		return nazwaPrzedmiotu + " klasa: " + nazwaOddzialu;
	}
	
}
