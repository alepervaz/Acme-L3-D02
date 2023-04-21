
package acme.features.authenticated.offer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedOfferController extends AbstractController<Authenticated, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedOfferListService		listService;

	@Autowired
	protected AuthenticatedOfferShowService		showService;
	@Autowired
	protected AuthenticatedOfferCreateService	createService;

	@Autowired
	protected AuthenticatedOfferUpdateService	updateService;
	@Autowired
	protected AuthenticatedOfferDeleteService	deleteService;
	@Autowired
	protected AuthenticatedOfferPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("publish", "update", this.publishService);

	}
}
