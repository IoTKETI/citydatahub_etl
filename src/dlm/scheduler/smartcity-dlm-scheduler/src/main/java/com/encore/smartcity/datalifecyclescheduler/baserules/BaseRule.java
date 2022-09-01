package com.encore.smartcity.datalifecyclescheduler.baserules;

import com.encore.smartcity.datalifecyclescheduler.rulebaserulerelations.RuleBaseRuleRelation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseRule 테이블의 필드를 정의한 클래스
 */
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "BASE_RULE")
public class BaseRule {

    @Id
    @Column(name = "BASE_RULE_ID")
    private String baseRuleId;

    @Column(name = "BASE_RULE_NM")
    private String baseRuleName;

    @Column(name = "DFLT_MOVE_CYCLE")
    private Long defaultMoveCycle;

    @Column(name = "DFLT_DELETE_CYCLE")
    private Long defaultDeleteCycle;

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

    @OneToMany(mappedBy = "baseRule", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RuleBaseRuleRelation> ruleBaseRuleRelations = new ArrayList<>();

}