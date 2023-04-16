
package acme.features.company.sessionPracticum;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumConfirmService extends AbstractService<Company, SessionPracticum> {

	// Constants -------------------------------------------------------------
	protected static final String[]				PROPERTIES_BIND		= {
		"code", "title", "abstractSession", "description", "start", "end", "link"
	};

	protected static final String[]				PROPERTIES_UNBIND	= {
		"code", "title", "abstractSession", "description", "start", "end", "link", "additional", "confirmed"
	};
	public static final int						ONE_WEEK			= 1;

	// Internal state ---------------------------------------------------------
	@Autowired
	private CompanySessionPracticumRepository	repository;


	// AbstractService Interface ----------------------------------------------
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int sessionPracticumId;
		SessionPracticum sessionPracticum;
		Practicum practicum;

		sessionPracticumId = super.getRequest().getData("id", int.class);
		sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);
		practicum = this.repository.findOnePracticumBySessionPracticumId(sessionPracticumId);
		status = false;

		if (practicum != null && sessionPracticum != null) {
			Principal principal;
			boolean hasExtraAvailable;
			boolean isPublishedAndHasExtraAvailable;

			principal = super.getRequest().getPrincipal();
			hasExtraAvailable = this.repository.findManySessionPracticumsByExtraAvailableAndPracticumId(practicum.getId()).isEmpty();
			isPublishedAndHasExtraAvailable = !sessionPracticum.isConfirmed() && !practicum.isDraftMode() && hasExtraAvailable;

			status = isPublishedAndHasExtraAvailable && principal.hasRole(practicum.getCompany());
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum sessionPracticum;
		int sessionPracticumId;

		sessionPracticumId = super.getRequest().getData("id", int.class);
		sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);
		sessionPracticum.setConfirmed(true);

		super.getRequest().setData(sessionPracticum);
	}

	@Override
	public void bind(final SessionPracticum object) {
		assert object != null;

		super.bind(object, CompanySessionPracticumConfirmService.PROPERTIES_BIND);
	}

	@Override
	public void validate(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean isUnique;
			int sessionPracticumId;
			SessionPracticum old;

			sessionPracticumId = super.getRequest().getData("id", int.class);
			old = this.repository.findOneSessionPracticumById(sessionPracticumId);
			isUnique = this.repository.findManySessionPracticumsByCode(sessionPracticum.getCode()).isEmpty() || old.getCode().equals(sessionPracticum.getCode());

			super.state(isUnique, "code", "company.practicum.form.error.not-unique-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("start") || !super.getBuffer().getErrors().hasErrors("end")) {
			Date start;
			Date end;
			Date inAWeekFromNow;
			Date inAWeekFromStart;

			start = sessionPracticum.getStart();
			end = sessionPracticum.getEnd();
			inAWeekFromNow = MomentHelper.deltaFromCurrentMoment(CompanySessionPracticumConfirmService.ONE_WEEK, ChronoUnit.WEEKS);
			inAWeekFromStart = MomentHelper.deltaFromMoment(start, CompanySessionPracticumConfirmService.ONE_WEEK, ChronoUnit.WEEKS);

			if (!super.getBuffer().getErrors().hasErrors("start"))
				super.state(MomentHelper.isAfter(start, inAWeekFromNow), "start", "company.session-practicum.error.start-after-now");
			if (!super.getBuffer().getErrors().hasErrors("end"))
				super.state(MomentHelper.isAfter(end, inAWeekFromStart), "end", "company.session-practicum.error.end-after-start");
		}
	}

	@Override
	public void perform(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		sessionPracticum.setConfirmed(true);
		this.repository.save(sessionPracticum);
	}

	@Override
	public void unbind(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;

		Practicum practicum;
		Tuple tuple;

		practicum = sessionPracticum.getPracticum();
		tuple = super.unbind(sessionPracticum, CompanySessionPracticumConfirmService.PROPERTIES_UNBIND);
		tuple.put("masterId", practicum.getId());
		tuple.put("draftMode", practicum.isDraftMode());

		super.getResponse().setData(tuple);
	}
}
