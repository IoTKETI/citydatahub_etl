package com.encore.smartcity.datalifecyclecrud.rules;

import com.encore.smartcity.datalifecyclecrud.baserules.BaseRule;
import com.encore.smartcity.datalifecyclecrud.baserules.BaseRuleRepository;
import com.encore.smartcity.datalifecyclecrud.common.CommonService;
import com.encore.smartcity.datalifecyclecrud.common.TableName;
import com.encore.smartcity.datalifecyclecrud.rulebaserulerelations.RuleBaseRuleDto;
import com.encore.smartcity.datalifecyclecrud.rulebaserulerelations.RuleBaseRuleRelation;
import com.encore.smartcity.datalifecyclecrud.rulebaserulerelations.RuleBaseRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rpository 클래스와 Controller 클래스 사이의 비즈니스 로직을 처리하는 클래스
 *
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RuleService {

    private final RuleRepository ruleRepository;

    private final BaseRuleRepository baseRuleRepository;

    private final RuleBaseRuleRepository relationRepository;

    /**
     * Rule 테이블의 레코드 삽입을 위한 로직 처리
     * @param rule
     * @return
     */
    @Transactional
    public Rule rule(Rule rule) {
        List<RuleBaseRuleRelation> ruleBaseRuleRelations = new ArrayList<>();

        String ruleId = getRuleId();
        rule.setRuleId(ruleId);

        Long relationNo = getIdCount();

        for(String baseRuleId : rule.getBaseRuleList()) {
            BaseRule baseRule = baseRuleRepository.findOneById(baseRuleId);
            RuleBaseRuleRelation ruleBaseRule = RuleBaseRuleRelation.createRuleBaseRule(baseRule);
            String ruleBaseRuleId = getUpdateRuleBaseRuleId(relationNo);
            ruleBaseRule.setId(ruleBaseRuleId);
            ruleBaseRuleRelations.add(ruleBaseRule);
            relationNo++;
        }

        Rule newRule = Rule.createRule(rule, ruleBaseRuleRelations);

        ruleRepository.save(newRule);

        return newRule;
    }

    /**
     * Rule 테이블의 ID를 규칙에 맞게 생성하는 메소드
     *
     * @return
     */
    private String getRuleId() {
        List<Rule> lastRecord = ruleRepository.findLastRecord();
        String baseTableName = TableName.RULE + "";
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String ruleId;

        if(lastRecord.size() == 0) {
            ruleId = baseTableName + "_" + today + "_" + "001";
        } else {
            Rule exRule = lastRecord.get(0);
            String exRuleId = exRule.getRuleId();

            String[] exTableIds = exRuleId.split("_");
            String exTableIdNo = exRuleId.split("_")[exTableIds.length-1];
            ruleId = CommonService.getTableId(baseTableName, exRuleId, Long.valueOf(exTableIdNo));
        }

        return ruleId;
    }

    /**
     * Rule 테이블의 마지막 레코드 ID 번호를 구하는 메소드
     * @return
     */
    private Long getIdCount() {
        List<RuleBaseRuleRelation> lastRecord = relationRepository.findLastRecord();
        if(lastRecord.size() == 0) {
            return 1L;
        } else {
            RuleBaseRuleRelation exRelation = lastRecord.get(0);
            String exRelationId = exRelation.getId();

            return CommonService.getTableIdCount(exRelationId);
        }
    }

    /**
     * Rule_Base_Rule_R 테이블의 ID를 규칙에 맞게 생성하는 메소드
     *
     * @return
     */
    private String getRuleBaseRuleId(Long relationNo) {
        List<RuleBaseRuleRelation> lastRecord = relationRepository.findLastRecord();
        String baseTableName = TableName.RULE_BASE_RULE_R + "";
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String relationId;

        if(lastRecord.size() == 0) {
            relationId = baseTableName + "_" + today + "_" + "00" + relationNo;
        } else {
            RuleBaseRuleRelation exRelation = lastRecord.get(0);
            String exRelationId = exRelation.getId();

            relationId = CommonService.getTableId(baseTableName, exRelationId, relationNo);
        }

        return relationId;
    }

    /**
     * 규칙명의 중복여부를 확인하는 메소드
     * @param ruleName
     * @return
     */
    public boolean checkDuplicateRuleName(String ruleName) {
        List<Rule> rules = ruleRepository.findByNames(ruleName);
        return rules.isEmpty();
    }

    /**
     * Rule 테이블의 모든 목록을 조회하는 메소드
     * @return
     */
    public List<Rule> findRules() {
        return ruleRepository.findAll();
    }

    /**
     * Rule 테이블 중 규칙ID를 이용하여 조회하는 메소드
     * @param ruleId
     * @return
     */
    public Rule findOneById(String ruleId) {
        return ruleRepository.findOneById(ruleId);
    }

    /**
     * Rule 테이블의 레코드를 수정하기 위한 메소드
     * @param rule
     * @param ruleDto
     * @return
     */
    @Transactional
    public Rule update(Rule rule, RuleDto ruleDto) {
        if(!ruleDto.getRuleName().equals(rule.getRuleName())) {
            rule.setRuleName(ruleDto.getRuleName());
        }
        rule.setRuleId(ruleDto.getRuleId());
        rule.setMoveCycle(ruleDto.getMoveCycle());
        rule.setDeleteCycle(ruleDto.getDeleteCycle());
        rule.setDescription(ruleDto.getDescription());
        rule.setMemo(ruleDto.getMemo());
        rule.setModifiedDate(LocalDateTime.now());
        rule.setModifiedUser("modified user");
        rule.setBaseRuleList(ruleDto.getBaseRuleList());

        List<RuleBaseRuleRelation> ruleBaseRuleRelations = rule.getRuleBaseRuleRelations();

        List<String> baseRuleIds = ruleBaseRuleRelations.stream()
                .map(relation -> new RuleBaseRuleDto(relation).getBaseRuleId())
                .collect(Collectors.toList());

        List<RuleBaseRuleRelation> newRelation = new ArrayList<>();
        Rule newRule;

        if(!(baseRuleIds.size() == ruleDto.getBaseRuleList().size() &&
                baseRuleIds.containsAll(ruleDto.getBaseRuleList()))) {

            ruleRepository.deleteRuleBaseRuleRelation(ruleBaseRuleRelations);
            rule.setRuleBaseRuleRelations(new ArrayList<>());

            Long relationNo = getIdCount();

            for(String baseRuleId : ruleDto.getBaseRuleList()) {
                BaseRule baseRule = baseRuleRepository.findOneById(baseRuleId);
                RuleBaseRuleRelation ruleBaseRule = RuleBaseRuleRelation.createRuleBaseRule(baseRule);
                String ruleBaseRuleId = getUpdateRuleBaseRuleId(relationNo);
                ruleBaseRule.setId(ruleBaseRuleId);
                newRelation.add(ruleBaseRule);
                relationNo++;
            }

            newRule = Rule.updateRule(rule, newRelation);
            ruleRepository.newUpdate(newRule);
            return newRule;

        } else {
            ruleRepository.update(rule);
            return rule;
        }

    }

    private String getUpdateRuleBaseRuleId(Long relationNo) {
        List<RuleBaseRuleRelation> lastRecord = relationRepository.findLastRecord();
        String baseTableName = TableName.RULE_BASE_RULE_R + "";
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String relationId;

        if(lastRecord.size() == 0) {
            relationId = baseTableName + "_" + today + "_" + "00" + relationNo;
        } else {
            RuleBaseRuleRelation exRelation = lastRecord.get(0);
            String exRelationId = exRelation.getId();

            relationId = CommonService.getUpdateTableId(baseTableName, exRelationId, relationNo);
        }

        return relationId;
    }

    /**
     * Rule 테이블의 레코드를 삭제하기 위한 메소드
     * @param id
     */
    @Transactional
    public void deleteById(String id) {
        Rule ruleById = ruleRepository.findOneById(id);
        ruleRepository.delete(ruleById);
    }

}
