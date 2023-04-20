package acme.features.administrator.administrattor_dashboard;

import acme.entities.note.Note;
import acme.framework.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

    @Query("select count(u) from Lecturer l inner join UserAccount u on l member of u.userRoles")
	Integer countPrincipalByLecturer();

	@Query("select count(u) from Assistant a inner join UserAccount u on a member of u.userRoles")
	Integer countPrincipalByAssistant();

	@Query("select count(u) from Auditor a inner join UserAccount u on a member of u.userRoles")
	Integer countPrincipalByAuditor();

	@Query("select count(u) from Company c inner join UserAccount u on c member of u.userRoles")
	Integer countPrincipalByCompany();

	@Query("select count(u) from Consumer c inner join UserAccount u on c member of u.userRoles")
	Integer countPrincipalByConsumer();

	@Query("select count(u) from Provider p inner join UserAccount u on p member of u.userRoles")
	Integer countPrincipalByProvider();

	@Query("select count(u) from Student s inner join UserAccount u on s member of u.userRoles")
	Integer countPrincipalByStudent();

	@Query("select count(u) from Administrator a inner join UserAccount u on a member of u.userRoles")
	Integer countPrincipalByAdministrator();

	@Query("select count(l) from Peep p ")
	Double countAllPeeps();

	@Query("select count(l) from Peep p where p.email is not null and p.link is not null")
	Double countAllPeepsWithBoth();

	@Query("select count(l) from Bulletin b ")
	Double countAllBulletin();

	@Query("select count(l) from Bulletin b where b.flags = true")
	Double countAllCriticalBulletin();

	@Query("select count(o.price.amount) from Offer o where o.price.currency = :currency")
    int countPriceOfferByCurrency(String currency);

	@Query("select max(o.price.amount) from Offer o where o.price.currency = :currency")
	Double maxPriceOfferByCurrency(String currency);

	@Query("select min(o.price.amount) from Offer o where o.price.currency = :currency")
	Double minPriceOfferByCurrency(String currency);

	@Query("select avg(o.price.amount) from Offer o where o.price.currency = :currency")
	Double avgPriceOfferByCurrency(String currency);

	@Query("select stddev(o.price.amount) from Offer o where o.price.currency = :currency")
	Double stddevPriceOfferByCurrency(String currency);

	@Query("select n from Note n where n.moment >= :date ")
	Collection<Note> findNotesInLast10Weeks(Date date);

	@Query("select c.acceptedCurrencies from Configuration c")
	String findAcceptedCurrencies();
}
