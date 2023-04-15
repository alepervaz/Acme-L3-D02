package acme.repositories;

import acme.entities.banner.Banner;
import acme.framework.repositories.AbstractRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends AbstractRepository {

    @Query("select count(b) from Banner b where b.displayStart < CURRENT_TIMESTAMP and b.displayEnd > CURRENT_TIMESTAMP")
	int countBanners();

	@Query("select b from Banner b where b.displayStart < CURRENT_TIMESTAMP and b.displayEnd > CURRENT_TIMESTAMP")
    List<Banner> findManyBanners(PageRequest pageRequest);
}
