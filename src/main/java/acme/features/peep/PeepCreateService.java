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

package acme.features.peep;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peep.Peep;
import acme.framework.components.accounts.Anonymous;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class PeepCreateService extends AbstractService<Authenticated, Peep> {

	//Constants

	public final static String[]	PROPERTIES	= {
		"moment", "title", "nick", "message", "email", "link", "draftMode"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PeepRepository		repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


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
		Peep object;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;
		String fullName = "";
		String email = "";

		principal = super.getRequest().getPrincipal();
		object = new Peep();
		if (principal.hasRole(Anonymous.class)) {

		} else {
			userAccountId = principal.getAccountId();
			userAccount = this.repository.findOneUserAccountById(userAccountId);
			fullName = userAccount.getIdentity().getFullName();
			email = userAccount.getIdentity().getEmail();
		}
		object.setNick(fullName);
		object.setEmail(email);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Peep object) {
		assert object != null;

		super.bind(object, PeepCreateService.PROPERTIES);
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;
		object.setDraftMode(false);
		final Instant now = Instant.ofEpochMilli(System.currentTimeMillis());
		object.setMoment(Date.from(now));
	}

	@Override
	public void perform(final Peep object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		Tuple tuple;

		tuple = super.unbind(object, PeepCreateService.PROPERTIES);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
