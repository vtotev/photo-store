package softuni.photostore.service;

import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;

public interface CommonsService {
    Object[] getSessionUserInfo(SecurityContext context);
}
