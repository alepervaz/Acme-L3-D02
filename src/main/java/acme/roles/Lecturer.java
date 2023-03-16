
package acme.roles;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;

public class Lecturer extends AbstractEntity {

	// Serialisation identifier

	protected static final long	serialVersionUID	= 1L;

	//Attributes
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
	protected String			link;

}