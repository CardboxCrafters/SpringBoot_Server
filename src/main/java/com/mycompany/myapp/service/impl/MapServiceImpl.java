package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.converter.NamecardConverter;
import com.mycompany.myapp.domain.NameCard;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.NamecardRepository;
import com.mycompany.myapp.service.MapService;
import com.mycompany.myapp.web.dto.MapResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private final NamecardRepository namecardRepository;
    private final NamecardConverter namecardConverter;

    @Override
    public MapResponseDto.MapNamecardDto getMapNamecard(Long namecardId){
        NameCard nameCard = namecardRepository.findById(namecardId)
                .orElseThrow(() -> new NoSuchElementException("Namecard not found"));

        return namecardConverter.getMapNamecard(nameCard);
    }
}
