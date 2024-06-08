package com.mycompany.myapp.web.controller;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.exception.CustomExceptions;
import com.mycompany.myapp.exception.ResponseMessage;
import com.mycompany.myapp.exception.StatusCode;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.CategoryService;
import com.mycompany.myapp.service.NamecardService;
import com.mycompany.myapp.web.controller.base.BaseController;
import com.mycompany.myapp.web.dto.CategoryRequestDto;
import com.mycompany.myapp.web.dto.CategoryResponseDto;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import com.mycompany.myapp.web.dto.base.DefaultRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "카테고리 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController extends BaseController {
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    @ApiOperation(value = "Create Category API")
    @ApiResponse(code = 200, message = "카테고리 생성 성공")
    @PostMapping("")
    public ResponseEntity createCategory(@RequestBody CategoryRequestDto.CreateCategoryDto request){
        try {
            logger.info("Received request: method={}, path={}, description={}", "POST", "/api/category", "Create Category API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");
            String categoryName = request.getCategory();

            categoryService.createCategory(user, categoryName);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.CREATE_CATEGORY_SUCCESS), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Delete Category API")
    @ApiResponse(code = 200, message = "카테고리 삭제 성공")
    @DeleteMapping("/{category-id}")
    public ResponseEntity deleteCategory(@PathVariable("category-id") Long categoryId){
        try {
            logger.info("Received request: method={}, path={}, description={}", "DELETE", "/api/category", "DELETE Category API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            categoryService.deleteCategory(categoryId);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.DELETE_CATEGORY_SUCCESS), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get Category API")
    @ApiResponse(code = 200, message = "카테고리 불러오기 성공")
    @GetMapping("")
    public ResponseEntity getCategory(){
        try {
            logger.info("Received request: method={}, path={}, description={}", "GET", "/api/category", "Get Category API");
            User user = userRepository.getByPhoneNumber("010-2944-0386");

            List<CategoryResponseDto.getCategoryDTO> res = categoryService.getCategory(user);

            return new ResponseEntity( DefaultRes.res(StatusCode.OK, ResponseMessage.GET_CATEGORY_SUCCESS, res), HttpStatus.OK);
        } catch (CustomExceptions.testException e) {
            return handleApiException(e, HttpStatus.BAD_REQUEST);
        }
    }
}
