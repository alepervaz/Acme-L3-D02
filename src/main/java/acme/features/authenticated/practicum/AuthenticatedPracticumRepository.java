package acme.features.authenticated.practicum;

import acme.entities.courses.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {
    @Query("select p from Practicum p where p.company.userAccount.id = ?1 or p.draftMode = false")
    Collection<Practicum> findManyByUserAccountId(int userAccountId); // TODO: Deben mostrarse todos los que estén en draftMode y el creador.

    @Query("select p from Practicum p where p.id = ?1")
    Practicum findOnePracticumById(int id);

    @Query("select ua from UserAccount ua where ua.id = ?1")
    UserAccount findOneUserAccountById(int userAccountId);

    @Query("select c from Course c")
    Collection<Course> findAllCourses();
}
