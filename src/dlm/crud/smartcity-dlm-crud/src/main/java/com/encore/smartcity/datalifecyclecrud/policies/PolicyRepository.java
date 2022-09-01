package com.encore.smartcity.datalifecyclecrud.policies;

import com.encore.smartcity.datalifecyclecrud.policyrulerelations.PolicyRuleRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Policy 테이블을 데이터베이스에 쿼리하는 클래스
 */
@Repository
@RequiredArgsConstructor
public class PolicyRepository {

    private final EntityManager em;

    /**
     * Policy 테이블에 레코드를 저장하는 메소드
     *
     * @param policy
     */
    public void save(Policy policy) {
        em.persist(policy);
//        if(policy.getPolicyId() == null) {
//            em.persist(policy);
//        } else {
//            em.merge(policy);
//        }
    }

    public void update(Policy policy) {
        em.merge(policy);
    }

    public void newUpdate(Policy policy) {
        em.persist(policy);
    }

    /**
     * Policy 테이블의 레코드를 정책ID를 이용하여 조회하는 메소드
     * @param policyId
     * @return
     */
    public Policy findOneById(String policyId) {
        return em.find(Policy.class, policyId);
    }

    /**
     * Policy 테이블의 모든 목록을 조회하는 메소드
     * @return
     */
    public List<Policy> findAll() {
        return em.createQuery("select p from Policy p order by p.policyId desc", Policy.class)
                .getResultList();
    }

    /**
     * Policy 테이블의 레코드를 정책명을 이용하여 조회하는 메소드
     * @param policyName
     * @return
     */
    public List<Policy> findByNames(String policyName) {
        return em.createQuery("select p from Policy p where p.policyName = :policyName", Policy.class)
                .setParameter("policyName", policyName)
                .getResultList();
    }

    /**
     * Policy 테이블의 레코드를 삭제하는 메소드
     * @param policy
     */
    public void delete(Policy policy) {
        em.remove(policy);
    }

    /**
     * Policy_Rule_R 테이블의 레코드를 삭제하는 메소드
     * @param relations
     */
    public void deletePolicyRuleRelation(List<PolicyRuleRelation> relations) {
        for (PolicyRuleRelation relation : relations) {
            PolicyRuleRelation findRelation = em.find(PolicyRuleRelation.class, relation.getId());
            em.remove(findRelation);
        }
    }

    /**
     * Policy 테이블의 레코드를 소스파일명을 이용하여 조회하는 메소드
     * @param srcFileName
     * @return
     */
    public List<Policy> findBySrcFileName(String srcFileName) {
        return em.createQuery("select p from Policy p where p.srcFileName = :srcFileName", Policy.class)
                .setParameter("srcFileName", srcFileName)
                .getResultList();
    }

    /**
     * Policy 테이블의 레코드를 목적파일명을 이용하여 조회하는 메소드
     * @param destFileName
     * @return
     */
    public List<Policy> findByDescFileName(String destFileName) {
        return em.createQuery("select p from Policy p where p.destFileName = :destFileName", Policy.class)
                .setParameter("destFileName", destFileName)
                .getResultList();
    }

    /**
     * Policy 테이블의 마지막으로 삽입된 레코드를 조회하는 메소드
     * @return
     */
    public List<Policy> findLastRecord() {
        return em.createQuery("select p from Policy p order by p.policyId desc", Policy.class)
                .setMaxResults(1)
                .getResultList();
    }
}
