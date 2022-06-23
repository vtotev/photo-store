package softuni.photostore.web.handlers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandlingController implements ErrorController {

    @ExceptionHandler(AccessForbiddenError.class)
    public ModelAndView handleForbiddenException() {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "403: Нямате необходимият достъп за тази страница!");
        modelAndView.setStatus(HttpStatus.FORBIDDEN);
        return modelAndView;
    }

}
