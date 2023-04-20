package acme.features.administrator.administrattor_dashboard;

import acme.datatypes.Statistic;
import acme.entities.note.Note;
import acme.forms.AdministratorDashboard;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AdministratorDashboardShowService extends AbstractService<Administrator, AdministratorDashboard> {


	// Constants -------------------------------------------------------------
	protected static final String[] PROPERTIES = {"principalsByRole", "ratioOfCriticalBulletins", "ratioOfNonCriticalBulletins", "currentsOffersStats", "notesInLast10WeeksStats"};
	public static final int ONE_WEEK = 1;
	public static final int LAST_TEN_WEEKS = -10;

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AdministratorDashboardRepository repository;

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
		AdministratorDashboard dashboard = new AdministratorDashboard();

		Map<String, Integer> principalsByRole;
		Double ratioOfCriticalBulletins;
		double ratioOfNonCriticalBulletins;
		Map<String, Statistic> currentOfferStatistic;
		double ratioOfPeeps;
		Map<Integer, Double> notesPerWeek;

		principalsByRole = getStatisticsPrincipalsByRole();
		ratioOfCriticalBulletins = getRatioOfBulletinsByCriticality();
		ratioOfNonCriticalBulletins = ONE_WEEK - ratioOfCriticalBulletins;
		currentOfferStatistic = getCurrencyStatisticsForOffer();
		ratioOfPeeps = getRatioOfPeeps();
		notesPerWeek = getNotesInLastTenWeeks();

		dashboard.setPrincipalsByRole(principalsByRole);
		dashboard.setRatioOfCriticalBulletins(ratioOfCriticalBulletins);
		dashboard.setRatioOfNonCriticalBulletins(ratioOfNonCriticalBulletins);
		dashboard.setCurrentsOffersStats(currentOfferStatistic);
		dashboard.setLinkAndEmailPeepsRatio(ratioOfPeeps);
		dashboard.setNotesInLast10WeeksStats(notesPerWeek);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, PROPERTIES);

		super.getResponse().setData(tuple);
	}

	// Ancillary methods ------------------------------------------------------
	private Map<Integer, Double> getNotesInLastTenWeeks() {
		Date tenWeeks = MomentHelper.deltaFromCurrentMoment(LAST_TEN_WEEKS, ChronoUnit.WEEKS);
		Collection<Note> notes = repository.findNotesInLast10Weeks(tenWeeks);
		Map<Integer, Double> notasPorSemana = new HashMap<>();
		notes.forEach(nota -> notasPorSemana.merge(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR), 1.0, Double::sum));
		return notasPorSemana;
	}

	private double getRatioOfPeeps() {
		return repository.countAllPeepsWithBoth() / repository.countAllPeeps();
	}

	private Map<String, Integer> getStatisticsPrincipalsByRole() {
		Map<String, Integer> res = new HashMap<>();
		res.put("Lecturer", repository.countPrincipalByLecturer());
		res.put("Assistant", repository.countPrincipalByAssistant());
		res.put("Provider", repository.countPrincipalByProvider());
		res.put("Company", repository.countPrincipalByCompany());
		res.put("Consumer", repository.countPrincipalByConsumer());
		res.put("Student", repository.countPrincipalByStudent());
		res.put("Auditor", repository.countPrincipalByAuditor());
		res.put("Administrator", repository.countPrincipalByAdministrator());
		return res;
	}

	private Double getRatioOfBulletinsByCriticality() {
		double countAllBulletin = repository.countAllBulletin();
		return repository.countAllCriticalBulletin() * 1.0 / countAllBulletin;
	}

	private Map<String, Statistic> getCurrencyStatisticsForOffer() {
		Map<String, Statistic> res = new HashMap<>();
		for (String currency : repository.findAcceptedCurrencies().split("-")) {
			currency = currency.trim();
			Statistic stats = new Statistic();
			stats.setCount(repository.countPriceOfferByCurrency(currency));
			stats.setMax(repository.maxPriceOfferByCurrency(currency));
			stats.setMin(repository.minPriceOfferByCurrency(currency));
			stats.setAverage(repository.avgPriceOfferByCurrency(currency));
			stats.setDeviation(repository.stddevPriceOfferByCurrency(currency));
			res.put(currency, stats);
		}
		return res;
	}
}
