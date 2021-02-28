package lktgt.webide.repository;

import lktgt.webide.domain.Posted;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class JpaBoardRepository implements BoardRepository{

    private final EntityManager em;

    public JpaBoardRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Posted save(Posted posted) {
        em.persist(posted);
        return posted;
    }

    @Override
    public List<Posted> findAll() {
        return em.createQuery("select p from Posted p",Posted.class)
                .getResultList();
    }

    @Override
    public Optional<Posted> findByName(String name) {
        Posted posted = em.find(Posted.class, name);
        return Optional.ofNullable(posted);
    }
}
