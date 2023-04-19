
package acme.features.authenticated.student.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.enums.Type;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

	@Autowired
	protected StudentCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		SelectChoices typesChoices;
		Tuple tuple;

		typesChoices = SelectChoices.from(Type.class, object.getCourseType());

		tuple = super.unbind(object, "code", "title", "courseAbstract", "courseType", "retailPrice", "link");
		tuple.put("confirmation", false);
		tuple.put("readonly", true);
		tuple.put("lecturer", object.getLecturer().getIdentity().getFullName());
		tuple.put("types", typesChoices);

		super.getResponse().setData(tuple);
	}

}
