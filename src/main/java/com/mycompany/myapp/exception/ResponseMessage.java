package com.mycompany.myapp.exception;

public class ResponseMessage {

    //test
    public static final String TEST_SUCCESS = "테스트 성공";

    //namecard
    public static final String POST_OCR_SUCCESS = "OCR 요청 성공";
    public static final String CREATE_NAMECARD_SUCCESS = "명함 등록 성공";
    public static final String GET_NAMECARD_SUCCESS = "명함 불러오기 성공";
    public static final String GET_NAMECARD_LIST_SUCCESS = "카테고리 별 명함첩 불러오기 성공";
    public static final String SEARCH_NAMECARD_SUCCESS = "명함 검색하기 성공";
    public static final String DELETE_NAMECARD_SUCCESS = "명함 삭제하기 성공";
    public static final String UPDATE_NAMECARD_SUCCESS = "명함 수정하기 성공";

    //category
    public static final String CREATE_CATEGORY_SUCCESS = "카테고리 생성 성공";
    public static final String DELETE_CATEGORY_SUCCESS = "카테고리 삭제 성공";
    public static final String GET_CATEGORY_SUCCESS = "카테고리 불러오기 성공";

    //map
    public static final String GET_MAP_SUCCESS = "지도 불러오기 성공";
    public static final String GET_MAP_NAMECARD_SUCCESS = "지도 명함 불러오기 성공";

    //user
    public static final String GET_USER_SUCCESS = "내 정보 불러오기 성공";
    public static final String UPDATE_USER_SUCCESS = "내 정보 수정하기 성공";
    public static final String WITHDRAW_USER_SUCCESS = "회원 탈퇴 성공";
    public static final String SEND_CERTIFICATION_SUCCESS = "SMS 인증 문자 전송 성공";
    public static final String CONFIRM_CERTIFICATION_SUCCESS = "SMS 문자 인증 성공";
    public static final String REISSUE_ACCESS_TOKEN_SUCCESS = "Access Toekn 재발급 성공";
}
