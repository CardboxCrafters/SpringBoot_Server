package com.mycompany.myapp.web.controller;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.exception.ResponseMessage;
import com.mycompany.myapp.exception.StatusCode;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.NamecardService;
import com.mycompany.myapp.web.dto.CategoryResponseDto;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import com.mycompany.myapp.web.dto.NamecardResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mycompany.myapp.exception.CustomExceptions;
import com.mycompany.myapp.web.controller.base.BaseController;
import com.mycompany.myapp.web.dto.base.DefaultRes;

import java.util.List;

@Api(tags = "명함첩 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/namecard")
public class NameCardController extends BaseController {
    private final NamecardService namecardService;
    private final UserRepository userRepository;

    @ApiOperation(value = "Save Namecard API")
    @ApiResponse(code = 200, message = "명함 등록 성공")
    @PostMapping("")
    public ResponseEntity saveNamecard(@RequestBody NamecardRequestDto.CreateNamecardDto request){
        try {
            logger.info("Received request: method={}, path={}, description={}", "POST", "/api/namecard", "Save Namecard API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            namecardService.createNamecard(user, request);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.CREATE_NAMECARD_SUCCESS), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "POST OCR API")
    @ApiResponse(code = 200, message = "OCR 요청 성공")
    @PostMapping("/OCR")
    public ResponseEntity saveNamecard(@RequestBody NamecardRequestDto.PostOCRDTO request){
        try {
            logger.info("Received request: method={}, path={}, description={}", "POST", "/api/namecard/OCR", "POST OCR API");

            NamecardResponseDto.OCRResponseDto res = namecardService.postOCR(request);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.POST_OCR_SUCCESS, res), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get Namecard API")
    @ApiResponse(code = 200, message = "명함 불러오기 성공")
    @GetMapping("/{namecard-id}")
    public ResponseEntity getNamecard(@PathVariable("namecard-id") Long namecardId){
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/api/namecard/{namecard-id}", "Get Namecard API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            NamecardResponseDto.getNamecardDTO res = namecardService.getNamecard(namecardId);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.GET_NAMECARD_SUCCESS, res), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }
}
