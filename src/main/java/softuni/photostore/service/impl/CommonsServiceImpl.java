package softuni.photostore.service.impl;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import softuni.photostore.model.entity.accounts.User;
import softuni.photostore.service.CommonsService;
import softuni.photostore.service.UsersService;

@Service
public class CommonsServiceImpl implements CommonsService {
    private final UsersService usersService;

    public CommonsServiceImpl(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public Object[] getSessionUserInfo(SecurityContext context) {
        User user = null;
        String remoteIP = "";
        Authentication authentication = context.getAuthentication();
        if (authentication != null) {
            AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
            if (!authenticationTrustResolver.isAnonymous(authentication)) {
                user = usersService.getUserByUsername(authentication.getName());
            }
            if (context.getAuthentication().getDetails() instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails authDetails = (WebAuthenticationDetails) context.getAuthentication().getDetails();
                remoteIP = authDetails.getRemoteAddress();
            }
        }
        return new Object[] {user, remoteIP};
    }
}
