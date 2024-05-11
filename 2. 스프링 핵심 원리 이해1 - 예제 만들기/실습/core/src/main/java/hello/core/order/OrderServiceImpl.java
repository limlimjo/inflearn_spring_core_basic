package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

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
