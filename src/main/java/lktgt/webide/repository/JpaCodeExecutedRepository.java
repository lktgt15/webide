package lktgt.webide.repository;

import lktgt.webide.domain.CodeExecuted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@Repository
public class JpaCodeExecutedRepository implements CodeExecutedRepository {

    private final EntityManager em;

    @Autowired
    public JpaCodeExecutedRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public CodeExecuted save(CodeExecuted codeExecuted) {
        em.persist(codeExecuted);
        return codeExecuted;
    }

    @Override
    public List<CodeExecuted> findByName(String name) {
        return em.createQuery("select ce from CodeExecuted ce where ce.name = :name", CodeExecuted.class)
                .setParameter("name",name)
                .getResultList();
    }

}
