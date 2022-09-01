package com.encore.smartcity.datalifecyclecrud.rules;

import com.encore.smartcity.datalifecyclecrud.policyrulerelations.PolicyRuleRelation;
import com.encore.smartcity.datalifecyclecrud.rulebaserulerelations.RuleBaseRuleRelation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule 테이블의 필드 정의 및 생성 메소드를 정의한 클래스
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "RULE")
public class Rule {

    @Id
    @Column(name = "RULE_ID")
    private String ruleId;

    @Column(name = "RULE_NM")
    private String ruleName;

    @Column(name = "MOVE_CYCLE")
    private Long moveCycle;

    @Column(name = "DELETE_CYCLE")
    private Long deleteCycle;

    @Column(name = "`DESC`")
    private String description;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "REG_DTM")
    private LocalDateTime createdDate;

    @Column(name = "REGR_ID")
    private String createdUser;

    @Column(name = "MOD_DTM")
    private LocalDateTime modifiedDate;

    @Column(name = "MODR_ID")
    private String modifiedUser;

    @Transient
    private List<String> baseRuleList;

    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RuleBaseRuleRelation> ruleBaseRuleRelations = new ArrayList<>();

    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PolicyRuleRelation> policyRuleRelations = new ArrayList<>();

    //==Relation Method==//

    /**
     * Rule_Base_Rule_R 테이블 생성을 위한 메소드
     * @param ruleBaseRuleRelation
     */
    public void addRuleBaseRule(RuleBaseRuleRelation ruleBaseRuleRelation) {
        ruleBaseRuleRelations.add(ruleBaseRuleRelation);
        ruleBaseRuleRelation.setRule(this);
    }

    //==Create Method==//

    /**
     * Rule 테이블의 시스템 변수들(등록일시, 등록자ID, 수정일시, 수정자ID) 생성하는 메소드
     * @param rule
     * @param ruleBaseRuleRelations
     * @return
     */
    public static Rule createRule(Rule rule, List<RuleBaseRuleRelation> ruleBaseRuleRelations) {
        for(RuleBaseRuleRelation ruleBaseRuleRelation : ruleBaseRuleRelations) {
            rule.addRuleBaseRule(ruleBaseRuleRelation);
        }
        rule.setCreatedDate(LocalDateTime.now());
        rule.setCreatedUser("user");
        rule.setModifiedDate(LocalDateTime.now());
        rule.setModifiedUser("user");
        return rule;
    }

    //==Modify Method==//

    /**
     * Rule 테이블 수정 시 관계 테이블을 다시 생성하는 메소드
     * @param rule
     * @param ruleBaseRuleRelations
     * @return
     */
    public static Rule updateRule(Rule rule, List<RuleBaseRuleRelation> ruleBaseRuleRelations) {
        for(RuleBaseRuleRelation ruleBaseRuleRelation : ruleBaseRuleRelations) {
            rule.addRuleBaseRule(ruleBaseRuleRelation);
        }
        rule.setModifiedDate(LocalDateTime.now());
        rule.setModifiedUser("modified user");
        return rule;
    }

}