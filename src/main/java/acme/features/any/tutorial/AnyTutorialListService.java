
package acme.features.any.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyTutorialListService extends AbstractService<Authenticated, Tutorial> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AnyTutorialRepository repository;


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
		Collection<Tutorial> tutorial;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		tutorial = this.repository.findTutorialsByUserAccountId(userAccountId);

		super.getBuffer().setData(tutorial);
	}

	@Override
	public void unbind(final Tutorial tutorial) {
		assert tutorial != null;

		Tuple tuple;

		tuple = super.unbind(tutorial, "code", "title", "summary", "goals", "estimatedTime", "draftMode");

		super.getResponse().setData(tuple);
	}

}
