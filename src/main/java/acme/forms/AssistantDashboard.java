
package acme.forms;

import java.util.Map;

import acme.datatypes.Statistic;
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
	private Map<String, Long>	totalNumberOfTutorialByMonth;

	// Average, deviation, minimum, and maximum time length of his or her sessions.
	private Statistic			sessionLength;

	// Average, deviation, minimum, and maximum time length of his or her tutorial.
	private Statistic			tutorialLength;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
