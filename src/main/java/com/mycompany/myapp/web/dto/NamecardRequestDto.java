package com.mycompany.myapp.web.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;

public class NamecardRequestDto {

    @Getter
    public static class OCRRequestDto{
        @ApiModelProperty(example = "base64 encoding image")
        @ApiParam(name = "base64 encoding image", value = "base64 encoding image", required = true)
        private String base64Image;
    }

    @Getter
    public static class CreateCategoryDto{
        @ApiModelProperty(example = "친구")
        @ApiParam(name = "category", value = "카테고리 이름 입력", required = true)
        private String category;
    }

    @Getter
    public static class CreateNamecardDto{
        @ApiModelProperty(example = "최은지")
        @ApiParam(name = "name", value = "이름 입력", required = true)
        private String name;

        @ApiModelProperty(example = "010-1234-5678")
        @ApiParam(name = "mobile", value = "휴대폰 번호 입력", required = true)
        private String mobile;

        @ApiModelProperty(example = "example@gmail.com")
        @ApiParam(name = "email", value = "메일 주소 입력", required = true)
        private String email;

        @ApiModelProperty(example = "네이버")
        @ApiParam(name = "company", value = "회사 이름 입력", required = true)
        private String company;

        @ApiModelProperty(example = "마케팅부")
        @ApiParam(name = "department", value = "부서 입력", required = true)
        private String department;

        @ApiModelProperty(example = "사원")
        @ApiParam(name = "position", value = "직급 입력", required = true)
        private String position;

        @ApiModelProperty(example = "042-123-4567")
        @ApiParam(name = "tel", value = "유선 전화번호 입력", required = true)
        private String tel;

        @ApiModelProperty(example = "02-1234-5678")
        @ApiParam(name = "fax", value = "팩스 번호 입력", required = true)
        private String fax;

        @ApiModelProperty(example = "example.co.kr")
        @ApiParam(name = "homepage", value = "홈페이지 주소 입력", required = true)
        private String homepage;

        @ApiModelProperty(example = "용인시 기흥구 서천동 387-4")
        @ApiParam(name = "address", value = "주소 입력", required = true)
        private String address;

        @ApiModelProperty(example = "1")
        @ApiParam(name = "categoryId", value = "카테고리 ID 입력", required = true)
        private Long categoryId;
    }
}
