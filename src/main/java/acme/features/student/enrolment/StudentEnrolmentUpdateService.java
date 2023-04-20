
package acme.features.student.enrolment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentUpdateService extends AbstractService<Student, Enrolment> {

	// Constants -------------------------------------------------------------
	public static final String[]			PROPERTIES	= {
		"code", "motivation", "goals"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected StudentEnrolmentRepository	repository;


	// AbstractService <Student,Enrolment> ----------------------------------------------
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Enrolment enrolment;
		final Principal principal;
		Student student;

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);
		principal = super.getRequest().getPrincipal();
		student = this.repository.findStudentByPrincipalId(principal.getActiveRoleId());
		status = student != null && enrolment.getStudent().equals(student) && enrolment.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment enrolment) {
		assert enrolment != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(enrolment, StudentEnrolmentCreateService.PROPERTIES);
		enrolment.setCourse(course);
	}

	@Override
	public void validate(final Enrolment enrolment) {
		assert enrolment != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment existing;

			existing = this.repository.findOneEnrolmentByCode(enrolment.getCode());
			super.state(existing == null || existing.getId() == enrolment.getId(), "code", "asistant.tutorial.form.error.not-unique-code");
		}

	}

	@Override
	public void perform(final Enrolment enrolment) {
		assert enrolment != null;

		this.repository.save(enrolment);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		List<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findManyCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, StudentEnrolmentCreateService.PROPERTIES);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("draftMode", object.isDraftMode());

		super.getResponse().setData(tuple);

	}

}
