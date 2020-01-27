package pl.repositoriescomparator.application.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.repositoriescomparator.application.errorresponse.RepositoryNotFoundResponse;
import pl.repositoriescomparator.exception.RepositoryNotFoundException;

@ControllerAdvice
public class RepositoryResourceAdvice {

    @ExceptionHandler(RepositoryNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RepositoryNotFoundResponse handleRepositoryNotFoundException(RepositoryNotFoundException e) {
        return new RepositoryNotFoundResponse(e.getMessage());
    }
}
