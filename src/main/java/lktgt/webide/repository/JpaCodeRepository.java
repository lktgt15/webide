package lktgt.webide.repository;

import lktgt.webide.domain.Code;
import lktgt.webide.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class JpaCodeRepository implements CodeRepository{

    private final EntityManager em;

    @Autowired
    public JpaCodeRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Code save(Code code) {
        em.persist(code);
        return code;
    }

    @Override
    public Optional<Code> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Code>> findAll(String username) {
        List<Code> resultList = em.createQuery("select * from Code c where c.username = :username", Code.class)
                .setParameter("username",username)
                .getResultList();
        return null;
    }
}
