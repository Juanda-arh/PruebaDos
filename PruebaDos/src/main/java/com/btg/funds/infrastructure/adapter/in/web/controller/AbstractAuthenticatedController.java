package com.btg.funds.infrastructure.adapter.in.web.controller;

import com.btg.funds.infrastructure.security.ClientePrincipal;
import org.springframework.security.core.Authentication;

abstract class AbstractAuthenticatedController {
    protected String currentClienteId(Authentication authentication) {
        return ((ClientePrincipal) authentication.getPrincipal()).getId();
    }
}
