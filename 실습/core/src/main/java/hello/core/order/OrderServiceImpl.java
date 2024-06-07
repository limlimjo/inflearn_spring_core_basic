package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    // 아래 코드를 보면 OrderServiceImpl은 DIP 원칙을 잘 지키고 있음
    // final은 무조건 값이 할당되어야 함
    // 필드 주입은 사용하지 않는 것이 좋음
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DiscountPolicy discountPolicy;

    // 수정자 주입은 의존관계 두 번째 단계에서 일어난다고 보면 됨
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }

    // 중요! 생성자가 딱 1개만 있으면 @Autowired를 생략해도 자동 주입됨. (물론 스프링 빈에만 해당)
    // 생성자는 Spring Lifecycle 빈 등록할 때 자동 주입이 일어남
    // @Autowired의 기본 동작은 주입할 대상이 없으면 오류가 발생. 주입할 대상이 없어도 동작하게 하려면 @Autowired(required = false)로 지정하면 됨
//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        // 생성자 주입 확인
//        System.out.println("memberRepository = " + memberRepository);
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }
// 할인 정책 변경 문제점 -> 할인 정책을 변경하려면 클라이언트인 OrderServiceImpl의 아래 코드를 고쳐야 함
    // 위에서 언급한 문제점은 DIP, OCP 모두 위반
    // DIP: 인터페이스에만 의존하고, 구현체에는 의존x
    // OCP: 확장에는 열려있고, 변경에는 닫혀있어야 함
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // 그럼 어떻게 해결해야할까?
    // 아래처럼 인터페이스만 의존하도록 변경
    // But, 구현체가 없는데 코드 실행 어떻게? -> 코드 실행하면 Null Pointer Exception 발생
    // 그럼 어떻게 해결해야할까?
    // 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 함
//    private DiscountPolicy discountPolicy;


    // 1. 주문 생성 요청이 들어오면
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 2. 회원 정보 조회
        Member member = memberRepository.findById(memberId);
        // 3. 할인 정책에다가 회원 정보 넘김
        int discountPrice = discountPolicy.discount(member, itemPrice);
        // 4. 주문 정보 반환
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
