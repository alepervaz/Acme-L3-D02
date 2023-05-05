/*
 * AuthenticatedConsumerUpdateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.BinderHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuthenticatedAuditPublishService extends AbstractService<Authenticated, Audit> {

	//Constants

	public final static String[]			PROPERTIES	= {
		"course.code", "code", "conclusion", "strongPoints", "weakPoints", "auditor.firm", "draftMode"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAuditRepository	repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		Boolean status;
		final Boolean isMine;
		int auditUserId;
		final Audit audit;
		final int accountId = super.getRequest().getPrincipal().getAccountId();
		final int auditId = super.getRequest().getData("id", int.class);
		status = super.getRequest().getPrincipal().hasRole(Auditor.class);
		audit = this.repository.findOneAuditById(auditId);
		auditUserId = audit.getAuditor().getUserAccount().getId();
		isMine = auditUserId == accountId;
		super.getResponse().setAuthorised(status && isMine);
	}

	@Override
	public void check() {
		Boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		Audit object;
		int auditId;

		auditId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(auditId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, AuthenticatedAuditPublishService.PROPERTIES);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;
		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		Principal principal;
		Integer userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();

		Tuple tuple;
		final Integer idAuditor = object.getAuditor().getUserAccount().getId();
		tuple = BinderHelper.unbind(object, AuthenticatedAuditPublishService.PROPERTIES);
		tuple.put("myAudit", userAccountId == idAuditor);
		tuple.put("draftMode", object.isDraftMode());
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
