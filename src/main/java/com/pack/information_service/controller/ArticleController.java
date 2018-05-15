package com.pack.information_service.controller;

import com.pack.information_service.domain.Article;
import com.pack.information_service.domain.User;
import com.pack.information_service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/articlePage")
public class ArticleController {

    private ArticleService articleService;
    private UserService userService;
    private ArticleRatingService articleRatingService;
    private CommentService commentService;
    private CommentRatingService commentRatingService;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService, ArticleRatingService articleRatingService, CommentService commentService, CommentRatingService commentRatingService) {
        this.articleService = articleService;
        this.userService = userService;
        this.articleRatingService = articleRatingService;
        this.commentService = commentService;
        this.commentRatingService = commentRatingService;
    }

    @GetMapping("/{idArticle}")
    public String openArticle(@PathVariable Long idArticle, Model model) {
        Article article = articleService.findById(idArticle);
        model.addAttribute("article", article);
        model.addAttribute("commentsAuthors", articleService.findCommentsAuthors(idArticle));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals("anonymousUser")) {
            User loggedUser = userService.findByUsername(username);
            model.addAttribute("userArticleMark", articleRatingService.userMark(article, loggedUser));
            model.addAttribute("userCommentMarks", commentRatingService.userMark(article, loggedUser.getIdUser()));
        }
        return "article";
    }

    @PostMapping("/addArticleMark")
    public String addArticleMark(@RequestParam Integer mark, @RequestParam Long idArticle) {
        articleRatingService.save(mark, idArticle);
        return "redirect:/articlePage/" + idArticle;
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam String commentContent, @RequestParam Long idArticle) {
        commentService.save(commentContent, idArticle);
        return "redirect:/articlePage/" + idArticle;
    }

    @PostMapping("/editComment")
    public String editComment(@RequestParam String commentContent, @RequestParam Long idArticle, @RequestParam Long idComment) {
        commentService.edit(commentContent, idComment);
        return "redirect:/articlePage/" + idArticle;
    }

    @PostMapping("/commentMark")
    public String commentMark(@RequestParam Integer mark, @RequestParam Long idArticle, @RequestParam Long idComment) {
        commentRatingService.save(mark, idComment, idArticle);
        return "redirect:/articlePage/" + idArticle;
    }

    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam Long idComment, @RequestParam Long idArticle) {
        commentService.delete(idComment);
        return "redirect:/articlePage/" + idArticle;
    }

    @GetMapping("/journalist/{idUser}")
    public String getJournalistArticles(@PathVariable Long idUser, Model model) {
        model.addAttribute("articles", articleService.findByIdUser(idUser));
        return "searchPage";
    }

    @GetMapping("/category/{category}")
    public String getCategoryArticles(@PathVariable String category, Model model) {
        model.addAttribute("articles", articleService.findByCategory(category));
        return "searchPage";
    }

    @PostMapping("/searchArticle")
    public String searchArticle(@RequestParam String search, Model model) {
        model.addAttribute("articles", articleService.findByTitle(search));
        return "searchPage";
    }
}
