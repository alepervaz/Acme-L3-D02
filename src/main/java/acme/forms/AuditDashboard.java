
package acme.forms;

import java.time.Duration;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditDashboard extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	protected Integer			auditCount;
	protected Double			auditAverageNumberRecords;
	protected Double			auditDeviationNumberRecords;
	protected Integer			auditMinNumberRecords;
	protected Integer			auditMaxNumberRecords;

	protected Duration			recordAveragePeriod;
	protected Duration			recordDeviationPeriod;
	protected Duration			recordMinPeriod;
	protected Duration			recordMmaxPeriod;
}
