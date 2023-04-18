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

package acme.features.any.peep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peep.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepCreateService extends AbstractService<Any, Peep> {

	//Constants

	public final static String[]	PROPERTIES	= {
		"moment", "title", "nick", "message", "email", "link", "draftMode"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyPeepRepository		repository;

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
		final Peep object;
		Principal principal;
		int userAccountId;
		final UserAccount userAccount;
		String fullName = "";
		String email = "";

		principal = super.getRequest().getPrincipal();
		object = new Peep();
		object.setDraftMode(false);
		if (principal.hasRole(Authenticated.class)) {
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
		super.bind(object, AnyPeepCreateService.PROPERTIES);
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;

	}

	@Override
	public void perform(final Peep object) {
		assert object != null;

		this.repository.save(object);

	}

	@Override
	public void unbind(final Peep object) {
		Tuple tuple;
		tuple = super.unbind(object, AnyPeepCreateService.PROPERTIES);

		super.getResponse().setData(tuple);

	}

}
