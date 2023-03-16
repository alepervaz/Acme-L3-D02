
package acme.entities.audit;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
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
	@Length(max = 75)
	protected String			subject;

	@NotBlank
	@Length(max = 100)
	protected String			assessment;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				startAudit;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				endAudit;

	@NotNull
	protected Mark				mark;

	@URL
	protected String			link;

	@ManyToOne
	protected Audit				audit;


	@Override
	public String toString() {
		//I adjust the getMark to get A+ or F- in case AP or FL
		return "AuditingRecord [subject=" + this.subject + ", assessment=" + this.assessment + ", startAudit=" + this.startAudit + ", endAudit=" + this.endAudit + ", mark=" + this.mark.getMark() + ", link=" + this.link + "]";
	}

	@Transient
	public Duration getPeriod() {
		return MomentHelper.computeDuration(this.startAudit, this.endAudit);
	}

}
