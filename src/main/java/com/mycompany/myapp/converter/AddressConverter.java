package com.mycompany.myapp.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.NameCard;
import com.mycompany.myapp.web.dto.MapResponseDto;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AddressConverter {
    private final RestTemplate restTemplate;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    public MapResponseDto.LocationDataListDto toAddressDataListDto(List<Address> nearbyAddress){
        return MapResponseDto.LocationDataListDto.builder()
                .LocationData(this.toLocationDataDtoList(nearbyAddress))
                .build();
    }

    public List<MapResponseDto.LocationDataListDto.LocationDataDto> toLocationDataDtoList(List<Address> nearbyLocations) {
        return nearbyLocations.stream()
                .map(this::toLocationDataDto)
                .collect(Collectors.toList());
    }

    public MapResponseDto.LocationDataListDto.LocationDataDto toLocationDataDto(Address address) {
        return MapResponseDto.LocationDataListDto.LocationDataDto.builder()
                .namecardId(address.getNameCard().getId())
                .point(MapResponseDto.LocationDataListDto.PointDto.builder()
                        .latitude(address.getLatitude())
                        .longitude(address.getLongitude())
                        .build())
                .build();
    }

    public Address createAddress(NamecardRequestDto.CreateNamecardDto request, NameCard namecard){
        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + request.getAddress();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String res = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(res);
            JsonNode documents = root.path("documents");
            if (documents.isArray() && documents.size() > 0) {
                JsonNode firstDocument = documents.get(0);
                Double latitude = firstDocument.path("y").asDouble();
                Double longitude = firstDocument.path("x").asDouble();

                return Address.builder()
                        .nameCard(namecard)
                        .latitude(latitude)
                        .longitude(longitude)
                        .build();
            } else {
                throw new NoSuchElementException("No coordinates found for the given address.");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON response.");
        }
    }
}
