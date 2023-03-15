
package acme.entities;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class note {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;
	// Attributes -------------------------------------------------------------
	@NotNull
	@Temporal(TemporalType.DATE)
	protected Date				moment;

	@NotBlank
	@Length(max = 75, message = "title shorter than 76 characters")
	protected String			title;

	@NotBlank
	@Length(max = 75, message = "autor shorter than 76 characters")
	protected String			autor;

	@NotBlank
	@Length(max = 100, message = "message shorter than 101 characters")
	protected String			message;

	@Email
	protected String			emailAddress;

	@URL
	protected String			link;
	// Derived attributes -----------------------------------------------------
	// Relationships ----------------------------------------------------------
}
