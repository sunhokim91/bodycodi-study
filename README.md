# spring-rest-doc-swagger 적용

## 서론
스프링에서 API문서 만드는 방법중에는 rest-doc을 사용하는 방법과 swagger방법이 있습니다. 하지만 이 둘은 각각의 장점과 단점을 가지고 있기에 둘의 장점을 포용하여 API문서로 만들 수 있다면 시너지가 많이 일어날 것으로 보여집니다.


### 스프링 rest doc과 swagger 특징
스프링 rest doc의 경우 테스트 코드를 통해 API 문서를 만들 수 있습니다. 이는 서비스 코드에 영향을 주지 않고 테스트를 강제할 수 있다는 점에서 좋지만 디자인 측면이 떨어지고 API문서를 통해 API테스트를 할 수 없습니다. 또한 rest doc설정과 테스트 코드와 더불어 adoc파일에도 수정이 필요한 점이 있다는 단점이 존재합니다.

swagger의 경우 rest doc과 반대로 디자인 측면이 뛰어나고 API 문서를 통해 API를 호출하여 테스트를 할 수 있습니다. 하지만 서비스 코드에 주석을 달아야 하고 문서만으로 모든 연동 시스템을 구현할 수 없습니다.

### OPEN API 3.0 Spcipication(OAS)
OpenAPI Specification (OAS) 은 RESTful API 를 기술하는 표준으로 서비스에서 제공하는 API 의 기능과 End Point를 개발자나 시스템이 자동으로 발견하고 처리하는데 필요한 정보를 제공합니다.

또한 OAS 는 json 이나 yml 형식으로 기술해야 하며 OAS 파일을 읽어서 디플로이 해주는 도구(Ex: swagger-ui)를 사용하면 아래와 같이 브라우저에서 편리하게 API 문서를 볼 수 있습니다.

그리고 OAS의 대표적으로 swagger와 rest doc이 있는데, OAS를 사용하면 rest doc 테스트 코드를 swagger-ui로 변경할 수 있는 인터페이스 역할도 서포트합니다.

## 📌 실습
### 버전
1. 스프링 버전: 2.7.8
2. 자바 버전: 11
### 기능
1. 로그인 기능: API문서에 일반 사람들이 접근하지 못하게 해야 하기에 로그인 기능을 추가합니다.
2. swagger-ui도커를 통해 swagger문서를 띄우는 게 아닌 spring 프로젝트로 swagger문서를 띄우는 형식으로 개발합니다.

swagger-ui다운로드 -> dist폴더에 있는 내용만 가져옵니다.

### 📌 메이븐 라이브러리 추가

### 타임리프
타임리프는 흔히 뷰 템플릿(View Template)이라 부르며 컨트롤러가 전달하는 데이터를 이용하여 동적으로 화면을 구성하게 해줍니다. html 태그에 속성을 추가해 페이지에 동적으로 값을 추가하거나 처리하고 html태그 기반에 th:속성으로 동적인 뷰를 제공합니다. 스프링 부트(Spring Boot)에서는 JSP 사용 시 호환 및 환경설정에 어려움이 많기 때문에 타임리프 사용 권장하고 있습니다.

여기서 타임리프 라이브러리는 반드시 스프링 부트 버전을 사용해야 합니다.
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
만약 스프링 부트 전용 타임리프를 사용하지 않는다면 빌드시에는 에러가 나지 않지만, 실제 사용 시 에러가 발생 할 수 있습니다.

### rest doc
```xml
<snippetsDirectory>${project.build.directory}/generated-snippets</snippetsDirectory>

<dependency>
	<groupId>org.springframework.restdocs</groupId>
	<artifactId>spring-restdocs-mockmvc</artifactId>
	<scope>test</scope>
</dependency>

<plugin>
	<groupId>org.asciidoctor</groupId>
	<artifactId>asciidoctor-maven-plugin</artifactId>
	<version>1.5.8</version>
		<executions>
			<execution>
				<id>generate-docs</id>
				<phase>package</phase>
				<goals>
					<goal>process-asciidoc</goal>
				</goals>
				<configuration>
					<backend>html</backend>
					<doctype>book</doctype>
					<attributes>
						<snippets>${snippetsDirectory}</snippets>
					</attributes>
						<sourceDirectory>src/docs/asciidoc</sourceDirectory>
				</configuration>
			</execution>
		</executions>
	<dependencies>
		<dependency>
			<groupId>org.springframework.restdocs</groupId>
			<artifactId>spring-restdocs-asciidoctor</artifactId>
			<version>${spring-restdocs.version}</version>
		</dependency>
	</dependencies>
</plugin>
<plugin>
	<artifactId>maven-resources-plugin</artifactId>
	<version>3.3.0</version>
	<executions>
		<execution>
			<id>copy-resources</id>
			<phase>prepare-package</phase>
			<goals>
				<goal>copy-resources</goal>
			</goals>
			<configuration>
				<outputDirectory>
					src/main/resources/static/api/docs
				</outputDirectory>
				<resources>
					<resource>
						<directory>
							${project.build.directory}/generated-docs
						</directory>
					</resource>
				</resources>
			</configuration>
		</execution>
	</executions>
</plugin>
```
### security 라이브러리
```xml
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-test</artifactId>
	<scope>test</scope>
</dependency>
```
이 라이브러리는 로그인 기능을 구현하기 위해 넣어줍니다.

### OAS 라이브러리
```xml
<properties>
	<restdocs-api-spec.version>0.10.0</restdocs-api-spec.version>
	<restdocs-spec.version>0.19</restdocs-spec.version>
</properties>

<repositories>
	<repository>
		<id>jcenter</id>
		<url>https://jcenter.bintray.com</url>
	</repository>
</repositories>

<dependency>
	<groupId>com.epages</groupId>
	<artifactId>restdocs-api-spec</artifactId>
	<version>${restdocs-api-spec.version}</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>com.epages</groupId>
	<artifactId>restdocs-api-spec-mockmvc</artifactId>
	<version>${restdocs-api-spec.version}</version>
	<scope>test</scope>
</dependency>

<pluginRepositories>
	<pluginRepository>
		<id>jcenter</id>
		<url>https://jcenter.bintray.com</url>
	</pluginRepository>
</pluginRepositories>

<plugin>
	<groupId>com.github.berkleytechnologyservices.restdocs-spec</groupId>
	<artifactId>restdocs-spec-maven-plugin</artifactId>
	<version>${restdocs-spec.version}</version>
	<executions>
		<execution>
			<goals>
				<goal>generate</goal>
			</goals>
			<configuration>
				<specification>OPENAPI_V3</specification>
				<format>JSON</format>
				<outputDirectory>${project.build.directory}/classes/static/api/docs</outputDirectory>
			</configuration>
		</execution>
	</executions>
</plugin>
```

## 구현 코드
### swagger 연동
swagger와 연동하려면 먼저 swagger-ui를 만들어야 합니다. 링크는 아래 참고링크에서 다운받으며 되며 dist폴더에 있는 js, html, css파일만 가져옵니다.

### 로그인 기능 구현
#### 로그인 페이지
MvcConfigure를 상속받아 컨트롤러의 구현이 필요없이 특정 URL와 매핑되는 웹페이지 링크를 넣어줍니다. 아래 addViewController는 URL이고, setViewName은 특정 웹 페이지(html)입니다.
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    public static final String API_LOGIN = "api/login";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/api").setViewName(API_LOGIN);
        registry.addViewController("/api/login").setViewName(API_LOGIN);
    }
}
```
addViewController는 URL이고, setViewName은 특정 웹 페이지(html)입니다.  웹 페이지의 경로는 maven과 gradle별로 다르지만 저는 maven을 사용했기에, templates사용해야 하며 templates폴더는 thymeleaf의 파일들을 두는 곳입니다.

여기서 주의할 점은 웹페이지가 매핑될 때 타임리프와 연계가 되는데, 이때 절대경로로 넣어주면 안되고 상대경로로 넣어주어야 합니다. 위 코드는 IDE(ex: 인텔리제이)에서는 빌드가 가능하지만 실제 java 명령어를 통해 jar파일을 실행할 때 경로를 찾을 때 마지막에 /를 붙여 파일 경로를 찾기에 경로를 못찾는 경우가 발생합니다. 따라서 상대경로로 맞추어 줍니다.

#### 로그인 기능
```java
@EnableWebSecurity
public class WebSecurityConfig {
    private static final String API_DOCS_USER_API_HTML = "/api/docs/index.html";
    private static final String LOGIN = "/api/login";

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles(Roles.ADMIN.name())
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionAuthenticationErrorUrl(LOGIN)
                .invalidSessionUrl(LOGIN)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(API_DOCS_USER_API_HTML).hasRole(Roles.ADMIN.name())
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage(LOGIN)
                .loginProcessingUrl(LOGIN)
                .defaultSuccessUrl(API_DOCS_USER_API_HTML)
                .permitAll();

        return http.build();
    }
}
```
EnableWebSecurity어노테이션을 붙이면 스프링 시큐리티가 발동됩니다. 시큐리티 관련 내용은 다음 포스트에서 말씀드리고, 앞에서 /api/login으로 접속하여 로그인을 성공적으로 마무리하면 API_DOCS_USER_API_HTML에 swagger-ui로 다운받은 html파일을 호출합니다. html파일의 경우 반드시 로그인이 성공되어야 접근 가능합니다.

### 실습 시 발생된 이슈사항
#### 1.빌드 오류
빌드 시 `class file has wrong version`와 같은 오가 발생했는 데 이는 스프링 버전과 자바 버전의 호환이 맞지 않아 발생된 문제입니다. 따라서 스프링 버전에 따라 자바버전이 맞는 지 확인해야 합니다.

#### 2. 타겟 위치에 html파일이 생기지 않는 이유
plugin으로 아무리 설치를 해봐도 generated-docs폴더에 html파일이 생기지 않는 것입니다. 이는 test코드에 작성한다고 해서 html파일이 저절로 생기는 것이 아닌 하나의 ->src/docs/api 밑에 adoc으로 생성된 파일에 또 다시 써야합니다.

#### 3. com.epages.restdocs.apispec.model.ResourceModel Desrialize에러

```
Cannot deserialize instance of `java.lang.String` out of START_OBJECT token
 at [Source: (File); line: 15, column: 23] (through reference chain: com.epages.restdocs.apispec.model.ResourceModel["request"]->com.epages.restdocs.apispec.model.RequestModel["headers"]->java.util.ArrayList[0]->com.epages.restdocs.apispec.model.HeaderDescriptor["description"])
```
위 에러는 MockMvc로 rest doc템플릿을 만들 때 Header에 Media타입을 넣고 있는 지 확인 바랍니다. 만약 그렇게 넣고 있다면 오류 메시지 처럼 String 타입으로 넣어주시면 됩니다.

## 참고 링크
* git site 주소
* https://github.com/swagger-api/swagger-ui
