
package acme.features.authenticated.bulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bulletin.Bulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedBulletinRepository extends AbstractRepository {

	@Query("select a from Bulletin a where a.id = :id")
	Bulletin findOneBulletinById(int id);

	@Query("select b from Bulletin b")
	Collection<Bulletin> findManyBulletin();
}
