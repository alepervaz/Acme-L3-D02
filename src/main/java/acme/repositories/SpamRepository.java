
package acme.repositories;

import acme.framework.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpamRepository extends AbstractRepository {


    @Query("select c.spamWords from Configuration c")
    String findSpamWords();

    @Query("select c.spamThreshold from Configuration c")
    Double findSpamThreshold();

}
