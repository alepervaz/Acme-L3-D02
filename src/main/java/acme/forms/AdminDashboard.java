
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
	private Map<String, Integer>	roleCount;

	// Ratio of peeps with both an email address and a link
	private double					emailAndLinkRatio;

	// Ratios of critical and non-critical bulletins
	private double					criticalBulletinsRatio;
	private double					nonCriticalBulletinsRatio;

	// Average, minimum, maximum, and standard deviation of the budget in the offers grouped by currency
	private Map<String, Double>		averageBudget;
	private Map<String, Double>		minimumBudget;
	private Map<String, Double>		maximumBudget;
	private Map<String, Double>		standardDeviationBudget;

	// Average, minimum, maximum, and standard deviation of the number of notes posted over the last 10 weeks
	private double					averageNotes;
	private double					minimumNotes;
	private double					maximumNotes;
	private double					standardDeviationNotes;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
