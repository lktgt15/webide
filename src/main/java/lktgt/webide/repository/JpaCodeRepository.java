package lktgt.webide.repository;

import lktgt.webide.domain.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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
    public List<Code> findByName(String name) {
        return em.createQuery("select c from Code c where c.name = :name", Code.class)
                .setParameter("name",name)
                .getResultList();
    }
}
