package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import com.mycompany.myapp.web.dto.NamecardResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface NamecardService {
    void createCroppedNamecard(User user, NamecardRequestDto.NamecardDto request, MultipartFile image) throws IOException;
    void createNamecard(User user, NamecardRequestDto.NamecardDto request, MultipartFile image) throws IOException;
    NamecardResponseDto.OCRResponseDto postOCR(MultipartFile request);
    NamecardResponseDto.NamecardDTO getNamecard(Long namecardId);
    List<NamecardResponseDto.NamecardPreviewDto> getNamecardByCategory(User user, Optional<Long> categoryId);
    List<NamecardResponseDto.NamecardPreviewDto> searchNamecard(User user, String keyword);
    void deleteNamecard(Long namecardId);
    void updateNamecard(NamecardRequestDto.NamecardDto request, Long namecardId);
}
