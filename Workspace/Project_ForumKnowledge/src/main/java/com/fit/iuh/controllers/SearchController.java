package com.fit.iuh.controllers;

import com.fit.iuh.services.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final PostService postService;

    public SearchController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping
    public String search(Model model, @Param("q") String q) {
        Pageable pageable = PageRequest.of(0, 5);  // 10 bài viết mỗi trang
        model.addAttribute("rcmPost", postService.findForHome(pageable));

        model.addAttribute("title", "Tìm kiếm | iDev4rum");
        model.addAttribute("q", q);

        model.addAttribute("searchPosts", postService.searchKeyword(pageable, q));
        return "views_user/search";
    }
}
