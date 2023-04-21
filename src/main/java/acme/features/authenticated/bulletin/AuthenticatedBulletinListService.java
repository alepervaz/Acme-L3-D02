
package acme.features.authenticated.bulletin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinListService extends AbstractService<Authenticated, Bulletin> {

	// Constants -------------------------------------------------------------
	public static final String[]				PROPERTIES	= {
		"moment", "title", "message", "flags", "link"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedBulletinRepository	repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		status = principal.hasRole(Authenticated.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Collection<Bulletin> objects;

		objects = this.repository.findManyBulletin();

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, AuthenticatedBulletinListService.PROPERTIES);

		super.getResponse().setData(tuple);
	}
}
