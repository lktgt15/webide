package lktgt.webide.service;

import lktgt.webide.domain.CodeForm;
import lktgt.webide.domain.Posted;
import lktgt.webide.repository.JpaBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final JpaBoardRepository jpaBoardRepository;

    public BoardService(JpaBoardRepository jpaBoardRepository) {
        this.jpaBoardRepository = jpaBoardRepository;
    }

    public List<Posted> getList(){
        return jpaBoardRepository.findAll();
    }

    public String join(Posted posted){
        jpaBoardRepository.save(posted);
        return "글 작성 완료";
    }
}
