package com.encore.smartcity.datalifecyclecrud.baserules;

import com.encore.smartcity.datalifecyclecrud.common.CommonController;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
 * BaseRule 테이블의 CRUD 및 관련 메소드를 정의한 클래스
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/baseRule")
public class BaseRuleController {

    private final BaseRuleService baseRuleService;

    /**
     * BaseRule 테이블의 레코드를 생성하는 메소드
     * 기본규칙명, 디폴트이관주기, 디폴트삭제주기, 설명, 메모 필드를 요청 받음
     * 기본규칙명, 디폴트이관주기, 디폴트삭제주기는 빈 값을 받을 수 없음
     * 기본규칙명은 중복된 값을 받을 수 없음
     * 디폴트삭제주기는 디폴트이관주기보다 짧을 수 없음
     * 결과를 json 형태로 반환
     *
     * @param request
     * @param errors
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    public ResponseEntity createBaseRule(@RequestBody @Valid BaseRuleRequest request,
                                         Errors errors) throws URISyntaxException {
        if(errors.hasErrors()) {
            return CommonController.badRequest(errors);
        }

        validateCheck(request, errors);
        if(errors.hasErrors()) {
            return CommonController.badRequest(errors);
        }

        if(!baseRuleService.checkDuplicateBaseRule(request.getBaseRuleName())) {
            return CommonController.badRequest(errors);
        }

        BaseRule baseRule = new BaseRule();
        baseRule.setBaseRuleName(request.getBaseRuleName());
        baseRule.setDefaultMoveCycle(request.getDefaultMoveCycle());
        baseRule.setDefaultDeleteCycle(request.getDefaultDeleteCycle());
        baseRule.setDescription(request.getDescription());
        baseRule.setMemo(request.getMemo());

        BaseRule joinedBaseRule = baseRuleService.join(baseRule);

        URI location = new URI("/api/baseRule/" + joinedBaseRule.getBaseRuleId());
        return ResponseEntity.created(location).body(joinedBaseRule);
    }

    /**
     * BaseRule 테이블의 레코드 생성 시 요청을 받아오는 내부 클래스
     * 기본규칙명, 디폴트이관주기, 디폴트삭제주기, 설명, 메모 필드를 요청 받음
     * 기본규칙명, 디폴트이관주기, 디폴트삭제주기는 빈 값을 받을 수 없음
     * 디폴트이관주기 및 디폴트삭제주기는 최솟값이 0
     *
     */
    @Data
    @Builder
    static class BaseRuleRequest {

        @NotEmpty
        private String baseRuleName;

        @Min(0)
        private Long defaultMoveCycle;

        @Min(0)
        private Long defaultDeleteCycle;

        private String description;

        private String memo;
    }

    /**
     * 디폴트이관주기와 디폴트삭제주기 길이를 체크하는 메소드
     * 디폴트삭제주기는 디폴트이관주기보다 짧을 수 없음
     *
     * @param request
     * @param errors
     */
    private void validateCheck(BaseRuleRequest request, Errors errors) {
        if(request.getDefaultMoveCycle() > request.getDefaultDeleteCycle()) {
            errors.rejectValue("defaultMoveCycle", "wrongValue", "이동 기간은 삭제 기간보다 길 수 없습니다.");
            errors.rejectValue("defaultDeleteCycle", "wrongValue", "이동 기간은 삭제 기간보다 길 수 없습니다.");
        }
    }

    /**
     * BaseRule 테이블의 전체목록을 조회하는 메소드
     * json 형태로 반환
     * @return
     */
    @GetMapping
    public ResponseEntity findBaseRules() {
        List<BaseRule> baseRules = baseRuleService.findBaseRules();
        List<BaseRuleDto> collect = baseRules.stream()
                .map(m -> new BaseRuleDto(
                        m.getBaseRuleId(),
                        m.getBaseRuleName(),
                        m.getDefaultMoveCycle(),
                        m.getDefaultDeleteCycle(),
                        m.getDescription(),
                        m.getMemo(),
                        m.getCreatedDate(),
                        m.getCreatedUser(),
                        m.getModifiedDate(),
                        m.getModifiedUser()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new Result(collect));
    }

    /**
     * BaseRule 테이블의 상세내용을 조회하는 메소드
     * 레코드가 존재하지 않을 경우 404 에러 반환
     * 결과는 json 형태로 반환
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity findBaseRuleById(@PathVariable("id") String id) {
        BaseRule baseRule = baseRuleService.findOneById(id);
        if(baseRule == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new Result(baseRule));
    }

    /**
     * BaseRule 테이블의 조회 메소드 반환 시 json 형태로 반환하기 위한 내부 클래스
     *
     * @param <T>
     */
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    /**
     * BaseRule 테이블의 레코드를 수정하는 메소드
     * 기본규칙명, 디폴트이관주기, 디폴트삭제주기, 설명, 메모 필드를 요청 받음
     * 기본규칙명, 디폴트이관주기, 디폴트삭제주기는 빈 값을 받을 수 없음
     * 기본규칙명은 수정 대상 레코드의 기본규칙명을 제외한 타 레코드와 중복된 값을 받을 수 없음
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
    public ResponseEntity updateBaseRule(@PathVariable("id") String id,
                                         @RequestBody @Valid BaseRuleRequest request,
                                         Errors errors) {

        BaseRule oneById = baseRuleService.findOneById(id);
        if(oneById == null) {
            return ResponseEntity.notFound().build();
        }

        if(errors.hasErrors()) {
            return CommonController.badRequest(errors);
        }

        validateCheck(request, errors);
        if(errors.hasErrors()) {
            return CommonController.badRequest(errors);
        }

        if(!oneById.getBaseRuleName().equals(request.getBaseRuleName()) &&
                !baseRuleService.checkDuplicateBaseRule(request.getBaseRuleName())) {
            return CommonController.badRequest(errors);
        }

        BaseRuleDto baseRuleDto = new BaseRuleDto();
        baseRuleDto.setBaseRuleName(request.getBaseRuleName());
        baseRuleDto.setDefaultMoveCycle(request.getDefaultMoveCycle());
        baseRuleDto.setDefaultDeleteCycle(request.getDefaultDeleteCycle());
        baseRuleDto.setDescription(request.getDescription());
        baseRuleDto.setMemo(request.getMemo());

        BaseRule updatedBaseRule = baseRuleService.update(id, baseRuleDto);

        UpdateBaseRuleResponse response = new UpdateBaseRuleResponse();
        response.setBaseRuleId(updatedBaseRule.getBaseRuleId());
        response.setBaseRuleName(updatedBaseRule.getBaseRuleName());
        response.setDefaultMoveCycle(updatedBaseRule.getDefaultMoveCycle());
        response.setDefaultDeleteCycle(updatedBaseRule.getDefaultDeleteCycle());
        response.setDescription(updatedBaseRule.getDescription());
        response.setMemo(updatedBaseRule.getMemo());
        response.setModifiedDate(updatedBaseRule.getModifiedDate());
        response.setModifiedUser(updatedBaseRule.getModifiedUser());

        return ResponseEntity.ok(new Result(response));
    }

    /**
     * BaseRule 테이블의 레코드 수정 시 요청을 받아오는 내부 클래스
     * 기본규칙명, 디폴트이관주기, 디폴트삭제주기, 설명, 메모 필드를 요청 받음
     * 기본규칙명, 디폴트이관주기, 디폴트삭제주기는 빈 값을 받을 수 없음
     * 디폴트이관주기 및 디폴트삭제주기는 최솟값이 0
     *
     */
    @Data
    static class UpdateBaseRuleRequest {
        @NotEmpty
        private String baseRuleName;
        @Min(0)
        private Long defaultMoveCycle;
        @Min(0)
        private Long defaultDeleteCycle;
        private String description;
        private String memo;
    }

    /**
     * BaseRule 테이블의 레코드 수정 시 수정된 내용을 다시 반환하는 내부 클래스
     */
    @Data
    static class UpdateBaseRuleResponse {
        private String baseRuleId;
        private String baseRuleName;
        private Long defaultMoveCycle;
        private Long defaultDeleteCycle;
        private String description;
        private String memo;
        private LocalDateTime modifiedDate;
        private String modifiedUser;

    }

    /**
     * BaseRule 테이블의 레코드를 삭제하는 메소드
     * 레코드가 존재하지 않을 경우 404 에러 반환
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity deleteBaseRulesById(@PathVariable("id") String id) {
        BaseRule oneById = baseRuleService.findOneById(id);

        if(oneById == null) {
            return ResponseEntity.notFound().build();
        }

        baseRuleService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
