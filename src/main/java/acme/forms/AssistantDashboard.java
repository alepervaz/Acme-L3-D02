
package acme.forms;

import java.util.List;

import javax.validation.constraints.Positive;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of tutorial (the index in the list represent type of tutorial).
	private List<Integer>		totalNumberOfTutorials;

	// Average, deviation, minimum, and maximum time length of his or her sessions.
	@Positive(message = "The average session length must be positive")
	private Double				averageSession;
	@Positive(message = "The deviation session length must be positive")
	private Double				deviationSession;
	@Positive(message = "The minimum session length must be positive")
	private Double				minimumSession;
	@Positive(message = "The maximum session length must be positive")
	private Double				maximumSession;

	// Average, deviation, minimum, and maximum time length of his or her tutorial.
	@Positive(message = "The average Tutorial length must be positive")
	private Double				averageTutorial;
	@Positive(message = "The deviation Tutorial length must be positive")
	private Double				deviationTutorial;
	@Positive(message = "The minimum Tutorial length must be positive")
	private Double				minimumTutorial;
	@Positive(message = "The maximum Tutorial length must be positive")
	private Double				maximumTutorial;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
