package learn.myCookbook.domain;

import learn.myCookbook.data.CookbookRepository;
import learn.myCookbook.models.Cookbook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CookbookService {
    private final CookbookRepository repository;

    public CookbookService(CookbookRepository repository) {
        this.repository = repository;
    }

    public List<Cookbook> findAll() {
        return repository.findAll();
    }

    public Cookbook findById(int cookbookId) {
        return repository.findById(cookbookId);
    }

    public List<Cookbook> findAllByUserId(int userId) {
        return repository.findAllByUserId(userId);
    }

    public List<Cookbook> findPublicByUserId(int userId) {
        return repository.findPublicByUserId(userId);
    }

    public List<Cookbook> findAllByRecipeId(int recipeId) {
        return repository.findAllByRecipeId(recipeId);
    }

    public List<Cookbook> findPublicByRecipeId(int recipeId) {
        return repository.findPublicByRecipeId(recipeId);
    }

    public List<Cookbook> findPublicByTitle(String title) {
        return repository.findPublicByTitle(title);
    }
}
    /*
    Cookbook add(Cookbook cookbook);

    boolean update(Cookbook cookbook);

    boolean deleteById(int cookbookId);
     */