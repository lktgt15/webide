package lktgt.webide.repository;

import lktgt.webide.domain.CodeExecuted;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeExecutedRepository {

    CodeExecuted save(CodeExecuted codeExecuted);
    List<CodeExecuted> findByName(String name);
}
