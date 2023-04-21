
package acme.entities.configuration;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Configuration extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}$")
	private String				defaultCurrency;

	@NotBlank
	@Pattern(regexp = "^([A-Z]{3})(-\\s*[A-Z]{3})*")
	private String				acceptedCurrencies;

	@NotBlank
	protected String			spamWords;

	@Range(min = 0, max = 1)
	protected double			spamThreshold;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
