package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {
    // 테스트 결과 url 정보 없이 connect가 호출되는 것 확인할 수 있음
    // 객체를 생성하는 단계에는 url이 없고, 객체를 생성한 다음에 외부에서 생성자 주입을 통해 setUrl()이 호출되어야 url이 존재하게 됨
    @Test
    public void lifeCycleTest() {
        // ConfigurableApplicationContext는 거의 모든 애플리케이션 컨텍스트가 갖는 공통 애플리케이션 컨텍스트 인터페이스로써
        // ApplicationContext, Lifecycle, Closable 인터페이스를 상속받음
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        // 스프링 컨테이너 종료 (ConfigurableApplicationContext 필요)
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
