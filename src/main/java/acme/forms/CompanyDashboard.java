package acme.forms;

import java.util.List;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of practicas (the index in the list represent the months).
	private List<Integer>		totalNumberOfPracticaByMonth;

	// Average, deviation, minimum, and maximum period length of the sessions in their practica.
	@Positive(message = "The average session length must be positive")
	private Double				averageSessionLength;
	@Positive(message = "The deviation session length must be positive")
	private Double				deviationSessionLength;
	@Positive(message = "The minimum session length must be positive")
	private Double				minimumSessionLength;
	@Positive(message = "The maximum session length must be positive")
	private Double				maximumSessionLength;


	// Average, deviation, minimum, and maximum period length of their practica.
	@Positive(message = "The average practica length must be positive")
	private Double				averagePracticaLength;
	@Positive(message = "The deviation practica length must be positive")
	private Double				deviationPracticaLength;
	@Positive(message = "The minimum practica length must be positive")
	private Double				minimumPracticaLength;
	@Positive(message = "The maximum practica length must be positive")
	private Double				maximumPracticaLength;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
