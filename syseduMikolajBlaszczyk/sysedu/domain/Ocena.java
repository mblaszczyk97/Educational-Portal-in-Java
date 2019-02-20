package sysedu.domain;

public class Ocena {
	private int id;
	private float ocena;
	private String typ;
	private String komentarz;
	private int idPrzedmiot;
	private int idUczen;
	
	public Ocena(float ocena, String typ, String komentarz) {
		super();
		this.ocena = ocena;
		this.typ = typ;
		this.komentarz = komentarz;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getOcena() {
		return ocena;
	}

	public void setOcena(float ocena) {
		this.ocena = ocena;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getKomentarz() {
		return komentarz;
	}

	public void setKomentarz(String komentarz) {
		this.komentarz = komentarz;
	}

	@Override
	public String toString() {
		return "Ocena: " + ocena + "|" + typ + "| " + komentarz;
	}

	public int getIdPrzedmiot() {
		return idPrzedmiot;
	}

	public void setIdPrzedmiot(int idPrzedmiot) {
		this.idPrzedmiot = idPrzedmiot;
	}

	public int getIdUczen() {
		return idUczen;
	}

	public void setIdUczen(int idUczen) {
		this.idUczen = idUczen;
	}
	
	
	
}
