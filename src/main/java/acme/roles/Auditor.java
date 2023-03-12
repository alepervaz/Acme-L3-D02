
package acme.roles;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Auditor extends AbstractEntity {

	// Serialisation identifier

	protected static final long	serialVersionUID	= 1L;

	//Attributes

	@NotBlank
	@Length(max = 76)
	protected String			firm;

	@NotBlank
	@Length(max = 26)
	protected String			proffesionalId;

	@NotBlank
	@Size(max = 101)
	protected String			certifications;

	@URL
	protected String			link;

}
