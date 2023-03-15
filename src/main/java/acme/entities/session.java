
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
	@Length(max = 75, message = "Title must be shorter than 76 characters")
	protected String			title;

	@NotBlank(message = "Abstract must not be blank")
	@Length(max = 100, message = "Abstract must be shorter than 101 characters")
	protected String			abstractSession;

	@NotNull
	protected Approach			indication;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "The start date must not be null")
	protected Date				start;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "The end date must not be null")
	protected Date				end;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
