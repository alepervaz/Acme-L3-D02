
package acme.features.assistant.session;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enums.Approach;
import acme.entities.session.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionPublishService extends AbstractService<Assistant, Session> {

	// Constants -------------------------------------------------------------
	public static final String[]			PROPERTIES	= {
		"title", "summary", "type", "start", "end", "link", "draftMode"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository	repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int sessionId;
		final Session session;
		final Assistant assistant;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findOneSessionById(sessionId);
		assistant = session == null ? null : session.getTutorial().getAssistant();
		status = session != null && session.isDraftMode() && principal.hasRole(assistant);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Session session;
		int sessionId;

		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findOneSessionById(sessionId);

		super.getBuffer().setData(session);
	}

	@Override
	public void bind(final Session session) {
		assert session != null;

		int tutorialId;
		Tutorial tutorial;

		tutorialId = session.getTutorial().getId();
		tutorial = this.repository.findOneTutorialById(tutorialId);

		super.bind(session, AssistantSessionPublishService.PROPERTIES);
		session.setTutorial(tutorial);
	}

	@Override
	public void validate(final Session session) {
		assert session != null;
		Date OneDayAhead;
		boolean condition1;
		boolean condition2;
		if (!super.getBuffer().getErrors().hasErrors("start")) {
			OneDayAhead = MomentHelper.deltaFromCurrentMoment(1, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(session.getStart(), OneDayAhead), "start", "assistant.session.start-error");
		}
		if (!super.getBuffer().getErrors().hasErrors("end"))
			super.state(MomentHelper.isAfter(session.getEnd(), session.getStart()), "end", "assistant.session.finish-error");
		if (!super.getBuffer().getErrors().hasErrors("end")) {
			condition1 = MomentHelper.isLongEnough(session.getStart(), session.getEnd(), 1, ChronoUnit.HOURS);
			condition2 = MomentHelper.computeDuration(session.getStart(), session.getEnd()).getSeconds() <= Duration.ofHours(5).getSeconds();
			super.state(condition1 && condition2, "end", "assistant.session.error-end-time");
		}
	}

	@Override
	public void perform(final Session session) {
		assert session != null;

		session.setDraftMode(false);

		this.repository.save(session);
	}

	@Override
	public void unbind(final Session session) {
		assert session != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Approach.class, session.getType());

		tuple = super.unbind(session, AssistantSessionPublishService.PROPERTIES);
		tuple.put("type", choices);

		super.getResponse().setData(tuple);
	}

}
