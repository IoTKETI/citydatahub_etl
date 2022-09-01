package com.encore.smartcity.datalifecyclecrud.baserules;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * BaseRule 테이블을 데이터베이스에 쿼리하는 클래스
 */
@Repository
@RequiredArgsConstructor
public class BaseRuleRepository {

    private final EntityManager em;

    /**
     * BaseRule 테이블에 레코드를 저장하는 메소드
     *
     * @param baseRule
     */
    public void save(BaseRule baseRule) {
        em.persist(baseRule);
//        if(baseRule.getBaseRuleId() == null) {
//            em.persist(baseRule);
//        } else {
//            em.merge(baseRule);
//        }
    }

    public void update(BaseRule baseRule) {
        em.merge(baseRule);
    }

    /**
     * BaseRule 테이블의 레코드를 기본규칙ID를 이용하여 조회하는 메소드
     * @param baseRuleId
     * @return
     */
    public BaseRule findOneById(String baseRuleId) {
        return em.find(BaseRule.class, baseRuleId);
    }

    /**
     * BaseRule 테이블의 레코드를 기본규칙명을 이용하여 조회하는 메소드
     * @param baseRuleName
     * @return
     */
    public List<BaseRule> findByNames(String baseRuleName) {
        return em.createQuery("select b from BaseRule b where b.baseRuleName = :baseRuleName", BaseRule.class)
                .setParameter("baseRuleName", baseRuleName)
                .getResultList();
    }

    /**
     * BaseRule 테이블의 모든 목록을 조회하는 메소드
     * @return
     */
    public List<BaseRule> findAll() {
        return em.createQuery("select b from BaseRule b order by b.baseRuleId desc", BaseRule.class)
                .getResultList();
    }

    /**
     * BaseRule 테이블의 마지막으로 삽입된 레코드를 조회하는 메소드
     * @return
     */
    public List<BaseRule> findLastRecord() {
        return em.createQuery("select b from BaseRule b order by b.baseRuleId desc", BaseRule.class)
                .setMaxResults(1)
                .getResultList();
    }

    /**
     * BaseRule 테이블의 레코드를 삭제하는 메소드
     * @param baseRule
     */
    public void delete(BaseRule baseRule) {
        em.remove(baseRule);
    }
}
