package lktgt.webide.service;

import lktgt.webide.domain.Code;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class IDEService {

    private final CodeService codeService;

    public IDEService(CodeService codeService) {
        this.codeService = codeService;
    }

    /*
    Compile and Exec
     */
    @Async("threadPoolTaskExecutor")
    public String exec(Code code,String filename, boolean isRandomGenerator, boolean userRandomGenerator) throws IOException {

        filename = "classpath:static/code/"+filename;
        File file = ResourceUtils.getFile(filename);
        String path = file.getPath();

        String beforeCode = code.getCode();
        System.out.println(code.getCode());

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(beforeCode);
        bufferedWriter.close();

        Runtime runtime = Runtime.getRuntime();
        Process process = null;

        StringBuffer successoutput = new StringBuffer();
        StringBuffer erroroutput = new StringBuffer();
        BufferedReader successreader = null;
        BufferedReader errorreader = null;
        String msg = "";

        boolean success = false;

        String[] array = null;

        array = cmdStringList("g++ "+path+" -o Main -O2 -Wall -lm -static -std=gnu++17");


        /*
        ====================↓ Compile =======================
         */

        try {
            process = runtime.exec(array);

            successreader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
            while ((msg = successreader.readLine()) != null) {
                successoutput.append(msg + System.getProperty("line.separator"));
            }


            errorreader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
            while ((msg = errorreader.readLine()) != null) {
                erroroutput.append(msg + System.getProperty("line.separator"));
            }

            process.waitFor();

            if (process.exitValue() == 0) {
                System.out.println("컴파일 성공");
                System.out.println(successoutput.toString());
                success = true;
            } else {
                System.out.println("cmd 비정상 종료");
                System.out.println(successoutput.toString());
            }

            if (!(erroroutput.toString().isEmpty())) {
                System.out.println("Error");
                System.out.println(erroroutput.toString());
                int idx = erroroutput.toString().indexOf("error");
                code.setResult(erroroutput.toString().substring(idx));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                process.destroy();
                if(successreader != null) successreader.close();
                if(errorreader != null) errorreader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(success == false) {
            if(isRandomGenerator == false) codeService.join(code);
            return "Error";
        }

        /*
        ====================↑ Compile ===================

        ====================↓ Exec ======================
         */

        array = cmdStringList("Main.exe");

        try {
            process = runtime.exec(array);

            successreader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
            while ((msg = successreader.readLine()) != null) {
                successoutput.append(msg + System.getProperty("line.separator"));
            }


            errorreader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
            while ((msg = errorreader.readLine()) != null) {
                erroroutput.append(msg + System.getProperty("line.separator"));
            }

            process.waitFor();

            if (process.exitValue() == 0) {
                System.out.println("실행 성공");
                System.out.println(successoutput.toString());
                code.setResult(successoutput.toString());

            } else {
                System.out.println("cmd 비정상 종료");
                System.out.println(successoutput.toString());
                code.setResult(successoutput.toString());
            }

            if (!(erroroutput.toString().isEmpty())) {
                System.out.println("Error");
                System.out.println(erroroutput.toString());
                code.setResult(successoutput.toString());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                process.destroy();


                if(successreader != null) successreader.close();
                if(errorreader != null) errorreader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*
        ==================↑ Exec =================
         */

        if(isRandomGenerator == false) codeService.join(code);
        return code.getResult();
    }

    private String[] cmdStringList(String cmd){
        List<String> cmdList = new ArrayList<String>();
        cmdList.add("cmd");
        cmdList.add("/c");
        cmdList.add(cmd);

        return cmdList.toArray(new String[cmdList.size()]);
    }

}
