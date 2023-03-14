
package acme.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.enumerates.SessionType;
import acme.framework.data.AbstractEntity;

public class Course extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotBlank(message = "Code must not be blank")
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}\\\\d{3}$", message = "Code must be in the format 'AAA9999'")
	private String				code;

	@NotBlank(message = "Title must not be blank")
	@Length(max = 76, message = "Title must be shorter than 76 characters")
	private String				title;

	@NotBlank(message = "Abstract must not be blank")
	@Length(max = 101, message = "Abstract must be shorter than 101 characters")
	private String				courseAbstract;

	@Min(0)
	private Double				retailPrice;

	@URL
	private String				furtherInformation;

	@NotNull()
	protected SessionType		type;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@OneToMany(mappedBy = "lecture")
	private List<Lecture>		lectures;

}
