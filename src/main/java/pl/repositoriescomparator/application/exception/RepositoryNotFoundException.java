package pl.repositoriescomparator.application.exception;

public class RepositoryNotFoundException extends RuntimeException {

    public RepositoryNotFoundException(String message) {
        super(message);
    }
}
