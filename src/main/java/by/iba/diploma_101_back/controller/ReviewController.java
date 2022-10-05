package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.model.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path="/get-reviews")
public class ReviewController {
    private static List<Review> reviews = new ArrayList<>();

    static {
        reviews.add(new Review(2, 1, "test text", "2022-10-05"));
        reviews.add(new Review(3, 1, "test text 2", "2022-10-04"));
    }

    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/", "/get-reviews"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("get-reviews");
        model.addAttribute("message", message);
        return modelAndView;
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Review review = new Review();
//        review.setId(id);
//        n.setEmail(email);
//        userRepository.save(n);
        return "Saved";
    }

//    @GetMapping(path="/all")
//    public @ResponseBody Iterable<Review> getAllUsers() {
//        // This returns a JSON or XML with the users
//        return userRepository.findAll();
//    }
}
