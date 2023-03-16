
package acme.roles;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;

public class Student extends AbstractEntity {

	// Serialisation identifier

	protected static final long	serialVersionUID	= 1L;

	//Attributes
	@NotBlank
	@Length(max = 75)
	protected String			statements;

	@NotBlank
	@Length(max = 100)
	protected String			strongFeatures;

	@NotBlank
	@Length(max = 100)
	protected String			weakFeatures;

	@URL
	protected String			link;

}
