package com.mycompany.myapp.web.dto;

import lombok.*;

public class CategoryResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getCategoryDTO{
        private Long id;
        private String name;
    }
}
