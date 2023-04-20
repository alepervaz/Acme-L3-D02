
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface TutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id= ?1")
	Tutorial findOneTutorialById(int id);

	@Query("select ua from UserAccount ua where ua.id =?1")
	UserAccount findOneUserAccountById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select t from Tutorial t where t.assistant.userAccount.id =?1 or t.draftMode = false")
	Collection<Tutorial> findTutorialsByUserAccountId(int userAccountId);
}
