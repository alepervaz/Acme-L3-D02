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

package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditingRecord;
import acme.entities.audit.Mark;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditingRecordCreateService extends AbstractService<Authenticated, AuditingRecord> {

	//Constants

	public final static String[]		PROPERTIES	= {
		"subject", "assessment", "startAudit", "mark", "endAudit", "link", "special"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditingRecordRepository	repository;


	@Override
	public void authorise() {
		boolean status;
		int userAccountId;
		int auditId;
		Audit audit;
		Boolean isMine;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		status = super.getRequest().getPrincipal().hasRole(Auditor.class);
		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findOneAuditByAuditId(auditId);
		isMine = userAccountId == audit.getAuditor().getUserAccount().getId();

		super.getResponse().setAuthorised(status && isMine);
	}

	@Override
	public void check() {
		Boolean status;
		status = super.getRequest().hasData("auditId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int auditId;
		Audit audit;

		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findOneAuditByAuditId(auditId);

		object = new AuditingRecord();
		if (!audit.getDraftMode())
			object.setSpecial(!audit.getDraftMode());
		object.setAudit(audit);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		super.bind(object, AuditingRecordCreateService.PROPERTIES);
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;
		if (!object.getAudit().getDraftMode())
			if (!object.getSpecial())
				super.state(false, "special", "audit.error.edit-draftMode");

	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		Tuple tuple;
		SelectChoices choice;
		int auditId;

		auditId = object.getAudit().getId();
		choice = SelectChoices.from(Mark.class, object.getMark());
		tuple = super.unbind(object, AuditingRecordCreateService.PROPERTIES);
		tuple.put("myAudit", true);

		tuple.put("choice", choice);
		tuple.put("auditDraftMode", object.getAudit().getDraftMode());
		//tuple.put("draftMode", object.getAudit().getDraftMode());
		super.getResponse().setGlobal("auditId", auditId);
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
