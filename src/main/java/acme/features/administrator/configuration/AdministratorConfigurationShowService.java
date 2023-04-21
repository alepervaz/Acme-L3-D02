
package acme.features.administrator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorConfigurationShowService extends AbstractService<Administrator, Configuration> {

	// Constants -------------------------------------------------------------
	protected static final String[]					PROPERTIES	= {
		"defaultCurrency", "acceptedCurrencies", "spamWords", "spamThreshold"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AdministratorConfigurationRepository	repository;


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
		status = principal.hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Configuration configuration;

		configuration = this.repository.findConfiguration();

		super.getBuffer().setData(configuration);
	}

	@Override
	public void unbind(final Configuration configuration) {
		assert configuration != null;

		Tuple tuple;

		tuple = super.unbind(configuration, AdministratorConfigurationShowService.PROPERTIES);

		super.getResponse().setData(tuple);
	}
}
