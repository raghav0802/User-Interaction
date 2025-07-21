package com.example.walletpurchaser.service.impl;

import com.example.walletpurchaser.dto.UserRequestDto;
import com.example.walletpurchaser.dto.UserResponseDto;
import com.example.walletpurchaser.exception.ResourceNotFoundException;
import com.example.model.User;
import com.example.walletpurchaser.Repository.UserRepository;
import com.example.walletpurchaser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = User.builder()
                .username(requestDto.getName())
                .email(requestDto.getEmail())
                .referralCode(generateReferralCode())
                .build();

        User saved = userRepository.save(user);
        return toDto(saved);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return toDto(user);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setUsername(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        User updated = userRepository.save(user);

        return toDto(updated);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDto getUserByReferralCode(String referralCode) {
        User user = userRepository.findByReferralCode(referralCode)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with referral code"));
        return toDto(user);
    }

    private UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .referralCode(user.getReferralCode())
                .build();
    }

    private String generateReferralCode() {
        return UUID.randomUUID().toString().substring(0, 8); // simple random code
    }
}