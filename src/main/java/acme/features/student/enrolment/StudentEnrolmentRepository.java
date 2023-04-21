
package acme.features.student.enrolment;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activities.Activity;
import acme.entities.courses.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.id=:id")
	Enrolment findEnrolmentById(int id);

	@Query("select e from Enrolment e where e.student.id=:studentId")
	List<Enrolment> findEnrolmentsByStudentId(int studentId);

	@Query("select sum(TIME_TO_SEC(TIMEDIFF(a.endDate, a.startDate)) / 3600) from Activity a where a.enrolment.id=:id")
	Double finWorkTimeByEnrolmentId(int id);

	@Query("select c from Course c where c.id=:courseId")
	Course findCourseById(int courseId);

	@Query("select s from Student s where s.id=:principalId")
	Student findStudentByPrincipalId(int principalId);

	@Query("select a from Activity a where a.enrolment.id=:id")
	List<Activity> findManyAcitivitiesByEnrolmentId(int id);

	@Query("select c from Course c")
	List<Course> findManyCourses();

	@Query("select c from Course c where c.id=:courseId")
	Course findOneCourseById(int courseId);

	@Query("select e from Enrolment e where e.code=:code")
	Enrolment findOneEnrolmentByCode(String code);
}
