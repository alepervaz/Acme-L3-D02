
package acme.features.authenticated.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferShowService extends AbstractService<Authenticated, Offer> {

	// Constants -------------------------------------------------------------
	protected static final String[]			PROPERTIES	= {
		"instantiation", "heading", "summary", "startDate", "endDate", "price", "link", "draftMode"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedOfferRepository	repository;

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
		Offer offer;

		id = super.getRequest().getData("id", int.class);
		offer = this.repository.findOneOfferById(id);
		status = offer != null;
		status = status && super.getRequest().getPrincipal().hasRole(Authenticated.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;
		boolean isAdmin;
		isAdmin = super.getRequest().getPrincipal().hasRole(Administrator.class);
		tuple = super.unbind(object, AuthenticatedOfferCreateService.PROPERTIES);
		tuple.put("editable", object.isDraftMode() && isAdmin);
		// tuple.put("price", this.currencyService.changeIntoSystemCurrency(object.getPrice())); // Si es Authenticated
		tuple.put("isAdmin", isAdmin);

		super.getResponse().setData(tuple);
	}
}
