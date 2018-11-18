package sysedu.domain;

public class Ocena {
	private float ocena;
	private String typ;
	private String komentarz;
	
	public Ocena(float ocena, String typ, String komentarz) {
		super();
		this.ocena = ocena;
		this.typ = typ;
		this.komentarz = komentarz;
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
	
	
	
}
