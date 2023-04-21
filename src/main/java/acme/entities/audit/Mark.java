
package acme.entities.audit;

public enum Mark {

	AP("A+"), A("A"), B("B"), C("C"), F("F"), FL("F-");


	private String markString;


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getMark();
	}

	Mark(final String value) {
		this.markString = value;
	}

	public String getMark() {
		return this.markString;
	}

}
