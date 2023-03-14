
package acme.entities.audit;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.framework.data.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Audit extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	protected Course				course;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}", message = "Letras de A-Z entre 1 y 3 veces, seguidas de tres numeros entre 0 y 9")
	protected String				code;

	@NotBlank
	@Length(max = 101)
	protected String				conclusion;

	@NotBlank
	@Length(max = 101)
	protected String				strongPoints;

	@NotBlank
	@Length(max = 101)
	protected String				weakPoints;

	@OneToMany
	protected List<AuditingRecord>	auditingRecords;

	@NotNull
	protected Boolean				draftMode			= true;

	// Derived attributes -----------------------------------------------------


	@Transient
	public String mark() {
		final String result = this.auditingRecords.toString();
		return result.substring(1, result.length() - 1);

	}

}
