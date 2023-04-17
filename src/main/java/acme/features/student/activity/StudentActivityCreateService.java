
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.enrolment.Enrolment;
import acme.entities.enums.Approach;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	// Constants -------------------------------------------------------------
	public static final String[]		PROPERTIES	= {
		"title", "summary", "startDate", "endDate", "link"
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
	public void authorise() {
		boolean status;
		int id;
		Enrolment enrolment;
		Principal principal;
		Student student;

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);
		principal = super.getRequest().getPrincipal();
		student = this.repository.findStudentByPrincipalId(principal.getActiveRoleId());
		status = student != null && enrolment.getStudent().equals(student) && !enrolment.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int id;
		Enrolment enrolment;

		object = new Activity();

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);

		object.setEnrolment(enrolment);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, StudentActivityCreateService.PROPERTIES);
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(MomentHelper.isAfter(object.getEndDate(), object.getStartDate()), "endPeriod", "student.activity.form.error.endPeriod-too-soon");
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		int enrolmentId;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Approach.class, object.getType());
		enrolmentId = super.getRequest().getData("enrolmentId", int.class);

		tuple = super.unbind(object, StudentActivityCreateService.PROPERTIES);
		tuple.put("types", choices);
		tuple.put("readonly", object.getEnrolment().isDraftMode());
		super.getResponse().setGlobal("enrolmentId", enrolmentId);

		super.getResponse().setData(tuple);
	}

}
