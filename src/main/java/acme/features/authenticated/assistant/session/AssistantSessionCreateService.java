
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
public class AssistantSessionCreateService extends AbstractService<Assistant, Session> {

	// Constants -------------------------------------------------------------
	public static final String[]			PROPERTIES	= {
		"title", "summary", "type", "start", "end", "link"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AssistantSessionRepository	repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Principal principal;
		Tutorial tutorial;
		int tutorialId;

		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		principal = super.getRequest().getPrincipal();
		status = principal.hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Session session;
		Tutorial tutorial;
		int tutorialId;

		tutorialId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		session = new Session();
		session.setTutorial(tutorial);

		super.getBuffer().setData(session);
	}

	@Override
	public void bind(final Session session) {
		assert session != null;
		super.bind(session, AssistantSessionCreateService.PROPERTIES);
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

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Approach.class, session.getType());

		tuple = super.unbind(session, AssistantSessionCreateService.PROPERTIES);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("type", choices);
		tuple.put("tutorialDraftMode", session.getTutorial().getDraftMode());
		super.getResponse().setData(tuple);
	}

}
