package kakao.springboot.demo.controller;

import kakao.springboot.demo.model.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class SampleController {

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long userId){
        return new User(userId,"keywordBiz",10);
    }

    @PostMapping
    public String newUserHandler(@ModelAttribute @Valid User user, BindingResult error){
        if(error.hasErrors()){
            return "users/new";
        }

        return "redirect:/users";
    }
}