
package acme.entities.practicum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import acme.roles.Course;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

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

	@NotBlank(message = "Credit card number must not be blank")
	@Length(max = 100, message = "Abstract must be shorter than 101 characters")
	private String				abstractPracticum;

	@NotBlank(message = "Description must not be blank")
	@Length(max = 100, message = "Description must be shorter than 101 characters")
	private String				goals;

	@NotNull(message = "Estimated time in hours must not be blank")
	private Double				estimatedTimeInHours;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@ManyToOne(optional = false)
	private Company				company;

	@Valid
	@ManyToOne(optional = false)
	private Course				course;
}
