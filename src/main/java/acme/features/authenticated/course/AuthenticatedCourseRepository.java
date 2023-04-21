
package acme.features.authenticated.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedCourseRepository extends AbstractRepository {

	@Query("select audit from Audit audit where audit.course.id = :id")
	Collection<Audit> findAuditsByCourse(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select course from Course course")
	Collection<Course> findCourses();
}
