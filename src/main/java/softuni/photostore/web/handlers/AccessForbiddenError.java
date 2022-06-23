package softuni.photostore.web.handlers;

public class AccessForbiddenError extends RuntimeException {

    public AccessForbiddenError() {
        super("Нямате достатъчно права за да видите тази страница!");
    }

}
