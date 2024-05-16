package com.mycompany.myapp.web.controller;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.exception.CustomExceptions;
import com.mycompany.myapp.exception.ResponseMessage;
import com.mycompany.myapp.exception.StatusCode;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.web.controller.base.BaseController;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import com.mycompany.myapp.web.dto.NamecardResponseDto;
import com.mycompany.myapp.web.dto.UserRequestDto;
import com.mycompany.myapp.web.dto.UserResponseDto;
import com.mycompany.myapp.web.dto.base.DefaultRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Api(tags = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController extends BaseController {

    private final UserRepository userRepository;
    private final UserService userService;

    @ApiOperation(value = "Get User API")
    @ApiResponse(code = 200, message = "내 정보 불러오기 성공")
    @GetMapping("")
    public ResponseEntity getUser(){
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/api/user", "Get User API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            UserResponseDto.UserDto res = userService.getUser(user);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.GET_USER_SUCCESS, res), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Update User API")
    @ApiResponse(code = 200, message = "내 정보 수정하기 성공")
    @PutMapping("")
    public ResponseEntity updateUser(@RequestBody UserRequestDto.UpdateUserDto request){
        try {
            logger.info("Received request: method={}, path={}, description={}", "PUT", "/api/user", "Update User API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            userService.updateUser(user, request);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_USER_SUCCESS), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Withdraw User API")
    @ApiResponse(code = 200, message = "회원 탈퇴 성공")
    @PatchMapping("")
    public ResponseEntity withdrawUser(){
        try {
            logger.info("Received request: method={}, path={}, description={}", "PATCH", "/api/user", "Withdraw User API");
            User user = userRepository.getByPhoneNumber("010-3020-0386");

            userService.withdrawUser(user);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.WITHDRAW_USER_SUCCESS), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }
}
