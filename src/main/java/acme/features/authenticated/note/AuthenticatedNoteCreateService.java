
package acme.features.authenticated.note;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.note.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

	// Constants -------------------------------------------------------------
	protected static final String[]			PROPERTIES	= {
		"moment", "title", "author", "message", "emailAddress", "link"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedNoteRepository	repository;

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
		final Note object;
		final Date moment;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		moment = MomentHelper.getCurrentMoment();

		object = new Note();
		object.setMoment(moment);
		object.setAuthor(userAccount.getUsername() + "-" + userAccount.getIdentity().getSurname() + ", " + userAccount.getIdentity().getName());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note note) {
		assert note != null;

		super.bind(note, AuthenticatedNoteCreateService.PROPERTIES);
	}

	@Override
	public void validate(final Note note) {
		assert note != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Note note) {
		assert note != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		note.setMoment(moment);
		this.repository.save(note);
	}

	@Override
	public void unbind(final Note note) {
		assert note != null;

		Tuple tuple;

		tuple = super.unbind(note, AuthenticatedNoteCreateService.PROPERTIES);
		tuple.put("confirmation", false);

		super.getResponse().setData(tuple);
	}
}
