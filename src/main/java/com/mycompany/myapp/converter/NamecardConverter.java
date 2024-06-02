package com.mycompany.myapp.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.NameCard;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.web.dto.MapResponseDto;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import com.mycompany.myapp.web.dto.NamecardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class NamecardConverter {

    public Category createCategory(User user, String name){
        return Category.builder()
                .user(user)
                .name(name)
                .build();
    }

    public NameCard createNamecard(User user, NamecardRequestDto.CreateNamecardDto request, Category category){
        return NameCard.builder()
                .user(user)
                .name(request.getName())
                .mobile(request.getMobile())
                .email(request.getEmail())
                .company(request.getCompany())
                .department(request.getDepartment())
                .position(request.getPosition())
                .tel(request.getTel())
                .fax(request.getFax())
                .homepage(request.getHomepage())
                .address(request.getAddress())
                .category(category)
                .isUser(false)
                .build();
    }

    public NamecardResponseDto.OCRResponseDto postOCR(String body) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(body);

        JsonNode nameNode = rootNode.at("/images/0/nameCard/result/name/0/text");
        String name = nameNode.asText();

        JsonNode mobileNode = rootNode.at("/images/0/nameCard/result/mobile/0/text");
        String mobile = mobileNode.asText();

        JsonNode emailNode = rootNode.at("/images/0/nameCard/result/email/0/text");
        String email = emailNode.asText();

        JsonNode companyNode = rootNode.at("/images/0/nameCard/result/company/0/text");
        String company = companyNode.asText();

        JsonNode departmentNode = rootNode.at("/images/0/nameCard/result/department/0/text");
        String department = departmentNode.asText();

        JsonNode positionNode = rootNode.at("/images/0/nameCard/result/position/0/text");
        String position = positionNode.asText();

        JsonNode telNode = rootNode.at("/images/0/nameCard/result/tel/0/text");
        String tel = telNode.asText();

        JsonNode faxNode = rootNode.at("/images/0/nameCard/result/fax/0/text");
        String fax = faxNode.asText();

        JsonNode homepageNode = rootNode.at("/images/0/nameCard/result/homepage/0/text");
        String homepage = homepageNode.asText();

        JsonNode addressNode = rootNode.at("/images/0/nameCard/result/address/0/text");
        String address = addressNode.asText();

        return NamecardResponseDto.OCRResponseDto.builder()
                .name(name)
                .mobile(mobile)
                .email(email)
                .company(company)
                .department(department)
                .position(position)
                .tel(tel)
                .fax(fax)
                .homepage(homepage)
                .address(address)
                .build();
    }

    public NamecardResponseDto.NamecardDTO getNamecard(NameCard nameCard){
        return NamecardResponseDto.NamecardDTO.builder()
                .name(nameCard.getName())
                .mobile(nameCard.getMobile())
                .email(nameCard.getEmail())
                .company(nameCard.getCompany())
                .department(nameCard.getDepartment())
                .position(nameCard.getPosition())
                .tel(nameCard.getTel())
                .address(nameCard.getAddress())
                .fax(nameCard.getFax())
                .homepage(nameCard.getHomepage())
                .categoryName(nameCard.getCategory().getName())
                .build();
    }

    public MapResponseDto.MapNamecardDto getMapNamecard(NameCard nameCard){
        return MapResponseDto.MapNamecardDto.builder()
                .namecardId(nameCard.getId())
                .name(nameCard.getName())
                .position(nameCard.getPosition())
                .department(nameCard.getDepartment())
                .company(nameCard.getCompany())
                .namecardUrl(nameCard.getUrl())
                .build();
    }

    public NamecardResponseDto.NamecardByCategoryDto toNamecardPage(NameCard namecard){
        return NamecardResponseDto.NamecardByCategoryDto.builder()
                .namecardId(namecard.getId())
                .name(namecard.getName())
                .position(namecard.getPosition())
                .department(namecard.getDepartment())
                .company(namecard.getCompany())
                .namecardUrl(namecard.getUrl())
                .build();
    }
}
