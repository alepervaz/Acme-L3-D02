
package acme.entities.audit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class AuditingRecord extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 76)
	private String				subject;

	@NotBlank
	@Length(max = 101)
	private String				assessment;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				startAudit;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				endAudit;

	@NotNull
	private Mark				mark;

	@URL
	private String				link;


	@Override
	public String toString() {
		return "AuditingRecord [subject=" + this.subject + ", assessment=" + this.assessment + ", startAudit=" + this.startAudit + ", endAudit=" + this.endAudit + ", mark=" + this.mark.getMark() + ", link=" + this.link + "]";
	}

}
