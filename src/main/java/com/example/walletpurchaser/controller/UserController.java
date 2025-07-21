package com.example.walletpurchaser.controller;

import com.example.walletpurchaser.dto.ApiResponse;
import com.example.walletpurchaser.dto.UserRequestDto;
import com.example.walletpurchaser.dto.UserResponseDto;
import com.example.walletpurchaser.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "User created successfully", responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "Users fetched successfully", users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User found", user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
        UserResponseDto updated = userService.updateUser(id, requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "User updated", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User deleted", null));
    }

    @GetMapping("/referral/{code}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserByReferralCode(@PathVariable String code) {
        UserResponseDto user = userService.getUserByReferralCode(code);
        return ResponseEntity.ok(new ApiResponse<>(true, "User found", user));
    }
}
