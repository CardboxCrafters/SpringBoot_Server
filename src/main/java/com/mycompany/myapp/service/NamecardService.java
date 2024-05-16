package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import com.mycompany.myapp.web.dto.NamecardResponseDto;

import java.util.List;

public interface NamecardService {
    void createNamecard(User user, NamecardRequestDto.CreateNamecardDto request);
    NamecardResponseDto.OCRResponseDto postOCR(NamecardRequestDto.PostOCRDTO request);
    NamecardResponseDto.NamecardDTO getNamecard(Long namecardId);
    List<NamecardResponseDto.NamecardByCategoryDto> getNamecardByCategory(User user, Long categoryId);
}
