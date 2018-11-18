package sysedu.domain;

public class Przedmiot {
	private String nazwa;
	
	

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
