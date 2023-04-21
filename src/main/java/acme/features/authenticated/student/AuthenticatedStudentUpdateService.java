
package acme.features.authenticated.student;

import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentUpdateService extends AbstractService<Authenticated, Student> {

}
