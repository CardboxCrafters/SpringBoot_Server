package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.converter.NamecardConverter;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.NameCard;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.repository.NamecardRepository;
import com.mycompany.myapp.service.NamecardService;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import com.mycompany.myapp.web.dto.NamecardResponseDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NamecardServiceImpl implements NamecardService {
    private final RestTemplate restTemplate;
    private final NamecardConverter namecardConverter;
    private final NamecardRepository namecardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void createNamecard(User user, NamecardRequestDto.CreateNamecardDto request){
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        NameCard nameCard = namecardConverter.createNamecard(user, request, category);
        namecardRepository.save(nameCard);
    }

    @Override
    public NamecardResponseDto.OCRResponseDto postOCR(NamecardRequestDto.PostOCRDTO request){
        String base64Image = request.getBase64Image();

        // 요청 생성
        JSONObject requestBody = new JSONObject();
        JSONArray imagesArray = new JSONArray();
        JSONObject imageObject = new JSONObject();
        imageObject.put("format", "jpg");
        imageObject.put("name", "demo");
        imageObject.put("data", base64Image);
        imagesArray.put(imageObject);
        requestBody.put("images", imagesArray);
        requestBody.put("lang", "ko");
        requestBody.put("requestId", "string");
        requestBody.put("resultType", "string");
        requestBody.put("timestamp", System.currentTimeMillis()); // 현재 시간으로 timestamp 설정
        requestBody.put("version", "V2");

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-OCR-SECRET", "aEtSUkdmd0lEckpud3F2UkNZamx5d0V3T1BqTmphRVk=");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody.toString(), headers);

        // OCR 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://w7bm6awy9y.apigw.ntruss.com/custom/v1/30248/2d33f4f160220f8e6136123b6849d1595b9bb354afcbbaf23f263a98b335ac0c/document/name-card",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        try {
            return namecardConverter.postOCR(responseEntity.getBody());
        } catch (IOException e) {
            // IOException 처리 로직 추가
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public NamecardResponseDto.getNamecardDTO getNamecard(Long namecardId){
        NameCard namecard = namecardRepository.findById(namecardId)
                .orElseThrow(() -> new NoSuchElementException("Namecard not found."));

        return namecardConverter.getNamecard(namecard);
    }
}
