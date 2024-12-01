package com.fit.iuh.controllers;

import com.fit.iuh.entites.User;
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

    @GetMapping({"", "/home"})
        public String home(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(value = "tab", required = false) String tab) {
        Pageable pageable = PageRequest.of(page, 10);  // 10 bài viết mỗi trang
        String tabStr = tab == null ? "home" : tab.isBlank() ? "home" : tab;
        User currentUser = userService.findUserByEmail(
                SpringContext.getCurrentUserEmail()
        );
        if (currentUser == null) {
            model.addAttribute("postsPage", postService.findForHome(pageable));
        } else{
            if (tabStr.equals("home")) {
                model.addAttribute("postsPage", postService.findForHome(pageable));
            } else if (tabStr.equals("following")) {
                model.addAttribute("postsPage", postService.findForFollowing(pageable, currentUser.getUserId()));
            } else {
                model.addAttribute("postsPage", postService.findForHome(pageable));
            }
        }

        model.addAttribute("tab", tabStr);
        model.addAttribute("title", "Trang chủ | iDev4rum");
        model.addAttribute("currentUser", currentUser);
        return "views_user/index";  // Đảm bảo rằng views_user/index.html tồn tại trong thư mục templates\
    }

}
