package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// AppConfig는 애플리케이션의 실제 동작에 필요한 "구현 객체" 생성
// AppConfig를 통해서 확실하게 관심사가 분리됨
// @Configuration: 애플리케이션의 구성정보
// @Configuration 안 붙여도 스프링 컨테이너에 @Bean 되어있는거 스프링 빈으로 다 등록이 됨
// But, @Configuration을 안붙이게 되면, AppConfigCGLIB에 의해 싱글톤이 보장되지 않음
@Configuration
public class AppConfig {
    // @Bean: 스프링 컨테이너에 등록됨
    // @Bean memberService -> new MemberRepository()
    // @Bean orderService -> new MemberRepository()
    // 이렇게 두 개 호출하면 싱글톤 깨지는 거 아닌지??
//    @Autowired MemberRepository memberRepository;
    @Bean
    public MemberService memberService() {
        // appConfig 객체는 memoryMemberRepository 객체를 생성하고 그 참조값을 memberServiceImpl을 생성하면서 생성자로 전달함
        // 즉, MemberServiceImpl에는 MemoryMemberRepository 객체의 의존관계가 주입됨
//        return new MemberServiceImpl(new MemoryMemberRepository());
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        // appConfig 객체는 memoryMemberRepository 객체와 fixDiscountPolicy 객체를 생성하고 그 참조값을 orderServiceImpl을 생성하면서 생성자로 전달함
        // 즉, OrderServiceImpl에는 MemoryMemberRepository 객체와 FixDiscountPolicy 객체의 의존관계가 주입됨
//        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        System.out.println("call AppConfig.orderService");
//        return new OrderServiceImpl(memberRepository(), discountPolicy());
        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // 할인정책을 FixDiscountPolicy -> RateDiscountPolicy로 변경
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
