package com.encore.smartcity.datalifecyclescheduler.policies;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Policy 테이블의 쿼리가 정의된 클래스
 */
@Repository
@RequiredArgsConstructor
public class PolicyRepository {

    private final EntityManager em;

    /**
     * 실행상태가 Y인 레코드만 조회하는 메소드
     * @return
     */
    public List<Policy> findStatusActive() {
        return em.createQuery("select p from Policy p where p.status = 'Y' ", Policy.class)
                .getResultList();
    }

    /**
     * JPA의 영속성 컨테스트를 초기화하는 메소드
     */
    public void clear() {
        em.clear();
    }
}
