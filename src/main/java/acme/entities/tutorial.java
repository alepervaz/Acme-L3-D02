
package acme.entities;

import java.util.List;

import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;

public class tutorial extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 75, message = "title shorter than 76 characters")
	protected String			title;

	@NotBlank
	@Length(max = 100, message = "summary shorter than 101 characters")
	protected String			summary;

	@NotBlank
	@Length(max = 100, message = "goals shorter than 101 characters")
	protected String			goals;

	@NotNull
	@Positive
	protected Integer			estimatedTime;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	@Valid
	@OneToMany
	protected List<Object>		sessions;

}