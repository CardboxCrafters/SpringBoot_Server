package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.converter.AddressConverter;
import com.mycompany.myapp.converter.NamecardConverter;
import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.NameCard;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.AddressRepository;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.repository.NamecardRepository;
import com.mycompany.myapp.service.MapService;
import com.mycompany.myapp.web.dto.MapResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private final NamecardRepository namecardRepository;
    private final NamecardConverter namecardConverter;
    private final AddressRepository addressRepository;
    private final AddressConverter addressConverter;
    private final CategoryRepository categoryRepository;

    @Override
    public MapResponseDto.LocationDataListDto getLocationData(User user, Double latitude, Double longitude, Optional<Long> categoryId){
        List<Address> nearbyAddress;
        Double distanceThreshold = 100000.0;

        if (categoryId.isPresent()) {
            Category category = categoryRepository.findById(categoryId.get())
                    .orElseThrow(() -> new NoSuchElementException("Category not found."));

            nearbyAddress = addressRepository.findNearbyAddressByCategoryAndIsUserFalse(latitude,longitude, distanceThreshold, categoryId);
        } else {
            nearbyAddress = addressRepository.findNearbyAddressAndIsUserFalse(latitude,longitude, distanceThreshold);
        }

        return addressConverter.toAddressDataListDto(nearbyAddress);
    }

    @Override
    public MapResponseDto.MapNamecardDto getMapNamecard(Long namecardId){
        NameCard nameCard = namecardRepository.findById(namecardId)
                .orElseThrow(() -> new NoSuchElementException("Namecard not found"));

        return namecardConverter.getMapNamecard(nameCard);
    }
}
