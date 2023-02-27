
package acme.entities.practicum;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotBlank(message = "Code must not be blank")
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}", message = "Code must be in the format 'AAA9999'")
	private String					code;

	@NotBlank(message = "Title must not be blank")
	@Length(max = 76, message = "Title must be shorter than 76 characters")
	private String					title;

	@NotBlank(message = "Credit card number must not be blank")
	@Length(max = 101, message = "Abstract must be shorter than 101 characters")
	private String					abstractPracticum; // TODO: Rename

	@NotBlank(message = "Description must not be blank")
	@Length(max = 101, message = "Description must be shorter than 101 characters")
	private String					goals;

	private Double					estimatedTimeInHours;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@OneToMany(mappedBy = "practicum")
	private List<SessionPracticum>	sessions;

	@Valid
	@ManyToOne(optional = false)
	private Company					company;
}
