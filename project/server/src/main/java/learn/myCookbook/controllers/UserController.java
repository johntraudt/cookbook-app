package learn.myCookbook.controllers;

import learn.myCookbook.domain.Result;
import learn.myCookbook.domain.AppUserService;
import learn.myCookbook.models.AppUser;
import learn.myCookbook.models.UserRole;
import learn.myCookbook.security.JwtConverter;
import learn.myCookbook.security.ValidationErrorResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/user")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtConverter converter;
    private final AppUserService service;

    public UserController(AuthenticationManager authenticationManager, JwtConverter converter, AppUserService service) {
        this.authenticationManager = authenticationManager;
        this.converter = converter;
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


    @PostMapping("/create_account")
    public ResponseEntity<Object> add(@RequestBody AppUser user) {
        UserRole role = new UserRole();

        role.setName("USER");
        role.setUserRoleId(1);
        user.setRole(role);

        Result<AppUser> result = service.add(user);
        if (result.isSuccess()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("appUserId", String.valueOf(user.getUserId()));
            return new ResponseEntity<>(map, HttpStatus.CREATED);
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

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> credentials) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(credentials.get("userName"), credentials.get("passwordHash"));

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                User user = (User) authentication.getPrincipal();

                String jwtToken = converter.getTokenFromUser(user);

                HashMap<String, String> map = new HashMap<>();
                map.put("jwt_token", jwtToken);

                return new ResponseEntity<>(map, HttpStatus.OK);
            }

        } catch (AuthenticationException ex) {
            System.out.println(ex);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

//    @PostMapping("/refresh_token")
//    public ResponseEntity<Map<String, String>> refreshToken(UsernamePasswordAuthenticationToken principal) {
//        User user = new User(principal.getName(), principal.getName(), principal.getAuthorities());
//        String jwtToken = converter.getTokenFromUser(user);
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("jwt_token", jwtToken);
//
//        return new ResponseEntity<>(map, HttpStatus.OK);
//    }
}
