package com.mycompany.myapp.util;

import com.mycompany.myapp.converter.NamecardConverter;
import com.mycompany.myapp.web.dto.NamecardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.json.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class HttpRequestUtil {
    private final NamecardConverter namecardConverter;

    @Value("${ocr.secret}")
    private String ocrSecret;

    @Value("${ocr.url}")
    private String ocrUrl;

    public NamecardResponseDto.OCRResponseDto postOCR(String base64Image) {
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
        headers.add("X-OCR-SECRET", ocrSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody.toString(), headers);

        // OCR 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                ocrUrl,
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

    public static byte[] cropImage(MultipartFile image) {
        // 이미지 파일을 MultipartFile로 받아와서 image 변수에 저장

        // OCR 서버로 보낼 요청 데이터 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", image.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // fastAPI 서버 URL
        String ocrUrl = "http://cardbox.shop:8000/cropImage";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                ocrUrl,
                HttpMethod.POST,
                requestEntity,
                byte[].class
        );

        return responseEntity.getBody();
    }
}
