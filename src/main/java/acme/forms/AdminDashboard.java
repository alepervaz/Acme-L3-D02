
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class AdminDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of principals with each role
	@Positive(message = "Number of administrators must be positive")
	private Integer						numberOfAssistants;
	@Positive(message = "Number of administrators must be positive")
	private Integer						numberOfAuditors;
	@Positive(message = "Number of administrators must be positive")
	private Integer						numberOfCompanys;
	@Positive(message = "Number of administrators must be positive")
	private Integer						numberOfConsumers;
	@Positive(message = "Number of administrators must be positive")
	private Integer						numberOfProviders;
	@Positive(message = "Number of administrators must be positive")
	private Integer						numberOfLecturers;

	// Ratio of peeps with both an email address and a link
	@Positive(message = "Ratio of peeps with both an email address and a link must be positive")
	private Double					linkAndEmailPeepsRatio;

	// Ratios of critical and non-critical bulletins
	@Positive(message = "Ratio of critical bulletins must be positive")
	private Double					criticalBulletinsRatio;

	// Average, minimum, maximum, and standard deviation of the budget in the offers grouped by currency
	private Map<String, Double>		averageBudgetByCurrency;
	private Map<String, Double>		minimumBudgetByCurrency;
	private Map<String, Double>		maximumBudgetByCurrency;
	private Map<String, Double>		standardDeviationBudgetByCurrency;

	// Average, minimum, maximum, and standard deviation of the number of notes posted over the last 10 weeks
	@Positive(message = "Average number of notes posted over the last 10 weeks must be positive")
	private Double					averageNotesInLast10Weeks;
	@Positive(message = "Minimum number of notes posted over the last 10 weeks must be positive")
	private Double					minimumNotesInLast10Weeks;
	@Positive(message = "Maximum number of notes posted over the last 10 weeks must be positive")
	private Double					maximumNotesInLast10Weeks;
	@Positive(message = "Standard deviation of the number of notes posted over the last 10 weeks must be positive")
	private Double					standardDeviationNotesInLast10Weeks;

	// Derived attributes -----------------------------------------------------
	private Double getNonCriticalBulletinsRatio() {
		return 1.0 - criticalBulletinsRatio;
	}

	// Relationships ----------------------------------------------------------
}
