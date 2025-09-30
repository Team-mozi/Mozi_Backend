package com.mozi.domain.user.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NicknameExistsResponse {
    private boolean exists;
}