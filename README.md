개인 프로젝트 - 사진 공유 sns
=====
# 1. 개요
- ## 개발 기간
  2019년 6월 ~ 2019년 7월 (5주)
- ## 개발 목적
  소셜 계정으로 로그인하여 사용할 수 있고 인스타그램과 비슷한 ui를 가진 sns 개발
- ## 특징
  Google 계정으로 회원가입 및 로그인<br>
  사진 업로드 기능<br>
  게시물 좋아요 기능<br>
  사용자 팔로우 기능<br>
  내가 좋아요한 글 모아보기 기능<br>
  내가 팔로우하는 사용자들의 게시글 모아보기 기능<br>
  프로필 사진 관리 기능<br>
- ## 사용 기술
  Java8, Javascript(ES6), HTML5/CSS3<br>
  SpringBoot, Vue.js, MySQL, JPA(Hibernate), Querydsl<br>
  OAuth2 JWT, google oauth client<br>
  AWS(RDS, S3, Elastic Beanstalk)
- ## 배포 URL
  [배포 URL](http://sns1906.ap-northeast-2.elasticbeanstalk.com)(크롬 브라우저 권장)
- ## 실행 영상
    [유튜브 주소](https://youtu.be/voAc9psuix0)

# 2. [프론트엔드 프로젝트](https://github.com/qwer0115ty/sns-front-end)
# 3. 백엔드 프로젝트
- ## 디렉토리 구조
```
├─java
│  └─com
│      └─boot
│          ├─config
│          │  │  AmazonS3Prop.java
│          │  │  DbConfig.java
│          │  │  ModelMapperConfig.java
│          │  │  
│          │  └─oauth2
│          │      │  AuthorizationServerConfig.java
│          │      │  ResourceServerConfig.java
│          │      │  
│          │      ├─client
│          │      │      CustomClientDetail.java
│          │      │      
│          │      └─social
│          │              ClientResources.java
│          │              OAuth2ClientConfig.java
│          │              OAuth2SuccessHandler.java
│          │              
│          ├─controller
│          │      BoardController.java
│          │      MyController.java
│          │      SignupController.java
│          │      UserController.java
│          │      
│          ├─demo
│          │      ServletInitializer.java
│          │      SnsBackEndApplication.java
│          │      
│          ├─exception
│          │      NoHandlerFoundExceptionHandler.java
│          │      
│          ├─jpa
│          │  │  AwsS3FileRepository.java
│          │  │  LoginUserRepository.java
│          │  │  
│          │  ├─board
│          │  │  │  BoardFileRepository.java
│          │  │  │  BoardLikeRepository.java
│          │  │  │  BoardRepository.java
│          │  │  │  
│          │  │  └─support
│          │  │          BoardLikeRepositorySupport.java
│          │  │          BoardRepositorySupport.java
│          │  │          
│          │  └─user
│          │          UserAuthorityRepository.java
│          │          UserFollowRepository.java
│          │          UserProfileRepository.java
│          │          UserRepository.java
│          │          UserRoleRepository.java
│          │          UserSocialRepository.java
│          │          
│          ├─model
│          │  │  AwsS3File.java
│          │  │  
│          │  ├─board
│          │  │      Board.java
│          │  │      BoardFile.java
│          │  │      BoardLike.java
│          │  │      BoardLikeResponseBoardDto.java
│          │  │      BoardsTarget.java
│          │  │      
│          │  ├─oauth2
│          │  │      Authority.java
│          │  │      GoogleUser.java
│          │  │      LoginUser.java
│          │  │      
│          │  └─user
│          │          User.java
│          │          UserAuthority.java
│          │          UserFollow.java
│          │          UserProfile.java
│          │          UserRole.java
│          │          UserSignupDto.java
│          │          UserSocial.java
│          │          
│          ├─service
│          │  │  AwsS3Service.java
│          │  │  AwsS3ServiceImpl.java
│          │  │  BoardService.java
│          │  │  BoardServiceImpl.java
│          │  │  UserService.java
│          │  │  UserServiceImpl.java
│          │  │  
│          │  └─oauth2
│          │          AuthService.java
│          │          AuthServiceImpl.java
│          │          ClientDetailsServiceImpl.java
│          │          
│          └─util
│                  CustomUtils.java
│                  
├─resources
│  │  application.yml
│  │  
│  ├─static
│  │  ├─css  
│  │  └─js
│  │          
│  └─templates
│          index.html
│          main.html
│          
└─webapp
    ├─temp
    └─WEB-INF
        └─jsp
                googleAuthSuccess.jsp
                signupGoogleAuthSuccess.jsp
                
```
