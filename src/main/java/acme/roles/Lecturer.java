
package acme.roles;

import java.util.List;

import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.Lecture;
import acme.framework.data.AbstractRole;

public class Lecturer extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 76)
	protected String			almaMater;

	@NotBlank
	@Length(max = 101)
	protected String			resume;

	@NotBlank
	@Length(max = 101)
	protected String			qualifications;

	@URL
	protected String			furtherInfo;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@OneToMany(mappedBy = "lecture")
	private List<Lecture>		lectures;
}
