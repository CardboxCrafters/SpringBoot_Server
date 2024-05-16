package com.mycompany.myapp.web.dto;

import lombok.*;

public class UserResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserDto{
        private String name;
        private String company;
        private String department;
        private String position;
        private String mobile;
        private String email;
        private String tel;
        private String fax;
        private String homepage;
        private String address;
    }
}
