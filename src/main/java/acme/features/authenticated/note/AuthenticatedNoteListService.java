
package acme.features.authenticated.note;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.note.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteListService extends AbstractService<Authenticated, Note> {

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
		final Collection<Note> object;
		final Date deadline;

		deadline = MomentHelper.deltaFromCurrentMoment(-30, ChronoUnit.DAYS);
		object = this.repository.findLastMonthNotes(deadline);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Note note) {
		assert note != null;

		Tuple tuple;

		tuple = super.unbind(note, AuthenticatedNoteListService.PROPERTIES);

		super.getResponse().setData(tuple);
	}
}
