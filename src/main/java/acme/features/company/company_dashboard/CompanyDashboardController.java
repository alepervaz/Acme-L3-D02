
package acme.features.company.company_dashboard;

import acme.forms.CompanyDashboard;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class CompanyDashboardController extends AbstractController<Company, CompanyDashboard> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private CompanyDashboardShowService showService;


	// Constructors -----------------------------------------------------------
	@PostConstruct
	private void initialise() {
		super.addBasicCommand("show", this.showService);
	}
}
