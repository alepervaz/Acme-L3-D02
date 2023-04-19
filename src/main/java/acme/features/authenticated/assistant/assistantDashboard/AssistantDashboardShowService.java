
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
		"totalNumberOfTutorialByMonth", "sessionLength", "tutorialLength"
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
		deviationSessionLength = this.repository.findDeviationSessionLength(assistantId);
		minimumSessionLength = this.repository.findMinimumSessionLength(assistantId);
		maximumSessionLength = this.repository.findMaximumSessionLength(assistantId);
		countSession = this.repository.findCountSession(assistantId);
		sessionLength = new Statistic(countSession, averageSessionLength, maximumSessionLength, minimumSessionLength, deviationSessionLength);

		averageTutorialLength = this.repository.findAverageTutorialLength(assistantId);
		deviationTutorialLength = this.repository.findDeviationTutorialLength(assistantId);
		minimumTutorialLength = this.repository.findMinimumTutorialLength(assistantId);
		maximumTutorialLength = this.repository.findMaximumTutorialLength(assistantId);
		countTutorial = this.repository.findCountTutorial(assistantId);
		tutorialLength = new Statistic(countTutorial, averageTutorialLength, maximumTutorialLength, minimumTutorialLength, deviationTutorialLength);

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
