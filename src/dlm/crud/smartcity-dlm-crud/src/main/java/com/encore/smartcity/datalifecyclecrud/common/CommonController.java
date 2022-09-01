package com.encore.smartcity.datalifecyclecrud.common;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

/**
 * Controller 클래스에서 사용하는 공통 메소드를 정의하기 위한 클래스
 */
public class CommonController {

    /**
     * 잘못된 요청을 받았을 때 Bad Request 에러(400)를 반환하는 메소드
     * @param errors
     * @return
     */
    public static ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body("Bad Request");
    }
}
