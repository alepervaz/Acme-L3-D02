
package acme.features.student.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

	// Constants -------------------------------------------------------------
	public static final String[]		PROPERTIES	= {
		"title", "type"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository	repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		List<Activity> objects;
		int id;
		boolean showCreate;
		Enrolment enrolment;

		id = super.getRequest().getData("id", int.class);

		objects = this.repository.findActivitiesByEnrolmentId(id);
		enrolment = this.repository.findEnrolmentById(id);
		showCreate = !enrolment.isDraftMode();

		super.getResponse().setGlobal("enrolmentId", id);
		super.getResponse().setGlobal("showCreate", showCreate);

		super.getBuffer().setData(objects);
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
		status = student != null && enrolment.getStudent().equals(student);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, StudentActivityListService.PROPERTIES);

		super.getResponse().setData(tuple);
	}

}
