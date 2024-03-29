
package acme.forms;

import java.time.Duration;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	private Integer				totalAuditCount;
	private Double				auditAverageNumberRecords;
	private Double				auditDeviationNumberRecords;
	private Integer				auditMinNumberRecords;
	private Integer				auditMaxNumberRecords;

	private Duration			recordAveragePeriod;
	private Duration			recordDeviationPeriod;
	private Duration			recordMinPeriod;
	private Duration			recordMmaxPeriod;
}
