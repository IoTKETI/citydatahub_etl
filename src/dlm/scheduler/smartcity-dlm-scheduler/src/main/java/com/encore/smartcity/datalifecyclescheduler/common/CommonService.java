package com.encore.smartcity.datalifecyclescheduler.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Service 클래스에서 사용하는 공통의 메소드를 정의하기 위한 클래스
 */
public class CommonService {

    /**
     * 테이블의 ID의 날짜와 번호를 계산하여 반환하는 메소드
     * @param baseTableName
     * @param exTableId
     * @return
     */
    public static String getTableId (String baseTableName,
                                         String exTableId) {
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        String[] exTableIds = exTableId.split("_");
        String exTableDate = exTableId.split("_")[exTableIds.length-2];
        String exTableIdNo = exTableId.split("_")[exTableIds.length-1];

        String tableId;

        if(exTableDate.equals(today)) {
            String newTableIdNo = String.valueOf(Integer.parseInt(exTableIdNo) + 1);

            if(newTableIdNo.length() == 1) {
                newTableIdNo = "00" + newTableIdNo;
            } else if(newTableIdNo.length() == 2) {
                newTableIdNo = "0" + newTableIdNo;
            }

            tableId = baseTableName + "_" + today + "_" + newTableIdNo;
        } else {
            tableId = baseTableName + "_" + today + "_" + "001";
        }

        return tableId;
    }

}
