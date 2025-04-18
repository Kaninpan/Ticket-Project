package ticket.backend.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthen implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("Admin")) {  // ใช้บทบาทที่เก็บในฐานข้อมูล
                response.sendRedirect("/dashboard");
                return;
            } else if (role.equals("User")) {  // ใช้บทบาทที่เก็บในฐานข้อมูล
                response.sendRedirect("/home");
                return;
            }
        }

        // fallback
        response.sendRedirect("/officer?error=unauthorized");
    }
}
