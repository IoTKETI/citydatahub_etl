package com.encore.smartcity.datalifecyclescheduler.policies.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * Policy_Execut_Hist 테이블의 쿼리가 정의된 클래스
 */
@Repository
@RequiredArgsConstructor
public class PolicyExecutRepository {

    private final EntityManager em;

    /**
     * Policy_Execut_Hist에 레코드를 저장하는 메소드
     * @param policyExecutHist
     */
    public void save(PolicyExecutHist policyExecutHist) {
        em.persist(policyExecutHist);
        em.flush();
    }
}
