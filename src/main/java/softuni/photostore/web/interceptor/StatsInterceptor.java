package softuni.photostore.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import softuni.photostore.service.StatsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StatsInterceptor implements HandlerInterceptor {

    private final StatsService statsService;

    public StatsInterceptor(StatsService statsService) {
        this.statsService = statsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String fileExtRegex = "[.][\\w\\W]{2,4}";
        Pattern pattern = Pattern.compile(fileExtRegex);
        String url = request.getRequestURL().toString();
        url = url.substring(url.lastIndexOf("/"));
        Matcher matcher = pattern.matcher(url);
        if (!matcher.find()) {
            statsService.onRequest();
        }
        return true;
    }
}
