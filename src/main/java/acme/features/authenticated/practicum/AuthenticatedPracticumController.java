package acme.features.authenticated.practicum;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class AuthenticatedPracticumController extends AbstractController<Authenticated, Practicum> {

    // Internal state ---------------------------------------------------------
    @Autowired
    protected AuthenticatedPracticumListService listService;

    @Autowired
    protected AuthenticatedPracticumShowService showService;

    // Constructors -----------------------------------------------------------
    @PostConstruct
    protected void initialise() {
        super.addBasicCommand("list", this.listService);
        super.addBasicCommand("show", this.showService);
    }
}
