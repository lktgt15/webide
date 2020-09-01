package lktgt.webide.service;

import lktgt.webide.domain.CodeExecuted;
import lktgt.webide.repository.JpaCodeExecutedRepository;
import org.springframework.stereotype.Service;

@Service
public class CodeExecutedService {

    private final JpaCodeExecutedRepository jpaCodeExecutedRepository;

    public CodeExecutedService(JpaCodeExecutedRepository jpaCodeExecutedRepository) {
        this.jpaCodeExecutedRepository = jpaCodeExecutedRepository;
    }

    public String join(CodeExecuted codeExecuted){
        jpaCodeExecutedRepository.save(codeExecuted);
        return "코드 실행 완료.";
    }
}
