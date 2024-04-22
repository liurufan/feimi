package org.basic.feimi.domain.controller;

import org.basic.feimi.domain.body.LoginBody;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/login")
public class LoginController {
    @PostMapping
    public void login(@RequestBody @Valid LoginBody loginBody) {}
}
