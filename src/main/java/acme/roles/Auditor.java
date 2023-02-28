
package acme.roles;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
	private String				firm;

	@NotBlank
	@Length(max = 26)
	private String				proffesionalId;

	@NotNull
	@Valid
	@Size(max = 101)
	private List<String>		certifications;

	@URL
	private String				link;

}
