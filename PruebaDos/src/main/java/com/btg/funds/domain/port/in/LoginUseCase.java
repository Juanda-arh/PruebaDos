package com.btg.funds.domain.port.in;

import com.btg.funds.domain.model.AuthResult;

public interface LoginUseCase {
    AuthResult login(String email, String password);
}
