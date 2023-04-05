package acme.features.authenticated.company;


import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class AuthenticatedCompanyController extends AbstractController<Authenticated, Company> {

    // Internal state ---------------------------------------------------------
    //@Autowired
    //protected AuthenticatedCompanyCreateService createService;
    @Autowired
    protected AuthenticatedCompanyUpdateService updateService;

    // Constructors -----------------------------------------------------------
    @PostConstruct
    protected void initialise() {
        //super.addBasicCommand("create", this.createService);
        super.addBasicCommand("update", this.updateService);
    }
}
