# Web IDE 기능을 갖춘 Blog Project
- 회원가입  
  - 처리 :  
    - input : form으로 감싼 input들을 name과 매칭시켜 post로 전달하면 Controller에서 Mapping하여 가져올 수 있다.  
    
    - pw 암호화 : pw암호화는 Spring Security의 BCryptPasswordEncoder를 통해 Encode 후 DB에 저장하며 추후 로그인 확인할 때 
    pwencoder.match(raw,dbpw)를 통해 검증도 가능하다.  
    
    - DB 저장 : Repository의 Spring JPA EntityManager가 연동된 DB에 Entity인 Member를 저장해준다. 
    
    - 중복 확인 : 
    회원가입 page에서 입력받은 form을 토대로 새로운 member를 생성해 name과 pw를 set하고 join을 시도한다.  
    ```java
    @PostMapping("/member/new")
    public String create(Model model, MemberForm memberForm) {
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setPassword(passwordEncoder.encode(memberForm.getPassword()));

        String result = memberService.join(member);
        model.addAttribute("result",result);

        return "redirect:/";
    }
    ``` 
    join은 validateDupMember를 통해 member가 DB에 있는지 확인한다.  
    ```java
    public String join(Member member){
        validateDupMember(member);
        jpaMemberRepository.save(member);
        return "등록되었습니다.";
    }

    private void validateDupMember(Member member){
        jpaMemberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    ```
    jpaMemberRepository의 findByName은 입력받은 name을 MySQL에 쿼리를 날려 일치하는 name을 가진 member를
    Optional<Member>로 return하는 함수다. 이것을 ifPresent로 만약 NULL이 아니면 중복이므로 IllegalStateException으로 예외 처리를 해준다.  
    ```java
    @ExceptionHandler(IllegalStateException.class)
    public String exceptionHandler(Model model,Exception e){
        model.addAttribute("result",e.getMessage());
        return "member/createMemberForm";
    }
    ```
    예외 처리는 MemberController에서 `@ExceptionHandler`를 이용하여 model에 "이미 존재하는 회원입니다."라는 메세지를 담아서 view에 보여줄 수 있도록 한다.  
    
  
  - 완성 : pw 암호화, id 중복 체크
- 로그인
  - 처리 :
    - 권한 부여 : Spring SecurityConfig에서 제공하는 UserDetails를 implements하여 loadUserByUsername을 작성한다. 
    Repository의 Entity Manager를 이용하여 DB에서 이름을 검색하여 해당 User의 정보를 가져온다. Optional<T>로 감싸주었는데 이는 NULL exception을 방지하기 위해서다.
    여기에서 Set<GrantedAuthority>에 사용자 지정 ROLE을 add하여 User의 파라미터로 넘겨주면 SpringSecurity에서 User이름과 ROLE을 설정하여 리턴해준다.  
    
    - DB에서 불러오기 : Entity Manager의 createQuery(쿼리,entity)를 통해 List에 Member를 저장하며, Stream API의 .findAny()를 통해 첫번째 요소를 골라오고,
    만약 첫번째 요소도 없는 NULL이어도 Optional로 감쌌기 때문에 null exception이 발생하지 않는다.
    
  - 완성 : DB에서 아이디 체크 , 권한 부여  
  - 미완성 : Remeber 체크박스
  
- 로그아웃
  - 완성 : 로그아웃

- IDE
  - 처리 :
    - 서버에 코드 post : 회원가입 때와 같이 @Postmapping으로 가져오는데, 추가로 Spring Security의 Principal을 이용하여 
    로그인한 username까지 알아온다. Code는 username과 함께 DB에 저장된다.  
    - 파일로 저장 : code를 DB에 저장하고 IDEService의 exec를 실행한다. 여기서 "classpath:static/code/Main.cc"파일에 방금 받은 코드 내용을 쓴다. 
        ```java
        File file = ResourceUtils.getFile("classpath:static/code/Main.cc")
        String path = file.getPath();
        ```
      을 이용하여 내 Local에서 파일의 절대경로를 가져올 수 있다. 이렇게 얻은 Path로 
      ```java
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
      bufferedWriter.write(beforeCode);
      bufferedWriter.close();
      ```
      BufferedWriter를 이용하여 file에 code내용을 쓴다. Filewriter로도 바로 string을 file에 쓸 수 있지만, BufferedWriter를 사용하면
      Buffer를 이용하여 CPU 사용 횟수와 메모리 접근 횟수를 줄여 성능을 높인다.  
      
    - cmd로 컴파일 :
      ```java
      Runtime runtime = Runtime.getRuntime();
      Process process = null;
        
      StringBuffer successoutput = new StringBuffer();
      StringBuffer erroroutput = new StringBuffer();
      BufferedReader successreader = null;
      BufferedReader errorreader = null;
      String msg = "";
      ```
      Runtime을 이용하여 JVM이 작동하는 운영체제의 인터페이스를 사용할 준비를 한다.  
      사용할 process도 준비한다. BufferedReader로 process의 Inputstream과 Errorstream 을 읽을 준비를 한다.  
      process.getInputStream()과 getErrorStream()에서 BufferedReader로 받아온 것을 StringBuffer에 add할 것이다.  
      
      ```java
      String[] array = cmdStringList("g++ "+path+" -o Main -O2 -Wall -lm -static -std=gnu++17");
      ```
      로 windows cmd 실행 + 컴파일 옵션을 설정하여 컴파일 할 수 있게 한다.  
    - 코드 실행 결과 보기 : 
      기능의 내 코드를 누르면 DB에서 code table에서 username으로 검색하여 List를 반환하여 thymeleaf의 each를 이용하여 결과를 display한다.  
    - 랜덤인풋 생성기 :  
      
  - 완성 : Code 컴파일, 실행, 결과 DB에 저장, 코드 결과 보기
  - 미완성 : 코드 Id 클릭시 상세 정보 보기, 랜덤인풋 생성기
      
    
#----------------------
- Todo:
  - IDE 
    - 인풋 동적으로 추가하고 list로 서버에 받아오는것까지 성공, 여러가지 validation 필요할듯