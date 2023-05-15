
package acme.features.authenticated.offer;

import java.util.Collection;

import acme.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferListService extends AbstractService<Authenticated, Offer> {

	// Constants -------------------------------------------------------------
	protected static final String[]			PROPERTIES	= {
		"instantiation", "heading", "summary", "startDate", "endDate", "price", "link", "draftMode"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuthenticatedOfferRepository	repository;
	@Autowired
	protected CurrencyService currencyService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Collection<Offer> objects;

		objects = this.repository.findManyOffer();

		super.getBuffer().setData(objects);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, PROPERTIES);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;
		boolean isAdmin;
		isAdmin = super.getRequest().getPrincipal().hasRole(Administrator.class);
		tuple = super.unbind(object, PROPERTIES);
		tuple.put("price", this.currencyService.changeIntoSystemCurrency(object.getPrice()));
		tuple.put("isAdmin", isAdmin);
		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Offer> objects) {
		assert objects != null;

		boolean isAdmin;
		isAdmin = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setGlobal("isAdmin", isAdmin);
		super.unbind(objects);
	}
}
