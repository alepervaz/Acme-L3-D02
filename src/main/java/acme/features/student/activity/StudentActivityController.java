
package acme.features.student.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.activities.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentActivityController extends AbstractController<Student, Activity> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityListService	listService;

	@Autowired
	protected StudentActivityShowService	showService;

	@Autowired
	protected StudentActivityCreateService	createService;

	@Autowired
	protected StudentActivityDeleteService	deleteService;

	@Autowired
	protected StudentActivityUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
	}

}
