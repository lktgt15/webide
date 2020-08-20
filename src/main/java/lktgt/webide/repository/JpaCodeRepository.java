package lktgt.webide.repository;

import lktgt.webide.domain.Code;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaCodeRepository implements CodeRepository{

    private final EntityManager em;

    @Autowired
    public JpaCodeRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Code save(Code code) {
        em.persist(code);
        return null;
    }

    @Override
    public Optional<Code> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Code> findAll() {
        return null;
    }
}
