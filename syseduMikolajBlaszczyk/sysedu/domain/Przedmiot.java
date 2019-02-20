package sysedu.domain;

public class Przedmiot {
	private String nazwa;
	private int id;
	
	

	public Przedmiot(String nazwa, int id) {
		super();
		this.nazwa = nazwa;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Przedmiot(String nazwa) {
		super();
		this.nazwa = nazwa;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	@Override
	public String toString() {
		return nazwa;
	}
	
	
	
	
}
