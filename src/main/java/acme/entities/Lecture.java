
package acme.entities;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.enumerates.SessionType;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;

public class Lecture extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotBlank(message = "Title must not be blank")
	@Length(max = 76, message = "Title must be shorter than 76 characters")
	private String				title;

	@NotBlank(message = "Abstract must not be blank")
	@Length(max = 101, message = "Abstract must be shorter than 101 characters")
	private String				lectureAbstract;

	@NotNull()
	@Min(0)
	private Double				time;

	@NotBlank(message = "Body must not be blank")
	@Length(max = 101, message = "Body must be shorter than 101 characters")
	private String				body;

	@NotNull()
	private SessionType			sessionType;

	@URL
	private String				furtherInformation;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Valid
	@ManyToOne(optional = false)
	private Lecturer			lecturer;

	@Valid
	@ManyToOne(optional = true)
	private Course				course;
}
