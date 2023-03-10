
package acme.entities.sessionPracticum;

import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SessionPracticum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank(message = "Code must not be blank")
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$", message = "Code must be in the format 'AAA999'")
	private String				code;

	@NotBlank(message = "Title must not be blank")
	@Length(max = 75, message = "Title must be shorter than 76 characters")
	private String				title;

	@NotBlank(message = "Abstract session must not be blank")
	@Length(max = 100, message = "Abstract must be shorter than 101 characters")
	private String				abstractSession;

	@NotBlank(message = "Description must not be blank")
	@Length(max = 100, message = "Goals must be shorter than 101 characters")
	private String				description;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "The start date must not be null")
	private Date start;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "The end date must not be null")
	private Date			end;

	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@ManyToOne(optional = false)
	private Practicum			practicum;
}
