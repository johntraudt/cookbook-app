package learn.myCookbook.controllers;

import learn.myCookbook.domain.Result;
import learn.myCookbook.domain.AppUserService;
import learn.myCookbook.models.AppUser;
import learn.myCookbook.security.ValidationErrorResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/user")
public class UserController {

    private final AppUserService service;

    public UserController(AppUserService service) {
        this.service = service;
    }

    @GetMapping
    public List<AppUser> findAll() {
        return service.findAll();
    }

    @GetMapping("/{userId}")
    public AppUser findById(@PathVariable int userId) {
        return service.findById(userId);
    }


    @PostMapping
    public ResponseEntity<Object> add(@RequestBody AppUser user) {
        Result<AppUser> result = service.add(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable int userId, @RequestBody AppUser user) {
        if (userId != user.getUserId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<AppUser> result = service.update(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deactivateById(@PathVariable int userId) {
        if (service.deactivateById(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // TODO: Could this replace POST?
    @PostMapping("/create_account")
    public ResponseEntity<?> createAccount(@RequestBody AppUser appUser) {
        try {
//            appUser.getRoles().add("USER");
            service.add(appUser);
        } catch (ValidationException ex) {
            ValidationErrorResult validationErrorResult = new ValidationErrorResult();
            validationErrorResult.addMessage(ex.getMessage());
            return new ResponseEntity<>(validationErrorResult, HttpStatus.BAD_REQUEST);
        } catch (DuplicateKeyException ex) {
            ValidationErrorResult validationErrorResult = new ValidationErrorResult();
            validationErrorResult.addMessage("The provided username already exists");
            return new ResponseEntity<>(validationErrorResult, HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("appUserId", String.valueOf(appUser.getUserId()));

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
