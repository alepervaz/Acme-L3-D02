
package acme.features.company.practicum;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumPublishService extends AbstractService<Company, Practicum> {

	// Constants -------------------------------------------------------------
	public static final String[]			PROPERTIES	= {
		"code", "title", "abstractPracticum", "goals", "estimatedTimeInHours"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository	repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumId;
		Practicum practicum;
		Company company;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		company = practicum == null ? null : practicum.getCompany();
		status = practicum != null && practicum.getDraftMode() && principal.hasRole(company);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum practicum;
		int practicumId;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);

		super.getBuffer().setData(practicum);
	}

	@Override
	public void bind(final Practicum practicum) {
		assert practicum != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(practicum, CompanyPracticumPublishService.PROPERTIES);
		practicum.setCourse(course);
	}

	@Override
	public void validate(final Practicum practicum) {
		assert practicum != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean isUnique;

			isUnique = this.repository.findManyPracticumByCode(practicum.getCode()).isEmpty();
			super.state(!isUnique, "code", "company.practicum.form.error.not-unique-code");
		}

		// Únicamente se deberá de comprobar que el tiempo estimado es correcto cuando se va a publicar.
		if (!super.getBuffer().getErrors().hasErrors("estimatedTimeInHours")) {
			Collection<SessionPracticum> sessions;
			Double estimatedTimeInHours;
			double totalHours;

			estimatedTimeInHours = practicum.getEstimatedTimeInHours();
			sessions = this.repository.findManySessionPracticesByPracticumId(practicum.getId());

			totalHours = sessions.stream().mapToDouble(session -> {
				Date start;
				Date end;

				start = session.getStart();
				end = session.getEnd();

				return MomentHelper.computeDuration(start, end).toHours();
			}).sum();

			super.state(totalHours >= estimatedTimeInHours * 0.9 && totalHours <= estimatedTimeInHours * 1.1, "estimatedTimeInHours", "company.practicum.form.error.not-in-range");
		}
	}

	@Override
	public void perform(final Practicum practicum) {
		assert practicum != null;

		practicum.setDraftMode(false);

		this.repository.save(practicum);
	}

	@Override
	public void unbind(final Practicum practicum) {
		assert practicum != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", practicum.getCourse());

		tuple = super.unbind(practicum, CompanyPracticumPublishService.PROPERTIES);
		tuple.put("course", choices);
		tuple.put("courses", courses);

		super.getResponse().setData(tuple);
	}
}
