package com.encore.smartcity.datalifecyclecrud.baserules;

import com.encore.smartcity.datalifecyclecrud.common.CommonService;
import com.encore.smartcity.datalifecyclecrud.common.TableName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Repository 클래스와 Controller 클래스 사이의 비즈니스 로직을 처리하는 클래스
 *
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BaseRuleService {

    private final BaseRuleRepository baseRuleRepository;

    /**
     * BaseRule 테이블의 레코드 삽입을 위한 로직 처리
     * @param baseRule
     * @return
     */
    @Transactional
    public BaseRule join(BaseRule baseRule) {
        String baseRuleId = getBaseRuleId();
        baseRule.setBaseRuleId(baseRuleId);

        baseRuleRepository.save(BaseRule.create(baseRule));
        return baseRule;
    }

    /**
     * BaseRule 테이블의 ID를 규칙에 맞게 생성하는 메소드
     *
     * @return
     */
    public String getBaseRuleId() {
        List<BaseRule> lastRecord = baseRuleRepository.findLastRecord();
        String baseTableName = TableName.BASE_RULE + "";
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String baseRuleId;

        if(lastRecord.size() == 0) {
            baseRuleId = baseTableName + "_" + today + "_" + "001";

        } else {
            BaseRule exBaseRule = lastRecord.get(0);
            String exBaseRuleId = exBaseRule.getBaseRuleId();

            String[] exTableIds = exBaseRuleId.split("_");
            String exTableIdNo = exBaseRuleId.split("_")[exTableIds.length-1];

            baseRuleId = CommonService.getTableId(baseTableName, exBaseRuleId, Long.valueOf(exTableIdNo));
        }

        return baseRuleId;
    }

    /**
     * 기본규칙명의 중복여부를 확인하는 메소드
     * @param baseRuleName
     * @return
     */
    public boolean checkDuplicateBaseRule(String baseRuleName) {
        List<BaseRule> findBaseRules = baseRuleRepository.findByNames(baseRuleName);
        return findBaseRules.isEmpty();
    }

    /**
     * BaseRule 테이블의 모든 목록을 조회하는 메소드
     * @return
     */
    public List<BaseRule> findBaseRules() {
        return baseRuleRepository.findAll();
    }

    /**
     * BaseRule 테이블 중 기본규칙ID를 이용하여 조회하는 메소드
     * @param baseRuleId
     * @return
     */
    public BaseRule findOneById(String baseRuleId) {
        return baseRuleRepository.findOneById(baseRuleId);
    }

    /**
     * BaseRule 테이블의 레코드를 수정하기 위한 메소드
     * @param id
     * @param baseRuleDto
     * @return
     */
    @Transactional
    public BaseRule update(String id, BaseRuleDto baseRuleDto) {
        BaseRule baseRule = baseRuleRepository.findOneById(id);
        if(!baseRuleDto.getBaseRuleName().equals(baseRule.getBaseRuleName())) {
            baseRule.setBaseRuleName(baseRuleDto.getBaseRuleName());
        }
        baseRule.setDefaultMoveCycle(baseRuleDto.getDefaultMoveCycle());
        baseRule.setDefaultDeleteCycle(baseRuleDto.getDefaultDeleteCycle());
        baseRule.setDescription(baseRuleDto.getDescription());
        baseRule.setMemo(baseRuleDto.getMemo());
        baseRule.setModifiedDate(LocalDateTime.now());
        baseRule.setModifiedUser("modified user");

        baseRuleRepository.update(baseRule);

        return baseRule;
    }

    /**
     * BaseRule 테이블의 레코드를 삭제하기 위한 메소드
     * @param id
     */
    @Transactional
    public void deleteById(String id) {
        BaseRule oneById = baseRuleRepository.findOneById(id);
        baseRuleRepository.delete(oneById);
    }
}
