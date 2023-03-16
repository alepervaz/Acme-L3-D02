
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
	@Positive
	private Double				averageLectureTime;
	@Positive
	private Double				deviationLectureTime;
	@Positive
	private Double				minimumLectureTime;
	@Positive
	private Double				maximumLectureTime;

	// Average, deviation, minimum, and maximum time length of courses.
	@Positive
	private Double				averageCourseTime;
	@Positive
	private Double				deviationCourseTime;
	@Positive
	private Double				minimumCourseTime;
	@Positive
	private Double				maximumCourseTime;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
