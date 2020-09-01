package lktgt.webide.service;

import lktgt.webide.domain.Code;
import lktgt.webide.domain.CodeExecuted;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class IDEService {

    private final CodeExecutedService codeExecutedService;

    public IDEService(CodeExecutedService codeExecutedService) {
        this.codeExecutedService = codeExecutedService;
    }

    @Async("threadPoolTaskExecutor")
    public void exec(Code code) throws IOException {
        CodeExecuted codeExecuted = new CodeExecuted();

        File file = ResourceUtils.getFile("classpath:static/code/Main.cc");
        String path = file.getPath();

        String beforeCode = code.getCode();
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

        String[] array = cmdStringList("g++ "+path+" -o Main -O2 -Wall -lm -static -std=gnu++17");

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
                System.out.println("성공");
                System.out.println(successoutput.toString());
            } else {
                System.out.println("cmd 비정상 종료");
                System.out.println(successoutput.toString());
            }

            if (!(erroroutput.toString().isEmpty())) {
                System.out.println("Error");
                System.out.println(erroroutput.toString());
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
                System.out.println("성공");
                System.out.println(successoutput.toString());
                codeExecuted.setResult(successoutput.toString());

            } else {
                System.out.println("cmd 비정상 종료");
                System.out.println(successoutput.toString());
                codeExecuted.setResult(successoutput.toString());
            }

            if (!(erroroutput.toString().isEmpty())) {
                System.out.println("Error");
                System.out.println(erroroutput.toString());
                codeExecuted.setResult(successoutput.toString());
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

        codeExecuted.setName(code.getName());
        codeExecuted.setCodeId(code.getId());

        codeExecutedService.join(codeExecuted);

    }

    private String[] cmdStringList(String cmd){
        List<String> cmdList = new ArrayList<String>();
        cmdList.add("cmd");
        cmdList.add("/c");
        cmdList.add(cmd);

        return cmdList.toArray(new String[cmdList.size()]);
    }

}