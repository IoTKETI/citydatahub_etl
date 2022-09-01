package com.encore.smartcity.datalifecyclescheduler.policyrulerelations;

import com.encore.smartcity.datalifecyclescheduler.policies.Policy;
import com.encore.smartcity.datalifecyclescheduler.rules.Rule;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

/**
 * Policy_Rule_R 테이블의 필드가 정의된 클래스
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "POLICY_RULE_R")
@ToString
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

}
