package com.Daniel_Muntean4.pomodoro_api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth", description = "Get a JWT for the API")
public class TokenController {
    private final JwtService jwtService;


    public TokenController(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    @PostMapping("/token")
    public TokenResponse  token(@RequestBody TokenRequest tokenRequest){
        String subject = (tokenRequest.subject()==null) ?
                "demo-user" : tokenRequest.subject();
        return new TokenResponse(jwtService.
                generateToken(subject,tokenRequest.permissions()));
    }

}
