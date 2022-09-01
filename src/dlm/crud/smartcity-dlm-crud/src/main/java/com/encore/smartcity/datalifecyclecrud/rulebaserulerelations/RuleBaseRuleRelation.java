package com.encore.smartcity.datalifecyclecrud.rulebaserulerelations;

import com.encore.smartcity.datalifecyclecrud.baserules.BaseRule;
import com.encore.smartcity.datalifecyclecrud.rules.Rule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

/**
 * Rule_Base_Rule_R 테이블의 필드 정의 및 생성 메소드를 정의한 클래스
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "RULE_BASE_RULE_R")
public class RuleBaseRuleRelation {

    @Id
    @Column(name = "RULE_BASE_RULE_R_ID")
    private String id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "BASE_RULE_ID")
    private BaseRule baseRule;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "RULE_ID")
    private Rule rule;

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
     * Rule_Base_Rule_R 테이블의 시스템 변수들(등록일시, 등록자ID, 수정일시, 수정자ID) 생성하는 메소드
     * @param baseRule
     * @return
     */
    public static RuleBaseRuleRelation createRuleBaseRule(BaseRule baseRule) {
        RuleBaseRuleRelation ruleBaseRuleRelation = new RuleBaseRuleRelation();
        ruleBaseRuleRelation.setBaseRule(baseRule);
        ruleBaseRuleRelation.setCreatedDate(LocalDateTime.now());
        ruleBaseRuleRelation.setCreatedUser("user");
        ruleBaseRuleRelation.setModifiedDate(LocalDateTime.now());
        ruleBaseRuleRelation.setModifiedUser("modified_user");

        return ruleBaseRuleRelation;
    }
}