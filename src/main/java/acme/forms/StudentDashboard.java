
package acme.forms;

import java.util.List;

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
	private Double				averageAtivities;
	private Double				deviationAtivities;
	private Double				minimumAtivities;
	private Double				maximumAtivities;

	// Average, deviation, minimum, and maximum time length of his or her enrolled.
	private Double				averageEnrolled;
	private Double				deviationEnrolled;
	private Double				minimumEnrolled;
	private Double				maximumEnrolled;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
