
package acme.features.company.sessionPracticum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Controller
public class CompanySessionPracticumListService extends AbstractService<Company, SessionPracticum> {

	// Constants --------------------------------------------------------------
	protected static final String[]				PROPERTIES	= {
		"code", "title", "abstractSession", "description", "start", "end", "link", "additional", "confirmed"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	private CompanySessionPracticumRepository	repository;


	// AbstractService Interface ----------------------------------------------
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumId;
		Practicum practicum;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		status = practicum != null && (!practicum.isDraftMode() || principal.hasRole(practicum.getCompany()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<SessionPracticum> sessionPracticums;
		int practicumId;

		practicumId = super.getRequest().getData("masterId", int.class);
		sessionPracticums = this.repository.findManySessionPracticumsByPracticumId(practicumId);

		super.getBuffer().setData(sessionPracticums);
	}

	@Override
	public void unbind(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		Tuple tuple;

		tuple = super.unbind(sessionPracticum, CompanySessionPracticumListService.PROPERTIES);

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<SessionPracticum> sessionPracticums) {
		assert sessionPracticums != null;

		int practicumId;
		Practicum practicum;
		boolean showCreate;
		Principal principal;
		boolean extraAvailable;

		principal = super.getRequest().getPrincipal();
		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		showCreate = practicum.isDraftMode() && principal.hasRole(practicum.getCompany());
		extraAvailable = sessionPracticums.stream().noneMatch(SessionPracticum::isAdditional);

		super.getResponse().setGlobal("masterId", practicumId);
		super.getResponse().setGlobal("showCreate", showCreate);
		super.getResponse().setGlobal("extraAvailable", extraAvailable);
	}
}
