package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // 설계 변경으로 MemberServiceImpl은 MemoryMemberRepository에 의존하지 않게 됨
    private final MemberRepository memberRepository;

    // MemberServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지 알 수 x
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    // 회원 조회
    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
