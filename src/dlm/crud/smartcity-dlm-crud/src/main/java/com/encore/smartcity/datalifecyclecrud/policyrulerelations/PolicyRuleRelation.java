package com.encore.smartcity.datalifecyclecrud.policyrulerelations;

import com.encore.smartcity.datalifecyclecrud.policies.Policy;
import com.encore.smartcity.datalifecyclecrud.rules.Rule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

/**
 * Policy_Rule_R 테이블의 필드 정의 및 생성 메소드를 정의한 클래스
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "POLICY_RULE_R")
public class PolicyRuleRelation {

    @Id
    @Column(name = "POLICY_RULE_R_ID")
    private String id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "RULE_ID")
    private Rule rule;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "POLICY_ID")
    private Policy policy;

    @Column(name = "REG_DTM")
    private LocalDateTime createdDate;

    @Column(name = "REGR_ID")
    private String createdUser;

    @Column(name = "MOD_DTM")
    private LocalDateTime modifiedDate;

    @Column(name = "MODR_ID")
    private String modifiedUser;

    //==Create Method==//

    /**
     * Policy_Rule_R 테이블의 시스템 변수들(등록일시, 등록자ID, 수정일시, 수정자ID) 생성하는 메소드
     * @param rule
     * @return
     */
    public static PolicyRuleRelation createPolicyRule(Rule rule) {
        PolicyRuleRelation policyRuleRelation = new PolicyRuleRelation();
        policyRuleRelation.setRule(rule);
        policyRuleRelation.setCreatedDate(LocalDateTime.now());
        policyRuleRelation.setCreatedUser("user");
        policyRuleRelation.setModifiedDate(LocalDateTime.now());

        return policyRuleRelation;
    }


}
