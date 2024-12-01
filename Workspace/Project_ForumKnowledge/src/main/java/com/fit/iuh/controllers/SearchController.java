package com.fit.iuh.controllers;

import com.fit.iuh.entites.User;
import com.fit.iuh.services.PostService;
import com.fit.iuh.utilities.SpringContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final PostService postService;

    public SearchController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping
    public String search(Model model, @Param("q") String q, @RequestParam(defaultValue = "keyword", required = false) String tab, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(0, 5);  // 10 bài viết mỗi trang
        model.addAttribute("rcmPost", postService.findForHome(pageable));

        model.addAttribute("title", "Tìm kiếm | iDev4rum");
        model.addAttribute("q", q);

        String tabStr = tab == null ? "keyword" : tab.isBlank() ? "keyword" : tab;

        model.addAttribute("tab", tabStr);

        if (tabStr.equals("author")) {
            model.addAttribute("searchPosts", postService.getPostByAuthor(pageable, q));
        } else if (tabStr.equals("tag")) {
            model.addAttribute("searchPosts", postService.getPostByTopic(pageable, q));
        } else {
            model.addAttribute("searchPosts", postService.searchKeyword(pageable, q));
        }

        return "views_user/search";
    }
}
