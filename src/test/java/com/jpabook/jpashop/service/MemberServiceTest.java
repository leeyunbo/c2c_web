package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

// Spring이랑 엮어서 실행할래
@RunWith(SpringRunner.class)
// Spring Boot를 띄운 상태로 실행할래 (Autowired)
@SpringBootTest
// Transactional -> 테스트 케이스에 있으면 기본으로 전략이 롤백임!
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given면 이게 주어지면
        Member member = new Member();
        member.setName("lee yun bok");

        //when 이걸 실행하면
        Long saveId = memberService.join(member);

        // 같은 트랜잭션에서 ID 값이 같으면 같은 영속성 컨텍스트가 되어서 딱 하나로 관리된다.
        // 따라서 True가 나올 수 있다.
        // then 이렇게 된다
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member = new Member();
        member.setName("lee yun bok");

        Member member2 = new Member();
        member2.setName("lee yun bok");

        //when
        memberService.join(member);
        try {
            memberService.join(member2); // 예외가 발생해야함
        } catch (IllegalStateException e) {
            // 예외 발생시 종료
            return;
        }

        //then
        // fail 함수가 실행되면 실패!
        fail("예외가 발생해야 한다.");
    }

}