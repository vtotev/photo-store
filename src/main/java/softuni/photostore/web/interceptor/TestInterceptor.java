package softuni.photostore.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TestInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String fileExtRegex = "[.][\\w]{2,4}";
        Pattern pattern = Pattern.compile(fileExtRegex);
        String url = request.getRequestURL().toString();
        url = url.substring(url.lastIndexOf("/"));
        Matcher matcher = pattern.matcher(url);
        if (!matcher.find()) {
            System.out.println(url);
        }
//        while (matcher.find()) {
//            System.out.println(matcher.group());
//        }
        //        System.out.println("is it new shit: "+request.getSession().isNew());
//        String[] fileExtensions = {".css", ".js", ".png", ".jpg", ".jpeg", ".html", ".html"};
//
//
//
//        if () {
//        }
//            System.out.println("html page loaded: " + request.getRequestURL().toString());
//        System.out.printf("context path: %s%nauthType: %s%nmethod: %s%nreq session id: %s%n%n",
//                request.getContextPath(),
//                request.getAuthType(),
//                request.getMethod(),
//                request.getRequestedSessionId()
//                );
//        System.out.println(request.getRequestURL());
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
