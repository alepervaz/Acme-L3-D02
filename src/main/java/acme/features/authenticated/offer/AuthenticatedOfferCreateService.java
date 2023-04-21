
package acme.features.authenticated.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferCreateService extends AbstractService<Authenticated, Offer> {

	// Constants -------------------------------------------------------------
	public static final String[]			PROPERTIES	= {
		"instantiation", "heading", "summary", "startDate", "endDate", "price", "link", "draftMode"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedOfferRepository	repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Offer object;

		object = new Offer();
		object.setDraftMode(true);
		object.setInstantiation(MomentHelper.getCurrentMoment());
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, AuthenticatedOfferCreateService.PROPERTIES);
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;
		object.setInstantiation(MomentHelper.getCurrentMoment());

	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		Tuple tuple;
		boolean isAdmin;
		isAdmin = super.getRequest().getPrincipal().hasRole(Administrator.class);
		tuple = super.unbind(object, AuthenticatedOfferCreateService.PROPERTIES);
		tuple.put("editable", object.isDraftMode() && isAdmin);
		tuple.put("isAdmin", isAdmin);
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
