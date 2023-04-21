
package acme.features.student.enrolment;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MessageHelper;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentPublishService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected StudentEnrolmentRepository repository;


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
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "cardHolderName", "cardLowerNibble");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
		SimpleDateFormat formatter;
		String format;
		Integer cvv;
		String expireDateString;
		Date expireDate;

		expireDateString = super.getRequest().getData("expireDate", String.class).trim().concat(" 00:00");
		format = MessageHelper.getMessage("default.format.moment", null, "yyyy/MM/dd HH:mm", super.getRequest().getLocale());
		formatter = new SimpleDateFormat(format);
		formatter.setLenient(false);

		expireDate = formatter.parse(expireDateString, new ParsePosition(0));
		cvv = super.getRequest().getData("cvv", Integer.class);

		super.state(object.getCardLowerNibble() != null && object.getCardLowerNibble().matches("^([0-9]{16})$"), "cardLowerNibble", "student.enrolment.form.error.invalid-card-number");
		super.state(!"".equals(object.getCardHolderName()), "cardHolderName", "student.enrolment.form.error.invalid-card-holder");
		super.state(cvv != null, "cvv", "student.enrolment.form.error.invalid-cvv");
		super.state(expireDate != null, "expireDate", "student.enrolment.form.error.invalid-expireDate-format");
		if (expireDate != null)
			super.state(!MomentHelper.isAfterOrEqual(MomentHelper.getCurrentMoment(), expireDate), "expireDate", "student.enrolment.form.error.invalid-expireDate-value");
		if (cvv != null)
			super.state(String.valueOf(cvv).length() == 3, "cvv", "student.enrolment.form.error.invalid-cvv");

		super.state(object.isDraftMode(), "draftMode", "student.enrolment.form.error.finalised");
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		object.setDraftMode(false);
		object.setCardLowerNibble(object.getCardLowerNibble().substring(12, 16));

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		List<Course> courses;
		final SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findManyCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "cardHolderName", "cardLowerNibble", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("draftMode", object.isDraftMode());

		super.getResponse().setData(tuple);
	}
}
