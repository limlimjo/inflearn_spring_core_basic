package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        // AppConfig를 AnnotationConfigApplicationContext로 넘기면 얘로 스프링빈으로 등록이 됨
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        // 순수한 클래스면 class.hello.core.AppConfig 이런식으로 출력이 되어야 함.
        // But, 예상과는 다르게 클래스 명에 xxxCGLIB가 붙음
        // 이것은 내가 만든 클래스가 아님
        // 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용함
        // 이를 사용해서 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것
        // AppConfig (부모 클래스), AppConfigCGLIB (자식 클래스)
        // 부모 클래스로 조회하면 자식 클래스 다 조회됨
        // 결론적으로 AppConfig가 조작을 해서 스프링 컨테이너에 빈이 등록되어 있으면 등록된 것을 뽑아주고,
        // 아니면 내가 만들었던 로직을 호출해서 뽑아주는 것임
        System.out.println("bean = " + bean.getClass());
    }

}
