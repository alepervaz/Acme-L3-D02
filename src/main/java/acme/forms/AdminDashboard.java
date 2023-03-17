
package acme.forms;

import java.util.Map;

import javax.validation.constraints.Positive;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of principals with each role
	@Positive
	private Integer				numberOfAssistants;
	@Positive
	private Integer				numberOfAuditors;
	@Positive
	private Integer				numberOfCompanys;
	@Positive
	private Integer				numberOfConsumers;
	@Positive
	private Integer				numberOfProviders;
	@Positive
	private Integer				numberOfLecturers;

	// Ratio of peeps with both an email address and a link
	@Positive
	private Double				linkAndEmailPeepsRatio;

	// Ratios of critical and non-critical bulletins
	@Positive
	private Double				criticalBulletinsRatio;

	// Average, minimum, maximum, and standard deviation of the budget in the offers grouped by currency
	private Map<String, Double>	averageBudgetByCurrency;
	private Map<String, Double>	minimumBudgetByCurrency;
	private Map<String, Double>	maximumBudgetByCurrency;
	private Map<String, Double>	standardDeviationBudgetByCurrency;

	// Average, minimum, maximum, and standard deviation of the number of notes posted over the last 10 weeks
	@Positive
	private Double				averageNotesInLast10Weeks;
	@Positive
	private Double				minimumNotesInLast10Weeks;
	@Positive
	private Double				maximumNotesInLast10Weeks;
	@Positive
	private Double				standardDeviationNotesInLast10Weeks;


	// Derived attributes -----------------------------------------------------
	private Double getNonCriticalBulletinsRatio() {
		return 1.0 - this.criticalBulletinsRatio;
	}

	// Relationships ----------------------------------------------------------
}
