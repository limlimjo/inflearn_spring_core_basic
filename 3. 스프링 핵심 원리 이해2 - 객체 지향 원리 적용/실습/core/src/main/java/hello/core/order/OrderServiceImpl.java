package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    // 아래 코드를 보면 OrderServiceImpl은 DIP 원칙을 잘 지키고 있음
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
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
}
