package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.entity.User;
import com.my_eshop.my_eshop.repository.UserRepository;
import com.my_eshop.my_eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin (origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

//    @GetMapping ("/hi/{inputName}")
//    public boolean hello (@PathVariable String inputName) {
//        List<String> students = List.of("Pantelis", "Mateo");
//        for (String name : students) {
//
//            if (name.equals(inputName)) {
//                return  true;
//            }
//        }
//        return false;
//    }

//    @GetMapping ("/hi")
//    public boolean hello (@RequestParam String inputName) {
//        List<String> students = List.of("Pantelis", "Mateo");
//        for (String name : students) {
//
//            if (name.equals(inputName)) {
//                return  true;
//            }
//        }
//        return false;
//    }

//    @PostMapping("/create")
//    public String createUser(@RequestBody Users users){
//
//        return  "Created" + users.getClass();
//    }

    //class users

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @Autowired //dependency injection
//            UserRepository userRepository; // με αυτον τον τροπο μπορω να χρησιμοποιησω κατευθειαν τον UserRepository

    @GetMapping
    public List<User> getAllUser () { //γυρναει ολους τους Users σε λιστα
//        userRepository.findAll(); //μεθοδος που κληρονομηθηκε
        return userService.getAllUsers();
    }

//    @GetMapping ("/user/{id}")
//    public Optional<User> findByUserId (@PathVariable long id) { //το Optional σημαινει οτι μπορει να μην επιστρεψει κατι εαν δεν υπαρχει
//        return userRepository.findById(id);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping //ειναι για να δημιουργουμε
    public User createUser(@RequestBody User user){
      return userService.createUser(user);
    }

//    @PutMapping("/update-user") //εαν βαλω εαν user με το id που εχει πχ 14 θα παει και θα τον αντικαταστησει απλα στο postman θα πρεπει να γραψω το id που κανονικα δεν θα το εγραφα στην καταχωρηση γιατι το περνει μονο του. Εδω χριαζεται να το δηλωσω για να παει να κανει update - Δηλαδη δινεις ακριβως τα ιδια πραγματα οπως το δημιουργεις αλλα δινεις και το id και αλλαζαεις το πεδιο που θελεις πχ το ονομα
//    public User updateUser(@RequestBody User user){
//        return userRepository.save(user);
//    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

//    @DeleteMapping("/delete-user")
//    public String deleteUser(@RequestParam String getUser) {
//
//        return getUser;
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping ("/update-user-password")
//    public String updatePass (@RequestParam String getName) {
//
//        return getName;
//    }


}
