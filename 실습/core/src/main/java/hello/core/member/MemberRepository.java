package hello.core.member;

// 회원 저장소
public interface MemberRepository {

    // 회원 가입
    void save(Member member);

    // 회원 조회
    Member findById(Long memberId);
}
