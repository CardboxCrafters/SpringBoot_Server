package com.mycompany.myapp.web.controller;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.exception.CustomExceptions;
import com.mycompany.myapp.exception.ResponseMessage;
import com.mycompany.myapp.exception.StatusCode;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.MapService;
import com.mycompany.myapp.service.NamecardService;
import com.mycompany.myapp.web.controller.base.BaseController;
import com.mycompany.myapp.web.dto.MapResponseDto;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import com.mycompany.myapp.web.dto.base.DefaultRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "지도 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
public class MapController extends BaseController {
    private final UserRepository userRepository;
    private final MapService mapService;

    @ApiOperation(value = "GET Map API")
    @ApiResponse(code = 200, message = "지도 불러오기 성공")
    @GetMapping("")
    public ResponseEntity getMap(@RequestParam("category-id") @ApiParam(value = "카테고리 ID", example = "1") Long categoryId,@RequestParam("latitude") @ApiParam(value = "사용자 위치(지도 중심)의 위도", example = "37.2431") double latitude, @RequestParam("longitude") @ApiParam(value = "사용자 위치(지도 중심)의 경도", example = "127.0736") double longitude){
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/api/map?category={category-id}&latitude={latitude}&longitude={longitude}", "GET Map API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            MapResponseDto.LocationDataListDto res = mapService.getLocationData(user, latitude, longitude, categoryId);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.GET_MAP_SUCCESS, res), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "GET Map-Namecard By Category API")
    @ApiResponse(code = 200, message = "지도 카테고리 별 명함 불러오기 성공")
    @GetMapping("/{namecard-id}")
    public ResponseEntity getMapNamecardByCategory(@PathVariable("namecard-id") Long namecardId){
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/api/map/{namecard-id}", "GET Map-namecard By Category API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            MapResponseDto.MapNamecardDto res = mapService.getMapNamecard(namecardId);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.GET_MAP_NAMECARD_SUCCESS, res), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

}
