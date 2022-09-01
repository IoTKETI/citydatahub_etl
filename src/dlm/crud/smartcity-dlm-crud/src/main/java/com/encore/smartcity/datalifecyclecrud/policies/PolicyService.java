package com.encore.smartcity.datalifecyclecrud.policies;

import com.encore.smartcity.datalifecyclecrud.common.CommonService;
import com.encore.smartcity.datalifecyclecrud.common.TableName;
import com.encore.smartcity.datalifecyclecrud.policyrulerelations.PolicyRuleDto;
import com.encore.smartcity.datalifecyclecrud.policyrulerelations.PolicyRuleRelation;
import com.encore.smartcity.datalifecyclecrud.policyrulerelations.PolicyRuleRepository;
import com.encore.smartcity.datalifecyclecrud.rules.Rule;
import com.encore.smartcity.datalifecyclecrud.rules.RuleRepository;
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
 * Repository 클래스와 Controller 클래스 사이의 비즈니스 로직을 처리하는 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyService {

    private final RuleRepository ruleRepository;

    private final PolicyRepository policyRepository;

    private final PolicyRuleRepository relationRepository;

    /**
     * Policy 테이블의 레코드 삽입을 위한 로직 처리
     * @param policy
     * @return
     */
    @Transactional
    public Policy policy(Policy policy) {
        List<PolicyRuleRelation> policyRuleRelations = new ArrayList<>();

        String policyId = getPolicyId();
        policy.setPolicyId(policyId);

        Long relationNo = getIdCount();

        for(String ruleId : policy.getRuleList()) {
            Rule rule = ruleRepository.findOneById(ruleId);
            PolicyRuleRelation policyRuleRelation = PolicyRuleRelation.createPolicyRule(rule);
            String policyRuleId = getPolicyRuleId(relationNo);
            policyRuleRelation.setId(policyRuleId);
            policyRuleRelations.add(policyRuleRelation);
            relationNo++;
        }

        Policy newPolicy = Policy.create(policy, policyRuleRelations);

        policyRepository.save(newPolicy);

        return newPolicy;
    }

    /**
     * Policy 테이블의 ID를 규칙에 맞게 생성하는 메소드
     *
     * @return
     */
    private String getPolicyId() {
        List<Policy> lastRecord = policyRepository.findLastRecord();
        String baseTableName = TableName.POLICY + "";
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String policyId;

        if(lastRecord.size() == 0) {
            policyId = baseTableName + "_" + today + "_" + "001";
        } else {
            Policy exPolicy = lastRecord.get(0);
            String exPolicyId = exPolicy.getPolicyId();

            String[] exTableIds = exPolicyId.split("_");
            String exTableIdNo = exPolicyId.split("_")[exTableIds.length-1];
            policyId = CommonService.getTableId(baseTableName, exPolicyId, Long.valueOf(exTableIdNo));
        }

        return policyId;
    }

    /**
     * Policy 테이블의 ID를 규칙에 맞게 생성하는 메소드
     *
     * @return
     */
    private String getPolicyRuleId(Long relationNo) {
        List<PolicyRuleRelation> lastRecord = relationRepository.findLastRecord();
        String baseTableName = TableName.POLICY_RULE_R + "";
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String relationId;

        if(lastRecord.size() == 0) {
            relationId = baseTableName + "_" + today + "_" + relationNo;
        } else {
            PolicyRuleRelation exRelation = lastRecord.get(0);
            String exRelationId = exRelation.getId();

            relationId = CommonService.getTableId(baseTableName, exRelationId, relationNo);
        }

        return relationId;
    }

    /**
     * Policy 테이블의 마지막 레코드 ID 번호를 구하는 메소드
     * @return
     */
    private Long getIdCount() {
        List<PolicyRuleRelation> lastRecord = relationRepository.findLastRecord();
        if(lastRecord.size() == 0) {
            return 0L;
        } else {
            PolicyRuleRelation exRelation = lastRecord.get(0);
            String exRelationId = exRelation.getId();

            return CommonService.getTableIdCount(exRelationId);
        }
    }

    /**
     * 정책명의 중복여부를 확인하는 메소드
     * @param policyName
     * @return
     */
    public boolean checkDuplicatePolicyName(String policyName) {
        List<Policy> policies = policyRepository.findByNames(policyName);
        return policies.isEmpty();
    }

    /**
     * 소스파일명의 중복여부를 확인하는 메소드
     * @param srcFileName
     * @return
     */
    public boolean checkDuplicateSrcFileName(String srcFileName) {
        List<Policy> policies = policyRepository.findBySrcFileName(srcFileName);
        return policies.isEmpty();
    }

    /**
     * 목적파일명의 중복여부를 확인하는 메소드
     * @param destFileName
     * @return
     */
    public boolean checkDuplicateDestFileName(String destFileName) {
        List<Policy> policies = policyRepository.findByDescFileName(destFileName);
        return policies.isEmpty();
    }

    /**
     * Policy 테이블 중 정책ID를 이용하여 조회하는 메소드
     * @param policyId
     * @return
     */
    public Policy findOneById(String policyId) {
        return policyRepository.findOneById(policyId);
    }

    /**
     * Policy 테이블의 레코드를 삭제하기 위한 메소드
     * @param id
     */
    @Transactional
    public void deleteById(String id) {
        Policy policy = policyRepository.findOneById(id);
        policyRepository.delete(policy);
    }

    /**
     * Policy 테이블의 레코드를 수정하기 위한 메소드
     * @param policy
     * @param policyDto
     * @return
     */
    @Transactional
    public Policy update(Policy policy, PolicyDto policyDto) {
        if(!policyDto.getPolicyName().equals(policy.getPolicyName())) {
            policy.setPolicyName(policyDto.getPolicyName());
        }

        if(!policyDto.getSrcFileName().equals(policy.getSrcFileName())) {
            policy.setSrcFileName(policyDto.getSrcFileName());
        }

        if(!policyDto.getDestFileName().equals(policy.getDestFileName())) {
            policy.setDestFileName(policyDto.getDestFileName());
        }

        if(!policyDto.getStatus().equals(policy.getStatus())) {
            policy.setStatus(policyDto.getStatus());
            policy.setSetupDate(LocalDateTime.now());
        }

        policy.setPolicyId(policyDto.getPolicyId());
        policy.setSrcPathName(policyDto.getSrcPathName());
        policy.setDestPathName(policyDto.getDestPathName());
        policy.setRuleList(policyDto.getRuleList());
        policy.setModifiedDate(LocalDateTime.now());
        policy.setModifiedUser("modified user");
        policy.setRuleList(policyDto.getRuleList());

        List<PolicyRuleRelation> policyRuleRelations = policy.getPolicyRuleRelations();

        List<String> ruleIds = policyRuleRelations.stream()
                .map(relation -> new PolicyRuleDto(relation).getRuleId())
                .collect(Collectors.toList());

        List<PolicyRuleRelation> newRelation = new ArrayList<>();
        Policy newPolicy;

        if(!(ruleIds.size() == policyDto.getRuleList().size() &&
                ruleIds.containsAll(policyDto.getRuleList()))) {

            policyRepository.deletePolicyRuleRelation(policyRuleRelations);
            policy.setPolicyRuleRelations(new ArrayList<>());

            Long relationNo = getIdCount();

            for(String ruleId : policy.getRuleList()) {
                Rule rule = ruleRepository.findOneById(ruleId);
                PolicyRuleRelation policyRuleRelation = PolicyRuleRelation.createPolicyRule(rule);
                String policyRuleId = getUpdatePolicyRuleId(relationNo);
                policyRuleRelation.setId(policyRuleId);
                policyRuleRelations.add(policyRuleRelation);
                relationNo++;
            }

            newPolicy = Policy.updatePolicy(policy, newRelation);
            policyRepository.newUpdate(newPolicy);
            return newPolicy;

        } else {
            policyRepository.update(policy);
            return policy;
        }

    }

    private String getUpdatePolicyRuleId(Long relationNo) {
        List<PolicyRuleRelation> lastRecord = relationRepository.findLastRecord();
        String baseTableName = TableName.POLICY_RULE_R + "";
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String relationId;

        if(lastRecord.size() == 0) {
            relationId = baseTableName + "_" + today + "_" + relationNo;
        } else {
            PolicyRuleRelation exRelation = lastRecord.get(0);
            String exRelationId = exRelation.getId();

            relationId = CommonService.getUpdateTableId(baseTableName, exRelationId, relationNo);
        }

        return relationId;
    }
}
