package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.web.dto.NamecardRequestDto;

public interface NamecardService {
    void createCategory(User user, String name);
    void createNamecard(User user, NamecardRequestDto.CreateNamecardDto request);
}
