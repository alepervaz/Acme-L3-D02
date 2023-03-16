
package acme.entities.audit;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
public class Audit extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	/*
	 * @NotNull
	 * protected Course course;
	 */

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	protected String				code;

	@NotBlank
	@Length(max = 100)
	protected String				conclusion;

	@NotBlank
	@Length(max = 100)
	protected String				strongPoints;

	@NotBlank
	@Length(max = 100)
	protected String				weakPoints;

	@OneToMany
	protected List<AuditingRecord>	auditingRecords;

	@ManyToOne
	protected Auditor				auditor;


	@Transient
	protected String marks() {
		final String marks = this.auditingRecords.stream().map(a -> a.getMark().toString()).collect(Collectors.toList()).toString();
		return marks.substring(1, marks.length() - 1);
	}

}
