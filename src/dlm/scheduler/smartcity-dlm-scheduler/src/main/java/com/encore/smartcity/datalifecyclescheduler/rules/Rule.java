package com.encore.smartcity.datalifecyclescheduler.rules;

import com.encore.smartcity.datalifecyclescheduler.policyrulerelations.PolicyRuleRelation;
import com.encore.smartcity.datalifecyclescheduler.rulebaserulerelations.RuleBaseRuleRelation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule 테이블의 필드가 정의된 클래스
 */
@ToString
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
}