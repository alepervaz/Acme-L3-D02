
package acme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface CurrencyRepository extends AbstractRepository {

	@Query("select c.defaultCurrency from Configuration c")
	String findCurrentCurrency();
}
