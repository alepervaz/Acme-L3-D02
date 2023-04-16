
package acme.features.authenticated.company;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticatedCompanyRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int userAccountId);

	// @Query("select c from Company c where c.userAccount.id = ?1")
	// Company findOneCompanyByUserAccountId(int userAccountId);
}
