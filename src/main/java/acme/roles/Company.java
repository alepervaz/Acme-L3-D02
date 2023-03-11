
package acme.roles;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.practicum.Practicum;
import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Company extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank(message = "Name must not be blank")
	@Length(max = 75, message = "Name must be shorter than 76 characters")
	protected String			name;

	@NotBlank(message = "VAT number must not be blank")
	@Length(max = 25, message = "VAT number must be shorter than 26 characters")
	protected String			vatNumber;

	@NotBlank(message = "Summary must not be blank")
	@Length(max = 100, message = "Summary must be shorter than 101 characters")
	protected String			summary;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
