/*
 * AuthenticatedConsumerCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.repositories.BannerRepository;

@Service
public class AuthenticatedShowBannerService extends AbstractService<Authenticated, Banner> {

	// Internal state ---------------------------------------------------------

	public final static String[]	PROPERTIES	= {
		"instant", "displayStart", "displayEnd", "picture", "slogan", "link"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected BannerRepository		repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Banner object;
		int bannerId;

		bannerId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(bannerId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, AuthenticatedShowBannerService.PROPERTIES);
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

	}

	@Override
	public void unbind(final Banner object) {
		Tuple tuple;

		tuple = super.unbind(object, AuthenticatedShowBannerService.PROPERTIES);
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
