
package acme.features.authenticated.assistant.assistantDashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	@Query("select a from Assistant a where a.userAccount.id = ?1")
	Assistant findOneAssistantByUserAccountId(int userAccountId);

	@Query("select avg(datediff(s.end,s.start)) from Session s where s.tutorial.assistant.id = ?1")
	double findAverageSessionLength(int assistantId);

	@Query("select stddev(datediff(s.end,s.start)) from Session  s where s.tutorial.assistant.id = ?1")
	double findDeviationSessionLength(int assistantId);

	@Query("select min(datediff(s.end,s.start)) from Session s where s.tutorial.assistant.id = ?1")
	double findMinimumSessionLength(int assistantId);

	@Query("select max(datediff(s.end,s.start)) from Session s where s.tutorial.assistant.id = ?1")
	double findMaximumSessionLength(int assistantId);

	@Query("select count(s) from Session s where s.tutorial.assistant.id = ?1")
	int findCountSession(int assistantId);

	@Query("select avg((select sum(datediff(s.end,s.start)) from Session s where s.tutorial.assistant.id = ?1 and s.tutorial.id = t.id)) from Tutorial t where t.assistant.id =?1")
	double findAverageTutorialLength(int assistantId);

	@Query("select stddev((select sum(datediff(s.end,s.start)) from Session s where s.tutorial.assistant.id = ?1 and s.tutorial.id = t.id)) from Tutorial t where t.assistant.id =?1")
	double findDeviationTutorialLength(int assistantId);

	@Query("select min((select sum(datediff(s.end,s.start)) from Session s where s.tutorial.assistant.id = ?1 and s.tutorial.id = t.id)) from Tutorial t where t.assistant.id =?1")
	double findMinimumTutorialLength(int assistantId);

	@Query("select max((select sum(datediff(s.end,s.start)) from Session s where s.tutorial.assistant.id = ?1 and s.tutorial.id = t.id)) from Tutorial t where t.assistant.id =?1")
	double findMaximumTutorialLength(int assistantId);

	@Query("select count(t) from Tutorial t where t.assistant.id = ?1")
	int findCountTutorial(int assistantId);

	@Query("SELECT FUNCTION('MONTH', s.start), COUNT(s) FROM Session s where s.tutorial.assistant.id = ?1 group by function('MONTH', s.start) order by count(s) desc")
	List<Object[]> findTotalNumberOfTutorialByMonth(int assistantId);

}
