package com.mycompany.myapp.web.dto;

import lombok.*;

import java.util.List;

public class MapResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LocationDataListDto{
        private List<LocationDataDto> LocationData;

        @Builder
        @Getter
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class LocationDataDto{
            private Long namecardId;
            private PointDto point;}

        @Builder
        @Getter
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class PointDto{
            private Double latitude;
            private Double longitude;
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MapNamecardDto{
        private Long namecardId;
        private String name;
        private String position;
        private String department;
        private String company;
        private String namecardUrl;
    }
}
