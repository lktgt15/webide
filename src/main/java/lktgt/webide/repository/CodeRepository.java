package lktgt.webide.repository;

import lktgt.webide.domain.Code;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository {

    Code save(Code code);
    List<Code> findByName(String username);
}
