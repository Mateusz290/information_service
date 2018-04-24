package com.pack.information_service.controller;

import com.pack.information_service.domain.Article;
import com.pack.information_service.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class MainPageFacade {

    private List<Article> topNews;
    private List<Article> news;
    private List<Article> sport;
    private List<Article> business;
    private List<Article> entertainment;
    private List<Article> technologies;
    private List<Article> motorization;
    private Map<String, String> categories;
    private ArticleRepository articleRepository;

    @Autowired
    public MainPageFacade(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void generateContent() {
        topNews = articleRepository
                .findFirst20ByOrderByPriorityAscPublicationDateDesc();
        news = articleRepository
                .findFirst10ByStatusAndCategoryOrderByPriorityAscPublicationDateDesc("to display", "News");
        sport = articleRepository
                .findFirst10ByStatusAndCategoryOrderByPriorityAscPublicationDateDesc("to display", "Sport");
        business = articleRepository
                .findFirst10ByStatusAndCategoryOrderByPriorityAscPublicationDateDesc("to display", "Business");
        entertainment = articleRepository
                .findFirst10ByStatusAndCategoryOrderByPriorityAscPublicationDateDesc("to display", "Entertainment");
        technologies = articleRepository
                .findFirst10ByStatusAndCategoryOrderByPriorityAscPublicationDateDesc("to display", "Technologies");
        motorization = articleRepository
                .findFirst10ByStatusAndCategoryOrderByPriorityAscPublicationDateDesc("to display", "Motorization");
        categories = new LinkedHashMap<>() {{
            put("News", "MainPage.article.news");
            put("Sport", "MainPage.article.sport");
            put("Business", "MainPage.article.business");
            put("Entertainment", "MainPage.article.entertainment");
            put("Technologies", "MainPage.article.technologies");
            put("Motorization", "MainPage.article.motorization");
        }};
    }

    public List<Article> getTopNews() {
        return topNews;
    }

    public void setTopNews(List<Article> topNews) {
        this.topNews = topNews;
    }

    public List<Article> getNews() {
        return news;
    }

    public void setNews(List<Article> news) {
        this.news = news;
    }

    public List<Article> getSport() {
        return sport;
    }

    public void setSport(List<Article> sport) {
        this.sport = sport;
    }

    public List<Article> getBusiness() {
        return business;
    }

    public void setBusiness(List<Article> business) {
        this.business = business;
    }

    public List<Article> getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(List<Article> entertainment) {
        this.entertainment = entertainment;
    }

    public List<Article> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Article> technologies) {
        this.technologies = technologies;
    }

    public List<Article> getMotorization() {
        return motorization;
    }

    public void setMotorization(List<Article> motorization) {
        this.motorization = motorization;
    }

    public Map<String, String> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, String> categories) {
        this.categories = categories;
    }
}
