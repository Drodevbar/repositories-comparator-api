package pl.repositoriescomparator.service.github;

import pl.repositoriescomparator.service.StatusAwareRepositoryInterface;

abstract class StatusAwareRepositoryService implements StatusAwareRepositoryInterface {

    private boolean hasError = false;

    public boolean hasError() {
        return hasError;
    }

    void setError() {
        hasError = true;
    }
}
