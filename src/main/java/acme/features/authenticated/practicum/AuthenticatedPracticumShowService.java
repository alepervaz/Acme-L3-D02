
package acme.features.authenticated.practicum;

import acme.entities.courses.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthenticatedPracticumShowService extends AbstractService<Authenticated, Practicum> {

	// Constants --------------------------------------------------------------
	protected static final String[]				PROPERTIES	= {
		"code", "title", "abstractPracticum", "goals", "estimatedTimeInHours", "draftMode"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuthenticatedPracticumRepository	repository;


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
		Principal principal;

		practicumId = super.getRequest().getData("id", int.class);
		principal = super.getRequest().getPrincipal();
		practicum = this.repository.findOnePracticumById(practicumId);
		status = !practicum.isDraftMode() && principal.isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);

		super.getBuffer().setData(practicum);
	}

	@Override
	public void unbind(final Practicum practicum) {
		assert practicum != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", practicum.getCourse());
		tuple = super.unbind(practicum, AuthenticatedPracticumShowService.PROPERTIES);
		tuple.put("course", choices);
		tuple.put("courses", courses);
		tuple.put("nameCompany", practicum.getCompany().getName());

		super.getResponse().setData(tuple);
	}
}
