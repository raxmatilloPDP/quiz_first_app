package uz.pdp.quiz_first_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.quiz_first_app.security.JwtUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refresh")
public class RefreshController {

    private final JwtUtil jwtUtil;

    @GetMapping
    public String refreshToken(@RequestParam String refreshToken) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUtil.generateToken(principal, jwtUtil.getLang(refreshToken));
    }

}
