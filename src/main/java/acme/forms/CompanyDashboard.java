package acme.forms;

import java.util.List;

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
	private Double				averageSessionLength;
	private Double				deviationSessionLength;
	private Double				minimumSessionLength;
	private Double				maximumSessionLength;


	// Average, deviation, minimum, and maximum period length of their practica.
	private Double				averagePracticaLength;
	private Double				deviationPracticaLength;
	private Double				minimumPracticaLength;
	private Double				maximumPracticaLength;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
