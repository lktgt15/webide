package lktgt.webide.repository;

import lktgt.webide.domain.Code;
import lktgt.webide.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
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
        Code code = em.find(Code.class, id);
        return Optional.ofNullable(code);
    }

    @Override
    public List<Code> findByName(String name) {
        return em.createQuery("select c from Code c where c.name = :name", Code.class)
                .setParameter("name",name)
                .getResultList();
    }
}
