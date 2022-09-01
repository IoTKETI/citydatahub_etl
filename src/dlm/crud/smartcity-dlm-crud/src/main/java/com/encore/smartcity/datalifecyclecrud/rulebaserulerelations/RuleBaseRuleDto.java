package com.encore.smartcity.datalifecyclecrud.rulebaserulerelations;

import lombok.Getter;

/**
 * Rule_Base_Rule_R 테이블의 데이터를 전달하기 위한 클래스
 */
@Getter
public class RuleBaseRuleDto {

    private String baseRuleId;
    private String baseRuleName;

    public RuleBaseRuleDto(RuleBaseRuleRelation relation) {
        baseRuleId = relation.getBaseRule().getBaseRuleId();
        baseRuleName = relation.getBaseRule().getBaseRuleName();
    }

}
