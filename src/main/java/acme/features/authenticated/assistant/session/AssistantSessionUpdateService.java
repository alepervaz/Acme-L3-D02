
package acme.features.authenticated.assistant.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enums.Approach;
import acme.entities.session.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionUpdateService extends AbstractService<Assistant, Session> {

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
		final int sessionId;
		final Session session;
		final Assistant assistant;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findOneSessionById(sessionId);
		assistant = session == null ? null : session.getTutorial().getAssistant();
		status = session != null && session.getDraftMode() && principal.hasRole(assistant);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Session session;
		int sessionId;

		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findOneSessionById(sessionId);

		super.getBuffer().setData(session);
	}

	@Override
	public void bind(final Session object) {
		assert object != null;

		super.bind(object, AssistantSessionUpdateService.PROPERTIES);

	}

	@Override
	public void validate(final Session session) {
		assert session != null;
	}

	@Override
	public void perform(final Session session) {
		assert session != null;

		this.repository.save(session);
	}

	@Override
	public void unbind(final Session session) {
		assert session != null;

		Tutorial tutorial;
		SelectChoices choices;
		Tuple tuple;

		tutorial = session.getTutorial();
		choices = SelectChoices.from(Approach.class, session.getType());

		tuple = super.unbind(session, AssistantSessionShowService.PROPERTIES);
		tuple.put("masterId", super.getRequest().getData("id", int.class));
		tuple.put("type", choices);
		tuple.put("tutorial", tutorial);
		tuple.put("tutorialDraftMode", tutorial.getDraftMode());

		super.getResponse().setData(tuple);
	}
}
