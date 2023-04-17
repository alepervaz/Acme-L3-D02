
package acme.features.authenticated.assistant.assistantDashboard;

import java.time.Month;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.Statistic;
import acme.forms.AssistantDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	// Constants --------------------------------------------------------------
	protected static final String[]			PROPERTIES	= {
		"totalNumberOfTutorialByMonth", "sessionLength", "TutorialLength"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	private AssistantDashboardRepository	repository;


	// AbstractService Interface ----------------------------------------------
	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		final Assistant assistant;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		assistant = this.repository.findOneAssistantByUserAccountId(userAccountId);

		status = assistant != null && principal.hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int assistantId;
		final AssistantDashboard assistantDashboard;
		final Principal principal;
		int userAccountId;
		final Assistant assistant;

		Statistic sessionLength;
		double averageSessionLength;
		double deviationSessionLength;
		final double minimumSessionLength;
		final double maximumSessionLength;
		int countSession;

		final Statistic tutorialLength;
		final double averageTutorialLength;
		final double deviationTutorialLength;
		final double minimumTutorialLength;
		final double maximumTutorialLength;
		final int countTutorial;

		final Map<String, Long> totalNumberOfTutorialByMonth;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		assistant = this.repository.findOneAssistantByUserAccountId(userAccountId);
		assistantId = assistant.getId();

		averageSessionLength = this.repository.findAverageSessionLength(assistantId);
		System.out.println("1");
		deviationSessionLength = this.repository.findDeviationSessionLength(assistantId);
		System.out.println("2");
		//minimumSessionLength = this.repository.findMinimumSessionLength(assistantId);
		System.out.println("3");
		//maximumSessionLength = this.repository.findMaximumSessionLength(assistantId);
		System.out.println("4");
		countSession = this.repository.findCountSession(assistantId);
		System.out.println("5");
		sessionLength = new Statistic(countSession, averageSessionLength, deviationSessionLength, /* , minimumSessionLength */0.0, /* maximumSessionLength */0.0);

		//averageTutorialLength = this.repository.findAverageTutorialLength(assistantId);
		System.out.println("6");
		//deviationTutorialLength = this.repository.findDeviationTutorialLength(assistantId);
		System.out.println("7");
		//minimumTutorialLength = this.repository.findMinimumTutorialLength(assistantId);
		System.out.println("8");
		//maximumTutorialLength = this.repository.findMaximumTutorialLength(assistantId);
		System.out.println("9");
		countTutorial = this.repository.findCountTutorial(assistantId);
		System.out.println("10");
		tutorialLength = new Statistic(countTutorial, /* averageTutorialLength */0.0, 0.0 /* deviationTutorialLength */, /* minimumTutorialLength */0.0, 0.0/* maximumTutorialLength */);

		totalNumberOfTutorialByMonth = this.repository.findTotalNumberOfTutorialByMonth(assistantId).stream().collect(Collectors.toMap(key -> Month.of((int) key[0]).toString(), value -> (long) value[1]));

		assistantDashboard = new AssistantDashboard();

		assistantDashboard.setTotalNumberOfTutorialByMonth(totalNumberOfTutorialByMonth);
		assistantDashboard.setSessionLength(sessionLength);
		assistantDashboard.setTutorialLength(tutorialLength);

		super.getBuffer().setData(assistantDashboard);
	}

	@Override
	public void unbind(final AssistantDashboard assistantDashboard) {
		Tuple tuple;

		tuple = super.unbind(assistantDashboard, AssistantDashboardShowService.PROPERTIES);

		super.getResponse().setData(tuple);
	}
}
