
package acme.features.any.tutorial;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AnyTutorialController extends AbstractController<Authenticated, Tutorial> {

	//Internal State ----------------------------------------------------
	@Autowired
	protected AnyTutorialListService	listService;

	@Autowired
	protected AnyTutorialShowService	showService;


	//Construtors --------------------------------
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
