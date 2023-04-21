
package acme.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("select count(b) from Banner b where b.displayStart < CURRENT_TIMESTAMP and b.displayEnd > CURRENT_TIMESTAMP")
	int countBanners();

	@Query("select b from Banner b where b.displayStart < CURRENT_TIMESTAMP and b.displayEnd > CURRENT_TIMESTAMP")
	List<Banner> findManyBanners(PageRequest pageRequest);

	@Query("select b from Banner b")
	List<Banner> findAllBanner();

	@Query("select b from Banner b where b.id = :id")
	Banner findOneBannerById(int id);
}
