package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.converter.CategoryConverter;
import com.mycompany.myapp.converter.NamecardConverter;
import com.mycompany.myapp.converter.UserConverter;
import com.mycompany.myapp.dao.SmsCertificationDao;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.NameCard;
import com.mycompany.myapp.domain.RefreshToken;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.enums.UserStatus;
import com.mycompany.myapp.exception.CustomExceptions;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.repository.NamecardRepository;
import com.mycompany.myapp.repository.RefreshTokenRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.util.JwtUtil;
import com.mycompany.myapp.util.SmsCertificationUtil;
import com.mycompany.myapp.web.controller.UserController;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import com.mycompany.myapp.web.dto.UserRequestDto;
import com.mycompany.myapp.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final NamecardRepository namecardRepository;
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final SmsCertificationUtil smsUtil;
    private final SmsCertificationDao smsCertificationDao;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CategoryRepository categoryRepository;
    private final NamecardConverter namecardConverter;
    private final CategoryConverter categoryConverter;

    @Override
    public UserResponseDto.UserDto getUser(User user){
        NameCard namecard = namecardRepository.findByUserAndIsUserTrue(user);

        return userConverter.getUser(namecard);
    }

    @Override
    @Transactional
    public void updateUser(User user, UserRequestDto.UpdateUserDto request){
        NameCard namecard = namecardRepository.findByUserAndIsUserTrue(user);
        namecard.updateNamecard(request);
    }

    @Override
    @Transactional
    public void withdrawUser(User user){
        user.withdrawUser();
        userRepository.save(user);
    }

    @Override
    public void sendSms(UserRequestDto.SendSmsCertificationDto request){
        String to = request.getPhone();
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        String certificationNumber = String.valueOf(randomNumber);
        smsUtil.sendSms(to, certificationNumber);
        smsCertificationDao.createSmsCertification(to, certificationNumber);
    }

    @Override
    @Transactional
    public Long verifyAndRegisterUser(UserRequestDto.ConfirmSmsCertificationDto request){
        verifySms(request);

        User user = userRepository.findByPhoneNumber(request.getPhone());

        boolean isNewUser = user == null;

        if (isNewUser) {
            System.out.println("new");
            return registerUser(request);
        } else if (user.getStatus() == UserStatus.INACTIVE) {
            System.out.println("withdraw");
            return registerWithdrawUser(request);
        } else {
            System.out.println("?");
            return loadUserByPhoneNumber(request.getPhone());
        }
    }

    @Override
    @Transactional
    public void saveRefreshToken(String token){
        Date tokenExpTime = jwtUtil.extractExpiration(token);
        Long userId = jwtUtil.extractId(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));;

        RefreshToken refreshToken = userConverter.saveRefreshToken(user, token, tokenExpTime);
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public String reissueAccessToken(User user){
        RefreshToken token = refreshTokenRepository.findByUser(user);
        if(jwtUtil.validateToken(token.getToken(), user))
            return jwtUtil.generateAccessToken(user.getId());
        else
            throw new CustomExceptions.Exception("refreshToken이 만료되었습니다.");
    }

    private void verifySms(UserRequestDto.ConfirmSmsCertificationDto request) {
        if (isVerify(request)) {
            throw new CustomExceptions.Exception("인증번호가 일치하지 않습니다.");
        }
        smsCertificationDao.removeSmsCertification(request.getPhone());
    }

    private boolean isVerify(UserRequestDto.ConfirmSmsCertificationDto request) {
        return !(smsCertificationDao.hasKey(request.getPhone()) &&
                smsCertificationDao.getSmsCertification(request.getPhone())
                        .equals(request.getCertificationNumber()));
    }

    private Long registerUser(UserRequestDto.ConfirmSmsCertificationDto request) {
        if (isVerify(request)) {
            User existingUser = userRepository.findByPhoneNumber(request.getPhone());
            if (existingUser == null) {
                String name = request.getName();
                String phone = request.getPhone();

                User user = userConverter.registerUser(name, phone);
                userRepository.save(user);

                NameCard nameCard = namecardConverter.registerUser(name, phone, user);
                namecardRepository.save(nameCard);

                Category allCategory = categoryConverter.registerUser(user);
                categoryRepository.save(allCategory);

                return user.getId();
            }
        }
        throw new CustomExceptions.Exception("회원가입을 할 수 없습니다.");
    }

    private Long registerWithdrawUser(UserRequestDto.ConfirmSmsCertificationDto request) {
        if (isVerify(request)) {
            User existingUser = userRepository.findByPhoneNumber(request.getPhone());
            if (existingUser != null && existingUser.getStatus() == UserStatus.INACTIVE) {
                existingUser.resetUser(request.getName(), request.getPhone());
                userRepository.save(existingUser);
                return existingUser.getId();
            }
        }
        throw new CustomExceptions.Exception("탈퇴한 유저를 되돌릴 수 없습니다.");
    }

    public Long loadUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new CustomExceptions.Exception("전화번호 " + phoneNumber + "를 사용하는 사용자가 없습니다.");
        }
        return user.getId();
    }

}
