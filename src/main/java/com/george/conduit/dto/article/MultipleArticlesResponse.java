package com.george.conduit.dto.article;

import com.george.conduit.model.Article;

import java.util.List;
import java.util.stream.Collectors;


public class MultipleArticlesResponse {

    private List<SingleArticleResponse> articles;
    private int articlesCount;

    public MultipleArticlesResponse(List<Article> articles) {
        this.articles = articles.stream().map(article -> new SingleArticleResponse(article)).collect(Collectors.toList());
        this.articlesCount = articles.size();
    }

    public List<SingleArticleResponse> getArticles() {
        return articles;
    }

    public void setArticles(List<SingleArticleResponse> articles) {
        this.articles = articles;
    }

    public int getArticlesCount() {
        return articlesCount;
    }

    public void setArticlesCount(int articlesCount) {
        this.articlesCount = articlesCount;
    }
}
