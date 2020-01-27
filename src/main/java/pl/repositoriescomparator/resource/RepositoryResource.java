package pl.repositoriescomparator.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.repositoriescomparator.dto.RepositoryDto;
import pl.repositoriescomparator.exception.RepositoryNotFoundException;
import pl.repositoriescomparator.service.RepositoryService;

@RestController
@RequestMapping("/api/repository")
public class RepositoryResource {

    private final RepositoryService repositoryService;

    @Autowired
    public RepositoryResource(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/{owner}/{name}")
    public ResponseEntity getRepository(@PathVariable String owner, @PathVariable String name) {
        RepositoryDto dto = repositoryService.build(owner, name);

        if (dto.isEmpty()) {
            throw new RepositoryNotFoundException("Given repository not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
