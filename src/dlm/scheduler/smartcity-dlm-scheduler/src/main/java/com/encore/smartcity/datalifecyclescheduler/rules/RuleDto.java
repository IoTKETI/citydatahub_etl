package com.encore.smartcity.datalifecyclescheduler.rules;

import com.encore.smartcity.datalifecyclescheduler.policyrulerelations.PolicyRuleDto;
import com.encore.smartcity.datalifecyclescheduler.rulebaserulerelations.RuleBaseRuleDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rule 테이블의 데이터를 전달하기 위한 클래스
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleDto {

    private String ruleId;
    private String ruleName;
    private Long moveCycle;
    private Long deleteCycle;
    private String description;
    private String memo;
    private LocalDateTime createdDate;
    private String createdUser;
    private LocalDateTime modifiedDate;
    private String modifiedUser;
    private List<String> baseRuleList;
    private List<RuleBaseRuleDto> ruleBaseRuleRelations;
    private List<PolicyRuleDto> policyRuleDtos;

    public RuleDto(Rule rule) {
        ruleId = rule.getRuleId();
        ruleName = rule.getRuleName();
        moveCycle = rule.getMoveCycle();
        deleteCycle = rule.getDeleteCycle();
        description = rule.getDescription();
        memo = rule.getMemo();
        createdDate = rule.getCreatedDate();
        createdUser = rule.getCreatedUser();
        modifiedDate = rule.getModifiedDate();
        modifiedUser = rule.getModifiedUser();
        baseRuleList = rule.getBaseRuleList();
        ruleBaseRuleRelations = rule.getRuleBaseRuleRelations().stream()
                .map(relation -> new RuleBaseRuleDto(relation))
                .collect(Collectors.toList());
    }

}