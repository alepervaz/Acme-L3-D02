
package acme.forms;

import java.util.List;

import javax.validation.constraints.Positive;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of lectures (the index in the list represent type of tutorial).
	private List<Integer>		totalNumberOfLectures;

	// Average, deviation, minimum, and maximum time length of lectures.
	@Positive(message = "The average lecture length must be positive")
	private Double				averageLectureTime;
	@Positive(message = "The deviation lecture length must be positive")
	private Double				deviationLectureTime;
	@Positive(message = "The minimum lecture length must be positive")
	private Double				minimumLectureTime;
	@Positive(message = "The maximum lecture length must be positive")
	private Double				maximumLectureTime;

	// Average, deviation, minimum, and maximum time length of courses.
	@Positive(message = "The average course length must be positive")
	private Double				averageCourseTime;
	@Positive(message = "The deviation course length must be positive")
	private Double				deviationCourseTime;
	@Positive(message = "The minimum course length must be positive")
	private Double				minimumCourseTime;
	@Positive(message = "The maximum course length must be positive")
	private Double				maximumCourseTime;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
