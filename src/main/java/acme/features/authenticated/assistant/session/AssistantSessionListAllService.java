
package acme.features.authenticated.assistant.session;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.session.Session;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionListAllService extends AbstractService<Assistant, Session> {

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
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Session> sessions;

		sessions = this.repository.findManySession();

		super.getBuffer().setData(sessions);
	}

	@Override
	public void unbind(final Session session) {
		assert session != null;

		Tuple tuple;

		tuple = super.unbind(session, AssistantSessionListAllService.PROPERTIES);

		super.getResponse().setData(tuple);
	}

}
