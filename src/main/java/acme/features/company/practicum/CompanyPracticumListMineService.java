
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumListMineService extends AbstractService<Company, Practicum> {

	// Constants -------------------------------------------------------------
	protected static final String[]			PROPERTIES	= {
		"code", "title", "abstractPracticum", "goals", "estimatedTimeInHours"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected CompanyPracticumRepository	repository;


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
		Collection<Practicum> practicums;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		practicums = this.repository.findManyPracticumsByCompanyId(principal.getActiveRoleId());

		super.getBuffer().setData(practicums);
	}

	@Override
	public void unbind(final Practicum practicum) {
		assert practicum != null;

		Tuple tuple;

		tuple = super.unbind(practicum, CompanyPracticumListMineService.PROPERTIES);

		super.getResponse().setData(tuple);
	}
}
