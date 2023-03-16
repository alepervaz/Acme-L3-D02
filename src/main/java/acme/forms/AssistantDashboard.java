
package acme.forms;

import java.util.Map;

import javax.validation.constraints.Positive;

import acme.entities.Approach;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of tutorial (the index in the list represent type of tutorial).
	private Map<Approach, Integer>	totalNumberOfTutorials;

	// Average, deviation, minimum, and maximum time length of his or her sessions.
	@Positive
	private Double					averageSession;
	@Positive
	private Double					deviationSession;
	@Positive
	private Double					minimumSession;
	@Positive
	private Double					maximumSession;

	// Average, deviation, minimum, and maximum time length of his or her tutorial.
	@Positive
	private Double					averageTutorial;
	@Positive
	private Double					deviationTutorial;
	@Positive
	private Double					minimumTutorial;
	@Positive
	private Double					maximumTutorial;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
