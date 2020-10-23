package lktgt.webide.repository;

import lktgt.webide.domain.Posted;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository {

    Posted save(Posted posted);
    List<Posted> findAll();
    Optional<Posted> findByName(String name);
}
