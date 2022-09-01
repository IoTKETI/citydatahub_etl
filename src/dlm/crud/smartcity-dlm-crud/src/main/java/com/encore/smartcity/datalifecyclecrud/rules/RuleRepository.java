package com.encore.smartcity.datalifecyclecrud.rules;

import com.encore.smartcity.datalifecyclecrud.rulebaserulerelations.RuleBaseRuleRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Rule 테이블을 데이터베이스에 쿼리하는 클래스
 */
@Repository
@RequiredArgsConstructor
public class RuleRepository {

    private final EntityManager em;

    /**
     * Rule 테이블에 레코드를 저장하는 메소드
     *
     * @param rule
     */
    public void save(Rule rule) {
        em.persist(rule);
//        if(rule.getRuleId() == null) {
//            em.persist(rule);
//        } else {
//            em.merge(rule);
//        }
    }

    public void update(Rule rule) {
        em.merge(rule);
    }

    public void newUpdate(Rule rule) {
        em.persist(rule);
    }

    /**
     * Rule 테이블의 레코드를 규칙ID를 이용하여 조회하는 메소드
     * @param ruleId
     * @return
     */
    public Rule findOneById(String ruleId) {
        return em.find(Rule.class, ruleId);
    }

    /**
     * Rule 테이블의 레코드를 규칙명을 이용하여 조회하는 메소드
     * @param ruleName
     * @return
     */
    public Rule findOneByName(String ruleName) {
        return em.find(Rule.class, ruleName);
    }

    /**
     * Rule 테이블의 모든 목록을 조회하는 메소드
     * @return
     */
    public List<Rule> findAll() {
        return em.createQuery("select r from Rule r order by r.ruleId desc", Rule.class)
                .getResultList();
    }

    /**
     * Rule 테이블의 레코드를 규칙명을 이용하여 조회하는 메소드
     * @param ruleName
     * @return
     */
    public List<Rule> findByNames(String ruleName) {
        return em.createQuery("select r from Rule r where r.ruleName = :ruleName", Rule.class)
                .setParameter("ruleName", ruleName)
                .getResultList();
    }

    /**
     * Rule_Base_Rule_R 테이블의 레코드를 삭제하는 메소드
     * @param relations
     */
    public void deleteRuleBaseRuleRelation(List<RuleBaseRuleRelation> relations) {
        for(RuleBaseRuleRelation relation : relations) {
            RuleBaseRuleRelation findRelation = em.find(RuleBaseRuleRelation.class, relation.getId());
            em.remove(findRelation);
        }
    }

    /**
     * Rule 테이블의 레코드를 삭제하는 메소드
     * @param rule
     */
    public void delete(Rule rule) {
        em.remove(rule);
    }

    /**
     * Rule 테이블의 레코드를 삭제하는 메소드
     * @return
     */
    public List<Rule> findLastRecord() {
        return em.createQuery("select r from Rule r order by r.ruleId desc", Rule.class)
                .setMaxResults(1)
                .getResultList();

    }
}
