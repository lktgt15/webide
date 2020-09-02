package lktgt.webide.service;

import lktgt.webide.domain.Code;
import lktgt.webide.repository.JpaCodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeService {

    private final JpaCodeRepository jpaCodeRepository;

    public CodeService(JpaCodeRepository jpaCodeRepository) {
        this.jpaCodeRepository = jpaCodeRepository;
    }

    public String join(Code code){
        jpaCodeRepository.save(code);
        return "코드 입력 완료.";
    }

    public List<Code> getCodeList(String name){
        List<Code> result = jpaCodeRepository.findByName(name);
        return result;
    }
}
