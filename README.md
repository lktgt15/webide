# Web IDE 기능을 갖춘 Blog Project
- 회원가입  
  - 처리 :  
    - input : form으로 감싼 input들을 name과 매칭시켜 post로 전달하면 Controller에서 Mapping하여 가져올 수 있다.  
    
    - pw 암호화 : pw암호화는 Spring Security의 BCryptPasswordEncoder를 통해 Encode 후 DB에 저장하며 추후 로그인 확인할 때 
    pwencoder.match(raw,dbpw)를 통해 검증도 가능하다.  
    
    - DB 저장 : Repository의 Spring JPA EntityManager가 연동된 DB에 Entity인 Member를 저장해준다.  
  
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
    
#----------------------
- Todo:
  - IDE 
    - 