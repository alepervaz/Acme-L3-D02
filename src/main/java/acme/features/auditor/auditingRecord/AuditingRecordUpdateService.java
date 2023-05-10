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

package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.AuditingRecord;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.BinderHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditingRecordUpdateService extends AbstractService<Authenticated, AuditingRecord> {

	//Constants

	public final static String[]		PROPERTIES	= {
		"subject", "assessment", "startAudit", "mark", "endAudit", "link", "special"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditingRecordRepository	repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		boolean status;
		int userAccountId;
		int auditingRecordId;
		AuditingRecord auditingRecord;
		Boolean isMine;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		status = super.getRequest().getPrincipal().hasRole(Auditor.class);
		auditingRecordId = super.getRequest().getData("id", int.class);
		auditingRecord = this.repository.findOneAuditingRecordById(auditingRecordId);
		isMine = userAccountId == auditingRecord.getAudit().getAuditor().getUserAccount().getId();

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
		AuditingRecord object;
		int auditId;

		auditId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditingRecordById(auditId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		super.bind(object, AuditingRecordUpdateService.PROPERTIES);
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;
		if (!object.getAudit().getDraftMode())
			super.state(false, "draftMode", "audit.error.edit-draftMode");
	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();

		Tuple tuple;
		final int idAuditor = object.getAudit().getAuditor().getUserAccount().getId();
		tuple = BinderHelper.unbind(object, AuditingRecordUpdateService.PROPERTIES);
		tuple.put("myAudit", userAccountId == idAuditor);
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
