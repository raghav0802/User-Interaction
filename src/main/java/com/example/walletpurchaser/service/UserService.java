package com.example.walletpurchaser.service;

import com.example.walletpurchaser.dto.UserRequestDto;
import com.example.walletpurchaser.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Long id);
    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
    void deleteUser(Long id);
    UserResponseDto getUserByReferralCode(String referralCode);
}
