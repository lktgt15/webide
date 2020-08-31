package lktgt.webide.service;

import lktgt.webide.domain.Code;
import lktgt.webide.repository.JpaCodeRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class IDEService {

    private final JpaCodeRepository jpaCodeRepository;

    public IDEService(JpaCodeRepository jpaCodeRepository) {
        this.jpaCodeRepository = jpaCodeRepository;
    }

    public String join(Code code){
        jpaCodeRepository.save(code);
        return "코드 입력 완료";
    }

    private void compile(Code code){


    }

    @Async
    public String exec(Code code){
        compile(code);


        return null;
    }

}
