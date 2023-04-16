
package acme.features.authenticated.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class AuthenticatedCompanyUpdateService extends AbstractService<Authenticated, Company> {

	// Constants -------------------------------------------------------------
	protected static final String[]				PROPERTIES	= {
		"name", "vatNumber", "summary", "link"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuthenticatedCompanyRepository	repository;


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
		Company company;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		company = this.repository.findOneCompanyByUserAccountId(userAccountId);

		super.getBuffer().setData(company);
	}

	@Override
	public void bind(final Company company) {
		assert company != null;

		super.bind(company, AuthenticatedCompanyUpdateService.PROPERTIES);
	}

	@Override
	public void validate(final Company company) {
		assert company != null;
	}

	@Override
	public void perform(final Company company) {
		assert company != null;

		this.repository.save(company);
	}

	@Override
	public void unbind(final Company company) {
		assert company != null;

		Tuple tuple;

		tuple = super.unbind(company, AuthenticatedCompanyUpdateService.PROPERTIES);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
