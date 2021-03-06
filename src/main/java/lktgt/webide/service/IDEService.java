package lktgt.webide.service;

import lktgt.webide.domain.Code;
import lktgt.webide.domain.CodeForm;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class IDEService {

    private final CodeService codeService;

    public IDEService(CodeService codeService) {
        this.codeService = codeService;
    }

    @Async("threadPoolTaskExecutor")
    public String execThread(CodeForm codeForm, Code code, boolean isRandominput) throws IOException {
        String ret = null;
        System.out.println("execThread Start");
        try {
            if (isRandominput) {
                Code inputCode = codeService.getCstyleCode(codeForm);
                String result = this.exec(inputCode, "RandomInputGen.cc");
                System.out.println("result:"+result);
                if (result != "Error") {
                    ret = this.exec(code, "RandomMain.cc");
                }
                else ret = "Error";
            } else {
                ret = this.exec(code, "Main.cc");
            }
        }catch (IOException e){
            System.out.println("execThread IOException");
            System.out.println(e.getMessage());
            ret = e.getMessage();
        }finally {
            System.out.println("execThread End");
            return ret;
        }
    }

    /*
    Compile and Exec
     */
    private String exec(Code code,String filename) throws IOException {

        String filepath = "classpath:static/code/"+filename;
        File file = ResourceUtils.getFile(filepath);
        String path = file.getPath();

        String beforeCode = code.getCode();
        System.out.println("code:"+code.getCode());

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(beforeCode);
        bufferedWriter.close();

        Runtime runtime = Runtime.getRuntime();
        Process process = null;

        StringBuilder successoutput = new StringBuilder();
        StringBuilder erroroutput = new StringBuilder();
        BufferedReader successreader = null;
        BufferedReader errorreader = null;
        String msg = "";

        boolean success = false;

        String[] array = null;

        filename = filename.substring(0,filename.length()-3);

        array = cmdStringList("g++ "+path+" -o "+filename+" -O2 -Wall -lm -static -std=gnu++17",true);


        /*
        ====================↓ Compile =======================
         */

        try {
            process = runtime.exec(array);


            process.waitFor();

            successreader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
            while ((msg = successreader.readLine()) != null) {
                successoutput.append(msg + System.getProperty("line.separator"));
            }


            errorreader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
            while ((msg = errorreader.readLine()) != null) {
                erroroutput.append(msg + System.getProperty("line.separator"));
            }


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
                int idx = erroroutput.toString().indexOf(".cc:");
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
                System.out.println(process.pid());
                process.destroy();
                if(successreader != null) successreader.close();
                if(errorreader != null) errorreader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(success == false) {
            return "Error";
        }

        /*
        ====================↑ Compile ===================

        ====================↓ Exec ======================
         */

        System.out.println("중간 filename:"+filename);
        if(filename.equals("RandomMain")) {
            filepath = "build/resources/main/static/code/RandomInputGen.txt";
            System.out.println(filepath);
            array = cmdStringList(filename+".exe < "+filepath,true);
        }
        else {
            System.out.println("else");
            array = cmdStringList(filename+".exe",false);
        }

        try {
            process = runtime.exec(array);


            if(!process.waitFor(4,TimeUnit.SECONDS)){
                System.out.println("time out");
                System.out.println(process.pid());
                process.destroy();
                System.out.println("return");
                return "error";
            }

            successreader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
            while ((msg = successreader.readLine()) != null) {
                successoutput.append(msg + System.getProperty("line.separator"));
            }


            errorreader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
            while ((msg = errorreader.readLine()) != null) {
                erroroutput.append(msg + System.getProperty("line.separator"));
            }


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
                code.setResult(erroroutput.toString());
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

        System.out.println("filename:"+filename);
        if(filename.equals("RandomInputGen")) {
            System.out.println("Randominput을 txt파일로 저장");
            filepath = "classpath:static/code/RandomInputGen.txt";
            file = ResourceUtils.getFile(filepath);

            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(code.getResult());
            bufferedWriter.close();
        }
        else{
            codeService.join(code);
        }
        return code.getResult();
    }

    private String[] cmdStringList(String cmd,boolean compile){
        List<String> cmdList = new ArrayList<String>();
        if(compile) {
            cmdList.add("cmd");
            cmdList.add("/c");
        }
        cmdList.add(cmd);

        return cmdList.toArray(new String[cmdList.size()]);
    }

}
