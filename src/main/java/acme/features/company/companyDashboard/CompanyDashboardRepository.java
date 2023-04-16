
package acme.features.company.companyDashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("select c from Company c where c.userAccount.id = ?1")
	Company findOneCompanyByUserAccountId(int userAccountId);

	@Query("select avg(timediff(sp.end,sp.start)) from SessionPracticum sp where sp.practicum.company.id = ?1")
	double findAverageSessionLength(int companyId);

	@Query("select stddev(timediff(sp.end,sp.start)) from SessionPracticum  sp where sp.practicum.company.id = ?1")
	double findDeviationSessionLength(int companyId);

	@Query("select min(timediff(sp.end,sp.start)) from SessionPracticum sp where sp.practicum.company.id = ?1")
	double findMinimumSessionLength(int companyId);

	@Query("select max(timediff(sp.end,sp.start)) from SessionPracticum sp where sp.practicum.company.id = ?1")
	double findMaximumSessionLength(int companyId);

	@Query("select count(sp) from SessionPracticum sp where sp.practicum.company.id = ?1")
	int findCountSession(int companyId);

	// Calcula la media  de las media de las sesiones en las prácticas de una empresa
	@Query("select avg(sum(timediff(sp.end,sp.start))) from SessionPracticum sp where sp.practicum.company.id = ?1 group by sp.practicum.id")
	double findAveragePracticaLength(int companyId);

	@Query("select stddev(sum(timediff(sp.end,sp.start))) from SessionPracticum sp where sp.practicum.company.id = ?1 group by sp.practicum.id")
	double findDeviationPracticaLength(int companyId);

	@Query("select max(sum(timediff(sp.end,sp.start))) from SessionPracticum sp where sp.practicum.company.id = ?1 group by sp.practicum.id")
	double findMinimumPracticaLength(int companyId);

	@Query("select min(sum(timediff(sp.end,sp.start))) from SessionPracticum sp where sp.practicum.company.id = ?1 group by sp.practicum.id")
	double findMaximumPracticaLength(int companyId);

	@Query("select count(p) from Practicum p where p.company.id = ?1")
	int findCountPractica(int companyId);

	@Query("SELECT FUNCTION('MONTH', sp.start), COUNT(sp) FROM SessionPracticum sp where sp.practicum.company.id = ?1 group by function('MONTH', sp.start) order by count(sp) desc")
	List<Object[]> findTotalNumberOfPracticaByMonth(int companyId);
}
