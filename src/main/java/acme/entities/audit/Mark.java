
package acme.entities.audit;

public enum Mark {

	AP("A+"), A("A"), B("B"), C("C"), F("F"), FL("F-");


	private String markString;


	private Mark(final String value) {
		this.markString = value;
	}

	public String getMark() {
		return this.markString;
	}
}
