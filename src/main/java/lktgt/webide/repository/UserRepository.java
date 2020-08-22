package lktgt.webide.repository;

import lktgt.webide.domain.Member;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
