
package acme.entities.practicum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import acme.entities.courses.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Practicum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	private String				code;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				abstractPracticum;

	@NotBlank
	@Length
	private String				goals;

	// TODO: Create a custom validator to validate if the estimated time is between 90% and 110% of the total time.
	@NotNull
	@Positive
	private Double				estimatedTimeInHours;

	@NotNull
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Company				company;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Course				course;

	// TODO: To use the validator it is neccesary change the access.
	// @NotNull
	// @Valid
	// @OneToMany(mapedBy="practicum")
	// private List<SessionPracticum>	sessions;
}
