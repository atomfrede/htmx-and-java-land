package io.github.atomfrede.htmx_and_javaland.paging;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface LocationRepository extends PagingAndSortingRepository<Location, Long> {

    void save(Location location);
}
