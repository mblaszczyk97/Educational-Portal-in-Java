package sysedu.domain;

public class Uczen {
	public String imie;
	String nazwisko;
	private Oddzial oddzial;
	
	public Uczen(String imie) {
		super();
		this.imie = imie;
	}

	public Uczen() {
		super();
		imie = new String("Goœæ");
	}

	public Oddzial getOddzial() {
		return oddzial;
	}

	public void setOddzial(Oddzial oddzial) {
		this.oddzial = oddzial;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}
	
	
	
	

}
