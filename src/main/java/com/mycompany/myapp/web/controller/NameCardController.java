package com.mycompany.myapp.web.controller;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.exception.ResponseMessage;
import com.mycompany.myapp.exception.StatusCode;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.NamecardService;
import com.mycompany.myapp.web.dto.CategoryResponseDto;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import com.mycompany.myapp.web.dto.NamecardResponseDto;
import com.mycompany.myapp.web.dto.UserRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mycompany.myapp.exception.CustomExceptions;
import com.mycompany.myapp.web.controller.base.BaseController;
import com.mycompany.myapp.web.dto.base.DefaultRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity saveNamecard(@RequestPart("image") MultipartFile image,
                                       @RequestPart("request") NamecardRequestDto.CreateNamecardDto request) throws IOException {
        try {
            logger.info("Received request: method={}, path={}, description={}", "POST", "/api/namecard", "Save Namecard API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            namecardService.createNamecard(user, request, image);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.CREATE_NAMECARD_SUCCESS), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "POST OCR API")
    @ApiResponse(code = 200, message = "OCR 요청 성공")
    @PostMapping("/OCR")
    public ResponseEntity saveNamecard(@RequestPart("image") MultipartFile image){
        try {
            logger.info("Received request: method={}, path={}, description={}", "POST", "/api/namecard/OCR", "POST OCR API");

            NamecardResponseDto.OCRResponseDto res = namecardService.postOCR(image);

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

            NamecardResponseDto.NamecardDTO res = namecardService.getNamecard(namecardId);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.GET_NAMECARD_SUCCESS, res), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get Namecard List API")
    @ApiResponse(code = 200, message = "카테고리 별 명함첩 불러오기 성공")
    @GetMapping("")
    public ResponseEntity getNamecardByCategory(@RequestParam("category-id") @ApiParam(value = "카테고리 ID", example = "1") Long categoryId){
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/api/namecard?category-id={category-id}", "Get Namecard List By Category API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            List<NamecardResponseDto.NamecardPreviewDto> res = namecardService.getNamecardByCategory(user, categoryId);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.GET_NAMECARD_LIST_SUCCESS, res), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Search Namecard API")
    @ApiResponse(code = 200, message = "명함 검색하기 성공")
    @GetMapping("/search")
    public ResponseEntity getNamecardBykeyword(@RequestParam("keyword") @ApiParam(value = "검색 키워드", example = "1") String keyword){
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/api/namecard?keyword={keyword}", "Search Namecard API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            List<NamecardResponseDto.NamecardPreviewDto> res = namecardService.searchNamecard(user, keyword);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.SEARCH_NAMECARD_SUCCESS, res), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Delete Namecard API")
    @ApiResponse(code = 200, message = "명함 삭제하기 성공")
    @DeleteMapping("/{namecard-id}")
    public ResponseEntity deleteCategory(@PathVariable("namecard-id") Long namecardId){
        try {
            logger.info("Received request: method={}, path={}, description={}", "DELETE", "/api/namecard/{namecard-id}", "DELETE Namecard API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            namecardService.deleteNamecard(namecardId);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.DELETE_NAMECARD_SUCCESS), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }
}
