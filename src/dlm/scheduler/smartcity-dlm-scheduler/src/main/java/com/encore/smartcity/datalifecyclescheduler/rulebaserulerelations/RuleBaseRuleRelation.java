package com.encore.smartcity.datalifecyclescheduler.rulebaserulerelations;


import com.encore.smartcity.datalifecyclescheduler.baserules.BaseRule;
import com.encore.smartcity.datalifecyclescheduler.rules.Rule;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

/**
 * Rule_Base_Rule_R 테이블의 필드가 정의된 클래스
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "RULE_BASE_RULE_R")
@ToString
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

}