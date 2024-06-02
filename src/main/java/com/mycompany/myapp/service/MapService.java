package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.web.dto.MapResponseDto;

public interface MapService {
    MapResponseDto.LocationDataListDto getLocationData(User user, Double latitude, Double longitude, Long categoryId);
    MapResponseDto.MapNamecardDto getMapNamecard(Long namecardId);
}
