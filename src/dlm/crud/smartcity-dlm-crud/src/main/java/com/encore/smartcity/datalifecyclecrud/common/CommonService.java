package com.encore.smartcity.datalifecyclecrud.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Service 클래스에서 사용하는 공통의 메소드를 정의하기 위한 클래스
 */
public class CommonService {


    /**
     * 이전 테이블의 마지막 번호를 구하는 메소드
     * @param exTableId
     * @return
     */
    public static Long getTableIdCount(String exTableId) {
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        String[] exTableIds = exTableId.split("_");
        String exTableDate = exTableId.split("_")[exTableIds.length-2];
        System.out.println("exTableDate");
        System.out.println(exTableDate);
        String exTableIdNo = exTableId.split("_")[exTableIds.length-1];

        if(exTableDate.equals(today)) {
            return Long.valueOf(exTableIdNo);

        } else {
            return 1L;
        }
    }

    /**
     * 테이블의 ID의 날짜와 번호를 계산하여 반환하는 메소드
     * @param baseTableName
     * @param exTableId
     * @param exIdNo
     * @return
     */
    public static String getTableId(String baseTableName,
                                    String exTableId,
                                    Long exIdNo) {
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        String[] exTableIds = exTableId.split("_");
        String exTableDate = exTableId.split("_")[exTableIds.length-2];

        String tableId;

        System.out.println("=============================");
        System.out.println("exIdNo : " + exIdNo);
        exIdNo++;
        if(exTableDate.equals(today)) {
            String newTableIdNo = String.valueOf(exIdNo);

            if(newTableIdNo.length() == 1) {
                newTableIdNo = "00" + newTableIdNo;
            } else if(newTableIdNo.length() == 2) {
                newTableIdNo = "0" + newTableIdNo;
            }

            tableId = baseTableName + "_" + today + "_" + newTableIdNo;
        } else {
            tableId = baseTableName + "_" + today + "_" + "001";
        }

        System.out.println("tableId : " + tableId);
        return tableId;
    }

    /**
     * 테이블의 ID의 날짜와 번호를 계산하여 반환하는 메소드
     * @param baseTableName
     * @param exTableId
     * @param exIdNo
     * @return
     */
    public static String getUpdateTableId(String baseTableName,
                                            String exTableId,
                                            Long exIdNo) {
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        String[] exTableIds = exTableId.split("_");
        String exTableDate = exTableId.split("_")[exTableIds.length-2];

        String tableId;

        System.out.println("=============================");
        System.out.println("exIdNo : " + exIdNo);
        if(exTableDate.equals(today)) {
            exIdNo++;
            String newTableIdNo = String.valueOf(exIdNo);

            if(newTableIdNo.length() == 1) {
                newTableIdNo = "00" + newTableIdNo;
            } else if(newTableIdNo.length() == 2) {
                newTableIdNo = "0" + newTableIdNo;
            }

            tableId = baseTableName + "_" + today + "_" + newTableIdNo;
        } else {
            tableId = baseTableName + "_" + today + "_00" + exIdNo;
        }

        System.out.println("tableId : " + tableId);
        return tableId;
    }

}
