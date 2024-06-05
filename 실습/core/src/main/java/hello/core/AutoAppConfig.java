package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// 컴포넌트 스캔은 이름 그대로 @Component 어노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록함
@Configuration
@ComponentScan(
        // basePackages: 탐색할 패키지의 시작 위치 지정
        // basePackages를 지정하지 않으면 상단 package에 지정된 거 기준으로 다 뒤짐
        basePackages = "hello.core.member",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}