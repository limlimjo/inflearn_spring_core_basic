package hello.core.member;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// MemberRepository의 구현체 MemoryMemberRepository
@Component
@Primary
public class MemoryMemberRepository implements MemberRepository {

    // 저장소
    // cf) 동시성 이슈 때문에 원래는 Concurrent HashMap을 쓰는게 맞음
    private static Map<Long, Member> store = new HashMap<>();

    // 회원 가입
    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    // 회원 조회
    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
