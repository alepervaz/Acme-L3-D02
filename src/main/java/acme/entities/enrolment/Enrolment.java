
package acme.entities.enrolment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.courses.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Student;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			motivation;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	@NotNull
	protected Boolean			draftMode;


	// Derived attributes -----------------------------------------------------
	@Transient
	//TODO: función derivada
	private static Double workTime() {
		/*
		 * Se trata de una propiedad derivada
		 * que va a calcular el periodo de tiempo de una inscripccion
		 * mediante las actividades asociadas a esta.
		 */
		return null;
	}


	// Relationships -------------------------------------------------------------
	@Valid
	@NotNull
	@ManyToOne
	protected Student	student;

	@Valid
	@NotNull
	@ManyToOne
	protected Course	course;
}
