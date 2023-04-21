
package acme.forms;

import java.util.List;

import javax.validation.constraints.Positive;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of practicas (the index in the list represent the months).
	private List<Integer>		totalNumberOfPracticaByMonth;

	// Average, deviation, minimum, and maximum period length of the sessions in their practica.
	@Positive
	private Double				averageSessionLength;
	@Positive
	private Double				deviationSessionLength;
	@Positive
	private Double				minimumSessionLength;
	@Positive
	private Double				maximumSessionLength;

	// Average, deviation, minimum, and maximum period length of their practica.
	@Positive
	private Double				averagePracticaLength;
	@Positive
	private Double				deviationPracticaLength;
	@Positive
	private Double				minimumPracticaLength;
	@Positive
	private Double				maximumPracticaLength;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
