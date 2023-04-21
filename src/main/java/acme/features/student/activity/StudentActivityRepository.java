
package acme.features.student.activity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activities.Activity;
import acme.entities.enrolment.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.id=:id")
	Enrolment findEnrolmentById(int id);

	@Query("select s from Student s where s.id=:principalId")
	Student findStudentByPrincipalId(int principalId);

	@Query("select a from Activity a where a.enrolment.id=:enrolmentId")
	List<Activity> findActivitiesByEnrolmentId(int enrolmentId);

	@Query("select a from Activity a where a.id=:id")
	Activity findActivityById(int id);
}
