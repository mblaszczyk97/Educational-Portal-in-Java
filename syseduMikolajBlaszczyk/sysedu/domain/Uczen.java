package sysedu.domain;

public class Uczen {
	private int id;
	public String imie;
	String nazwisko;
	private Oddzial oddzial;
	private int oddzialID;
	private String login;
	private String haslo;
	
	public Uczen(int id, String imie, String nazwisko, int oddzialID) {
		super();
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.oddzialID = oddzialID;
	}
	public Uczen(int id, String imie, String nazwisko) {
		super();
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
	}
	public Uczen(String imie, String nazwisko) {
		super();
		this.imie = imie;
		this.nazwisko = nazwisko;
	}
	public Uczen(String imie) {
		super();
		this.imie = imie;
	}

	public Uczen() {
		super();
		imie = new String("Goœæ");
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getHaslo() {
		return haslo;
	}
	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}
	@Override
	public String toString() {
		return " " + id + ". " + nazwisko +" "+ imie;
	}
	
	
	
	

}
