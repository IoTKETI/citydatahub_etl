package com.encore.smartcity.datalifecyclecrud.policyrulerelations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Policy_Rule_R 테이블을 데이터베이스에 쿼리하는 클래스
 */
@Repository
@RequiredArgsConstructor
public class PolicyRuleRepository {

    private final EntityManager em;

    /**
     * Policy_Rule_R 테이블의 마지막으로 삽입된 레코드를 조회하는 메소드
     * @return
     */
    public List<PolicyRuleRelation> findLastRecord() {
        return em.createQuery("select p from PolicyRuleRelation p order by p.id desc",
                PolicyRuleRelation.class)
                .setMaxResults(1)
                .getResultList();
    }


}
