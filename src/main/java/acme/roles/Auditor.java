
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

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
