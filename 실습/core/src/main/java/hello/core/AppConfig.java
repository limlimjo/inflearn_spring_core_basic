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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// AppConfig는 애플리케이션의 실제 동작에 필요한 "구현 객체" 생성
// AppConfig를 통해서 확실하게 관심사가 분리됨
// @Configuration: 애플리케이션의 구성정보
@Configuration
public class AppConfig {
    // @Bean: 스프링 컨테이너에 등록됨
    @Bean
    public MemberService memberService() {
        // appConfig 객체는 memoryMemberRepository 객체를 생성하고 그 참조값을 memberServiceImpl을 생성하면서 생성자로 전달함
        // 즉, MemberServiceImpl에는 MemoryMemberRepository 객체의 의존관계가 주입됨
//        return new MemberServiceImpl(new MemoryMemberRepository());
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        // appConfig 객체는 memoryMemberRepository 객체와 fixDiscountPolicy 객체를 생성하고 그 참조값을 orderServiceImpl을 생성하면서 생성자로 전달함
        // 즉, OrderServiceImpl에는 MemoryMemberRepository 객체와 FixDiscountPolicy 객체의 의존관계가 주입됨
//        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // 할인정책을 FixDiscountPolicy -> RateDiscountPolicy로 변경
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
