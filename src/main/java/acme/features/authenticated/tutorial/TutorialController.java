
package acme.features.authenticated.tutorial;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class TutorialController extends AbstractController<Authenticated, Tutorial> {

	//Internal State ----------------------------------------------------
	@Autowired
	protected TutorialListService	listService;

	@Autowired
	protected TutorialShowService	showService;


	//Construtors --------------------------------
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
