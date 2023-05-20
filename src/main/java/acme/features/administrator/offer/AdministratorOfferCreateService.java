
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferCreateService extends AbstractService<Administrator, Offer> {

	// Constants -------------------------------------------------------------
	protected static final String[]			PROPERTIES	= {
		"instantiation", "heading", "summary", "startDate", "endDate", "price", "link"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository	repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Offer object;

		object = new Offer();
		object.setInstantiation(MomentHelper.getCurrentMoment());

		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, AdministratorOfferCreateService.PROPERTIES);
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(MomentHelper.isFuture(object.getStartDate()), "startDate", "administrator.offer.error.code.start-future");
		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(MomentHelper.isFuture(object.getEndDate()), "endDate", "administrator.offer.error.code.end-future");
		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("availabilityEnd"))
			super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "startDate", "administrator.offer.error.code.start-before-end");
		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("availabilityEnd")) {
			super.state(MomentHelper.isLongEnough(object.getStartDate(), object.getEndDate(), 7, ChronoUnit.DAYS), "startDate", "administrator.offer.error.code.short-availability");
			super.state(MomentHelper.isLongEnough(object.getStartDate(), object.getEndDate(), 1, ChronoUnit.DAYS), "endDate", "administrator.offer.error.code.short-start");
		}
		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getPrice().getAmount() >= 0, "price", "administrator.offer.error.code.price-negative");
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		object.setInstantiation(MomentHelper.getCurrentMoment());

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		Tuple tuple;

		tuple = super.unbind(object, AdministratorOfferCreateService.PROPERTIES);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
