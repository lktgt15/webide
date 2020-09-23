package lktgt.webide.service;

import lktgt.webide.domain.Code;
import lktgt.webide.domain.CodeForm;
import lktgt.webide.repository.JpaCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    public Code findCodeById(Long id){
        Optional<Code> code = jpaCodeRepository.findCodeById(id);
        return code.get();
    }

    public List<Code> getCodeList(String name){
        List<Code> result = jpaCodeRepository.findByName(name);
        return result;
    }

    public Code getCstyleCode(CodeForm codeForm) throws FileNotFoundException {
        Code code = new Code();
        code.setLanguage("C++17");

        String filename = "classpath:static/code/RandomInputGen.cc";
        File file = ResourceUtils.getFile(filename);
        String path = file.getPath();

        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("#include <cstdio>\n" +
                "#include <random>\n" +
                "\n" +
                "using namespace std;\n" +
                "\n" +
                "int main(){\n" +
                "\trandom_device rd;\n" +
                "\tmt19937 gen(rd());");

        for(int i=0;i<codeForm.getKmax().size();i++) {
            System.out.println(codeForm.getKmin().get(i));
            codeBuilder.append(
                    randomTemplate(codeForm.getKmin().get(i), codeForm.getKmax().get(i),
                            codeForm.getRangemin().get(i), codeForm.getRangemax().get(i),i)
            );

        }

        codeBuilder.append("}");

        code.setCode(codeBuilder.toString());

        return code;
    }

    private String randomTemplate(Long kmin, Long kmax, Long rangemin, Long rangemax,int idx){
        return new String("" +
                "\tuniform_int_distribution<int> kgen"+idx+"("+kmin+","+kmax+");\n" +
                "\tuniform_int_distribution<int> rangen"+idx+"("+rangemin+","+rangemax+");\n" +
                "\tint k"+idx+" = kgen"+idx+"(gen);\n" +
                "\tprintf(\"%d\\n\",k"+idx+");\n" +
                "\tfor(int i=0;i<k"+idx+";i++) printf(\"%d \",rangen"+idx+"(gen));\n" +
                "\tprintf(\"\\n\");\n"
        );
    }
}
