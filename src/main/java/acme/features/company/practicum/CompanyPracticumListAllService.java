
package acme.features.company.practicum;

import acme.entities.practicum.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CompanyPracticumListAllService extends AbstractService<Company, Practicum> {

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

		practicums = this.repository.findManyPracticums();

		super.getBuffer().setData(practicums);
	}

	@Override
	public void unbind(final Practicum practicum) {
		assert practicum != null;

		Tuple tuple;
		String payload;

		tuple = super.unbind(practicum, CompanyPracticumListAllService.PROPERTIES);
		payload = String.format("%s; %s; %s; %s", practicum.getCourse().getTitle(), practicum.getCourse().getCode(), practicum.getAbstractPracticum(), practicum.getGoals());
		tuple.put("payload", payload);

		super.getResponse().setData(tuple);
	}

}
