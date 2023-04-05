
package acme.features.authenticated.assistant;

import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AuthenticatedAssistantUpdateService extends AbstractService<Authenticated, Assistant> {

}
