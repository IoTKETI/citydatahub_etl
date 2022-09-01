package com.encore.smartcity.datalifecyclecrud.rules;

import com.encore.smartcity.datalifecyclecrud.common.CommonController;
import com.encore.smartcity.datalifecyclecrud.policyrulerelations.PolicyRuleDto;
import com.encore.smartcity.datalifecyclecrud.rulebaserulerelations.RuleBaseRuleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rule 테이블의 CRUD 및 관련 메소드를 정의한 클래스
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rule")
public class RuleController {

    private final RuleService ruleService;

    private final RuleRepository ruleRepository;

    /**
     * Rule 테이블의 레코드를 생성하는 메소드
     * 규칙명, 이관주기, 삭제주기, 설명, 메모 필드와 관련된 기본규칙을 요청 받음
     * 규칙명, 이관주기, 삭제주기, 기본규칙은 빈 값을 받을 수 없음
     * 규칙명은 중복된 값을 받을 수 없음
     * 삭제주기는 이관주기보다 짧을 수 없음
     * 결과를 json 형태로 반환
     *
     * @param request
     * @param errors
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    public ResponseEntity createRule(@RequestBody @Valid CreateRuleRequest request,
                                     Errors errors) throws URISyntaxException {

        if(errors.hasErrors()) {
            return CommonController.badRequest(errors);
        }

        validateCheck(request, errors);
        if(errors.hasErrors()) {
            return CommonController.badRequest(errors);
        }

        if(!ruleService.checkDuplicateRuleName(request.getRuleName())) {
            return CommonController.badRequest(errors);
        }

        Rule rule = new Rule();
        rule.setRuleName(request.getRuleName());
        rule.setMoveCycle(request.getMoveCycle());
        rule.setDeleteCycle(request.getDeleteCycle());
        rule.setDescription(request.getDescription());
        rule.setMemo(request.getMemo());
        rule.setBaseRuleList(request.getBaseRuleList());

        Rule savedRule = ruleService.rule(rule);
        URI location = new URI("/api/rule/" + savedRule.getRuleId());

        return ResponseEntity.created(location).body(savedRule);
    }

    /**
     * Rule 테이블의 레코드 생성 시 요청을 받아오는 내부 클래스
     * 규칙명, 이관주기, 삭제주기, 설명, 메모 필드와 기본규칙을 요청 받음
     * 규칙명, 이관주기, 삭제주기, 기본규칙은 빈 값을 받을 수 없음
     * 이관주기 및 삭제주기는 최솟값이 0
     *
     */
    @Data
    static class CreateRuleRequest {

        @NotEmpty
        private String ruleName;

        @Min(0)
        private Long moveCycle;

        @Min(0)
        private Long deleteCycle;

        private String description;

        private String memo;

        @NotEmpty
        private List<String> baseRuleList;
    }

    /**
     * 이관주기와 삭제주기 길이를 체크하는 메소드
     * 삭제주기는 이관주기보다 짧을 수 없음
     *
     * @param request
     * @param errors
     */
    private void validateCheck(CreateRuleRequest request, Errors errors) {
        if(request.getMoveCycle() > request.getDeleteCycle()) {
            errors.rejectValue("defaultMoveCycle", "wrongValue", "이동 기간은 삭제 기간보다 길 수 없습니다.");
            errors.rejectValue("defaultDeleteCycle", "wrongValue", "이동 기간은 삭제 기간보다 길 수 없습니다.");
        }
    }

    /**
     * Rule 테이블의 전체목록을 조회하는 메소드
     * json 형태로 반환
     *
     * @return
     */
    @GetMapping
    public ResponseEntity findRules() {

        List<Rule> rules = ruleRepository.findAll();
        List<ResponseRuleDto> result = rules.stream()
                .map(r -> new ResponseRuleDto(r))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new Result(result));

    }

    /**
     * Rule 테이블의 상세내용을 조회하는 메소드
     * 레코드가 존재하지 않을 경우 404 에러 반환
     * 결과는 json 형태로 반환
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity findRuleById(@PathVariable("id") String id) {

        Rule rule = ruleRepository.findOneById(id);
        if(rule == null) {
            return ResponseEntity.notFound().build();
        }

        ResponseRuleDto result = new ResponseRuleDto(rule);

        return ResponseEntity.ok(new Result(result));
    }

    /**
     * 조회메소드 반환 시 관계 테이블까지 조회하는 메소드
     */
    @Data
    @AllArgsConstructor
    static class ResponseRuleDto {
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
        private List<RuleBaseRuleDto> ruleBaseRuleRelations;
        private List<PolicyRuleDto> policyRuleRelations;

        public ResponseRuleDto(Rule rule) {
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
                ruleBaseRuleRelations = rule.getRuleBaseRuleRelations().stream()
                        .map(relation -> new RuleBaseRuleDto(relation))
                        .collect(Collectors.toList());
                policyRuleRelations = rule.getPolicyRuleRelations().stream()
                        .map(relation -> new PolicyRuleDto(relation))
                        .collect(Collectors.toList());
            }
    }

    /**
     * Rule 테이블의 조회 메소드 반환 시 json 형태로 반환하기 위한 내부 클래스
     *
     * @param <T>
     */
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    /**
     * Rule 테이블의 레코드를 수정하는 메소드
     * 규칙명, 이관주기, 삭제주기, 설명, 메모 필드와 관련규칙을 요청 받음
     * 규칙명, 이관주기, 삭제주기, 관련규칙은 빈 값을 받을 수 없음
     * 규칙명은 수정 대상 레코드의 규칙명을 제외한 타 레코드와 중복된 값을 받을 수 없음
     *
     * 레코드가 존재하지 않을 경우 404 에러 반환
     * 결과는 json 형태로 반환
     *
     * @param id
     * @param request
     * @param errors
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity updateRule(@PathVariable("id") String id,
                                     @RequestBody @Valid UpdateRuleRequest request,
                                     Errors errors) {
        Rule oneById = ruleService.findOneById(id);

        if(oneById == null) {
            return ResponseEntity.notFound().build();
        }

        if(errors.hasErrors()) {
            return CommonController.badRequest(errors);
        }

        if(!oneById.getRuleName().equals(request.getRuleName()) &&
        !ruleService.checkDuplicateRuleName(request.getRuleName())) {
            return CommonController.badRequest(errors);
        }

        RuleDto ruleDto = new RuleDto();
        ruleDto.setRuleId(id);
        ruleDto.setRuleName(request.getRuleName());
        ruleDto.setMoveCycle(request.getMoveCycle());
        ruleDto.setDeleteCycle(request.getDeleteCycle());
        ruleDto.setDescription(request.getDescription());
        ruleDto.setMemo(request.getMemo());
        ruleDto.setBaseRuleList(request.getBaseRuleList());

        Rule updatedRule = ruleService.update(oneById, ruleDto);

        UpdateRuleResponse response = new UpdateRuleResponse();
        response.setRuleId(updatedRule.getRuleId());
        response.setRuleName(updatedRule.getRuleName());
        response.setMoveCycle(updatedRule.getMoveCycle());
        response.setDeleteCycle(updatedRule.getDeleteCycle());
        response.setDescription(updatedRule.getDescription());
        response.setMemo(updatedRule.getMemo());
        response.setBaseRuleList(updatedRule.getBaseRuleList());
        response.setModifiedDate(updatedRule.getModifiedDate());
        response.setModifiedUser(updatedRule.getModifiedUser());

        return ResponseEntity.ok(new Result(response));

    }

    /**
     * Rule 테이블의 레코드 수정 시 요청을 받아오는 내부 클래스
     * 규칙명, 이관주기, 삭제주기, 설명, 메모 필드와 기본규칙을 요청 받음
     * 규칙명, 이관주기, 삭제주기, 기본규칙은 빈 값을 받을 수 없음
     * 이관주기 및 삭제주기는 최솟값이 0
     *
     */
    @Data
    static class UpdateRuleRequest {
        @NotEmpty
        private String ruleName;
        @Min(0)
        private Long moveCycle;
        @Min(0)
        private Long deleteCycle;
        private String description;
        private String memo;
        @NotEmpty
        private List<String> baseRuleList;
    }

    /**
     * Rule 테이블의 레코드 수정 시 수정된 내용을 다시 반환하는 내부 클래스
     */
    @Data
    static class UpdateRuleResponse {
        private String ruleId;
        private String ruleName;
        private Long moveCycle;
        private Long deleteCycle;
        private String description;
        private String memo;
        private List<String> baseRuleList;
        private LocalDateTime modifiedDate;
        private String modifiedUser;
    }

    /**
     * Rule 테이블의 레코드를 삭제하는 메소드
     * 레코드가 존재하지 않을 경우 404 에러 반환
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteRule(@PathVariable("id") String id) {
        Rule oneById = ruleService.findOneById(id);

        if(oneById == null) {
            return ResponseEntity.notFound().build();
        }

        ruleService.deleteById(id);

        return ResponseEntity.ok().build();
    }

}
