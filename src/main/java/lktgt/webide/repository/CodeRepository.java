package lktgt.webide.repository;

import lktgt.webide.domain.Code;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository {

    Code save(Code code);
    Optional<Code> findCodeById(Long id);
    List<Code> findByName(String username);
}
