
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Assistant extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75, message = "title shorter than 76 characters")
	protected String			supervisor;

	@NotBlank
	@Length(max = 100, message = "expertiseFields shorter than 101 characters")
	protected String			expertiseFields;

	@NotBlank
	@Length(max = 100, message = "resume shorter than 101 characters")
	protected String			resume;

	@URL
	protected String			furtherInfo;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
