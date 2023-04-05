package acme.features.company.sessionPracticum;

import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanySessionPracticumDeleteService extends AbstractService<Company, SessionPracticum> {
    // Constants --------------------------------------------------------------
    public static final String[] PROPERTIES = {"code", "title", "abstractSession", "description", "start", "end", "link", "additional", "confirmed"};

    // Internal state ---------------------------------------------------------
    @Autowired
    private CompanySessionPracticumRepository repository;

    // AbstractService Interface ----------------------------------------------
    @Override
    public void check() {
        boolean status;

        status = super.getRequest().hasData("id", int.class);

        super.getResponse().setChecked(status);
    }

    @Override
    public void authorise() {
        boolean status;
        int sessionPracticumId;
        SessionPracticum sessionPracticum;
        Principal principal;

        principal = super.getRequest().getPrincipal();
        sessionPracticumId = super.getRequest().getData("id", int.class);
        sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);
        status = sessionPracticum != null && principal.hasRole(sessionPracticum.getPracticum().getCompany());

        super.getResponse().setAuthorised(status);
    }

    @Override
    public void load() {
        SessionPracticum sessionPracticum;
        int sessionPracticumId;

        sessionPracticumId = super.getRequest().getData("id", int.class);
        sessionPracticum = this.repository.findOneSessionPracticumById(sessionPracticumId);

        super.getBuffer().setData(sessionPracticum);
    }

    @Override
    public void bind(final SessionPracticum sessionPracticum) {
        assert sessionPracticum != null;

        super.bind(sessionPracticum, PROPERTIES);
    }

    @Override
    public void validate(final SessionPracticum sessionPracticum) {
        assert sessionPracticum != null;
    }

    @Override
    public void perform(final SessionPracticum sessionPracticum) {
        assert sessionPracticum != null;

        this.repository.delete(sessionPracticum);
    }

    @Override
    public void unbind(final SessionPracticum sessionPracticum) {
        assert sessionPracticum != null;

        Tuple tuple;

        tuple = super.unbind(sessionPracticum, PROPERTIES);
        tuple.put("masterId", sessionPracticum.getPracticum().getId());
        tuple.put("draftMode", sessionPracticum.getPracticum().getDraftMode());
    }
}
