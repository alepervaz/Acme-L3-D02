
package acme.forms;

import java.time.Month;
import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Map<Month, Double>	averageSessionLength;
	private Map<Month, Double>	deviationSessionLength;
	private Map<Month, Double>	minimumSessionLength;
	private Map<Month, Double>	maximumSessionLength;
	private Map<Month, Double>	averagePracticaLength;
	private Map<Month, Double>	deviationPracticaLength;
	private Map<Month, Double>	minimumPracticaLength;
	private Map<Month, Double>	maximumPracticaLength;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
