package com.encore.smartcity.datalifecyclecrud.rulebaserulerelations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Rule_Base_Rule_R 테이블을 데이터베이스에 쿼리하는 클래스
 */
@Repository
@RequiredArgsConstructor
public class RuleBaseRuleRepository {

    private final EntityManager em;

    /**
     * Rule_Base_Rule_R 테이블의 마지막으로 삽입된 레코드를 조회하는 메소드
     * @return
     */
    public List<RuleBaseRuleRelation> findLastRecord() {
        return em.createQuery("select r from RuleBaseRuleRelation r order by r.id desc",
                RuleBaseRuleRelation.class)
                .setMaxResults(1)
                .getResultList();
    }
}
