
package acme.features.company.session_practicum;

import acme.entities.practicum.Practicum;
import acme.entities.session_practicum.SessionPracticum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class CompanySessionPracticumCreateService extends AbstractService<Company, SessionPracticum> {

	// Constants -------------------------------------------------------------
	protected static final String[]				PROPERTIES_BIND		= {
		"title", "abstractSession", "description", "start", "end", "link"
	};

	protected static final String[]				PROPERTIES_UNBIND	= {
		"title", "abstractSession", "description", "start", "end", "link", "additional", "confirmed"
	};
	public static final int						ONE_WEEK			= 1;

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
		boolean hasExtraAvailable;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		status = false;

		if (practicum != null) {
			hasExtraAvailable = this.repository.findManySessionPracticumsByExtraAvailableAndPracticumId(practicum.getId()).isEmpty();

			status = (practicum.isDraftMode() || hasExtraAvailable) && principal.hasRole(practicum.getCompany());
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum sessionPracticum;
		int practicumId;
		Practicum practicum;
		boolean draftMode;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		draftMode = practicum.isDraftMode();

		sessionPracticum = new SessionPracticum();
		sessionPracticum.setPracticum(practicum);
		sessionPracticum.setAdditional(!draftMode);
		sessionPracticum.setConfirmed(draftMode);

		super.getBuffer().setData(sessionPracticum);
	}

	@Override
	public void bind(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		super.bind(sessionPracticum, CompanySessionPracticumCreateService.PROPERTIES_BIND);
	}

	@Override
	public void validate(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		if (!super.getBuffer().getErrors().hasErrors("start") || !super.getBuffer().getErrors().hasErrors("end")) {
			Date start;
			Date end;
			Date inAWeekFromNow;
			Date inAWeekFromStart;

			start = sessionPracticum.getStart();
			end = sessionPracticum.getEnd();
			inAWeekFromNow = MomentHelper.deltaFromCurrentMoment(CompanySessionPracticumCreateService.ONE_WEEK, ChronoUnit.WEEKS);
			inAWeekFromStart = MomentHelper.deltaFromMoment(start, CompanySessionPracticumCreateService.ONE_WEEK, ChronoUnit.WEEKS);

			if (!super.getBuffer().getErrors().hasErrors("start"))
				super.state(MomentHelper.isAfter(start, inAWeekFromNow), "start", "company.session-practicum.error.start-after-now");
			if (!super.getBuffer().getErrors().hasErrors("end"))
				super.state(MomentHelper.isAfter(end, inAWeekFromStart), "end", "company.session-practicum.error.end-after-start");
		}
	}

	@Override
	public void perform(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		this.repository.save(sessionPracticum);
	}

	@Override
	public void unbind(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		Practicum practicum;
		Tuple tuple;

		practicum = sessionPracticum.getPracticum();
		tuple = super.unbind(sessionPracticum, CompanySessionPracticumCreateService.PROPERTIES_UNBIND);
		tuple.put("masterId", practicum.getId());
		tuple.put("draftMode", practicum.isDraftMode());

		super.getResponse().setData(tuple);
	}
}
