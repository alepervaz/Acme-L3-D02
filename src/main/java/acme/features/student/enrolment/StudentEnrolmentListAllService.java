
package acme.features.student.enrolment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentListAllService extends AbstractService<Student, Enrolment> {

	// Constants -------------------------------------------------------------
	public static final String[]			PROPERTIES	= {
		"code", "motivation", "goals", "draftMode"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected StudentEnrolmentRepository	repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		List<Enrolment> enrolments;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		enrolments = this.repository.findEnrolmentsByStudentId(principal.getActiveRoleId());

		super.getBuffer().setData(enrolments);
	}

	@Override
	public void unbind(final Enrolment enrolment) {
		assert enrolment != null;

		Tuple tuple;

		tuple = super.unbind(enrolment, StudentEnrolmentListAllService.PROPERTIES);
		tuple.put("courseTitle", enrolment.getCourse().getTitle());

		super.getResponse().setData(tuple);
	}
}
