package com.mycompany.myapp.web.dto;

import lombok.*;

public class CategoryResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class getCategoryDTO{
        private Long id;
        private String name;
    }
}
