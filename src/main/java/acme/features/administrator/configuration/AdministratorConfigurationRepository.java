package acme.features.administrator.configuration;

import acme.entities.configuration.Configuration;
import acme.framework.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorConfigurationRepository extends AbstractRepository {

    @Query("select c from Configuration c")
    Configuration findConfiguration();
}
