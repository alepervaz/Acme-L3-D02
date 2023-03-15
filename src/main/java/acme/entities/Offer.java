
package acme.entities;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;

public class Offer extends AbstractEntity {

	// Serialisation identifier
	protected static final long	serialVersionUID	= 1L;

	// Attributes
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				instantiation;

	@NotBlank
	@Length(max = 75)
	protected String			heading;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	//Al menos un día después de la fecha instantation
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startDate;

	//Debe durar al menos una semana
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endDate;

	@NotNull
	@Min(0)
	protected Double			price;

	@URL
	protected String			link;

}
