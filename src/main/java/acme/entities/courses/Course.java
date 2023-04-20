
package acme.entities.courses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.enums.Approach;
import acme.entities.enums.Type;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

	//	Serialisation identifier ---------------------------
	protected static final long	serialVersionUID	= 1L;

	//	Attributes -----------------------------------------
	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			courseAbstract;

	@NotNull
	protected Type				courseType;

	@NotNull
	protected Money				retailPrice;

	@URL
	protected String			link;

	protected boolean			draftMode;

	//	Derived attributes ------------------------------------

	//	estimatedTotalTime;

	//	Relationships -----------------------------------------

	@NotNull
	protected Approach			type;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
