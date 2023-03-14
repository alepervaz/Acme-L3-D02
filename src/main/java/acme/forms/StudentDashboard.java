
package acme.forms;

import java.util.List;

import javax.validation.constraints.Positive;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of tutorial (the index in the list represent type of tutorial).
	private List<Integer>		totalNumberOfActivities;

	//Average, deviation, minimum, and maximum period of the activities in his or her workbook;
	@Positive(message = "The average activities length must be positive")
	private Double				averageAtivities;
	@Positive(message = "The deviation activities length must be positive")
	private Double				deviationAtivities;
	@Positive(message = "The minimum activities length must be positive")
	private Double				minimumAtivities;
	@Positive(message = "The maximum activities length must be positive")
	private Double				maximumAtivities;

	// Average, deviation, minimum, and maximum time length of his or her enrolled.
	@Positive(message = "The average enrolled length must be positive")
	private Double				averageEnrolled;
	@Positive(message = "The deviation enrolled length must be positive")
	private Double				deviationEnrolled;
	@Positive(message = "The minimum enrolled length must be positive")
	private Double				minimumEnrolled;
	@Positive(message = "The maximum enrolled length must be positive")
	private Double				maximumEnrolled;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
