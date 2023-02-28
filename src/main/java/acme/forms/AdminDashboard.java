
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of principals with each role
	private Integer						numberOfAssistants;
	private Integer						numberOfAuditors;
	private Integer						numberOfCompanys;
	private Integer						numberOfConsumers;
	private Integer						numberOfProviders;
	private Integer						numberOfLecturers;

	// Ratio of peeps with both an email address and a link
	private double					linkAndEmailPeepsRatio;

	// Ratios of critical and non-critical bulletins
	private double					criticalBulletinsRatio;
	private double					nonCriticalBulletinsRatio;

	// Average, minimum, maximum, and standard deviation of the budget in the offers grouped by currency
	private Map<String, Double>		averageBudgetByCurrency;
	private Map<String, Double>		minimumBudgetByCurrency;
	private Map<String, Double>		maximumBudgetByCurrency;
	private Map<String, Double>		standardDeviationBudgetByCurrency;

	// Average, minimum, maximum, and standard deviation of the number of notes posted over the last 10 weeks
	private double					averageNotesInLast10Weeks;
	private double					minimumNotesInLast10Weeks;
	private double					maximumNotesInLast10Weeks;
	private double					standardDeviationNotesInLast10Weeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
