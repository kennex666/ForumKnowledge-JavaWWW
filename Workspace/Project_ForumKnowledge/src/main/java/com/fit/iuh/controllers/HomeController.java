package com.fit.iuh.controllers;

import com.fit.iuh.services.PostService;
import com.fit.iuh.services.UserService;
import com.fit.iuh.utilities.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
        public String home(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);  // 10 bài viết mỗi trang
        model.addAttribute("postsPage", postService.findForHome(pageable));
            model.addAttribute("title", "Trang chủ | iDev4rum");
            model.addAttribute("currentUser", userService.findUserByEmail(
                    SpringContext.getCurrentUserEmail()
            ));
            return "views_user/index";  // Đảm bảo rằng views_user/index.html tồn tại trong thư mục templates
        }

}
