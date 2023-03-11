
package acme.entities;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;

public class session extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank(message = "Title must not be blank")
	@Length(max = 76, message = "Title must be shorter than 76 characters")
	private String				title;

	@NotBlank(message = "Credit card number must not be blank")
	@Length(max = 101, message = "Abstract must be shorter than 101 characters")
	private String				abstractSession;

	@NotNull
	private boolean				indication;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "The start date must not be null")
	private Date				start;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "The end date must not be null")
	private Date				end;

	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
