
package acme.features.company.sessionPracticum;

import java.util.Calendar;
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
public class CompanySessionPracticumUpdateService extends AbstractService<Company, SessionPracticum> {

	// Constants --------------------------------------------------------------
	public static final String[]				PROPERTIES_BIND		= {
		"code", "title", "abstractSession", "description", "start", "end", "link"
	};

	public static final String[]				PROPERTIES_UNBIND	= {
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
		Principal principal;
		System.out.println("authorise");
		principal = super.getRequest().getPrincipal();
		sessionPracticumId = super.getRequest().getData("id", int.class);
		sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);
		practicum = this.repository.findOnePracticumBySessionPracticumId(sessionPracticumId);
		status = practicum != null && (practicum.getDraftMode() || sessionPracticum.getAdditional()) && principal.hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum sessionPracticum;
		int sessionPracticumId;

		System.out.println("load");
		sessionPracticumId = super.getRequest().getData("id", int.class);
		sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);

		super.getBuffer().setData(sessionPracticum);
	}

	@Override
	public void bind(final SessionPracticum sessionPracticum) {
		assert sessionPracticum != null;
		System.out.println("bind");
		super.bind(sessionPracticum, CompanySessionPracticumUpdateService.PROPERTIES_BIND);
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
			final Date start;
			final Date end;
			final Date now;

			start = sessionPracticum.getStart();
			end = sessionPracticum.getEnd();
			now = MomentHelper.getCurrentMoment();

			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.WEEK_OF_YEAR, CompanySessionPracticumCreateService.ONE_WEEK);
			final Date inAWeekFromNow = calendar.getTime();

			calendar.setTime(start);
			calendar.add(Calendar.WEEK_OF_YEAR, CompanySessionPracticumCreateService.ONE_WEEK);
			final Date inAWeekFromStart = calendar.getTime();

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

		int sessionPracticumId;
		SessionPracticum old;
		Tuple tuple;
		System.out.println("unbind");
		sessionPracticumId = super.getRequest().getData("id", int.class);
		old = this.repository.findOneSessionPracticumById(sessionPracticumId);
		tuple = super.unbind(sessionPracticum, CompanySessionPracticumUpdateService.PROPERTIES_UNBIND);
		tuple.put("masterId", sessionPracticum.getPracticum().getId());
		tuple.put("draftMode", sessionPracticum.getPracticum().getDraftMode());

		super.getResponse().setData(tuple);
	}
}
