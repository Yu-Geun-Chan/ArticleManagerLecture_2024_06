package org.koreait.service;

import org.koreait.articleManager.Container;
import org.koreait.dao.ArticleDao;
import org.koreait.dto.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleService {
    private ArticleDao articleDao;

    public ArticleService() {
        articleDao = Container.articleDao;
    }

    public void add (Article article) {
        articleDao.add(article);
    }

    public int getSize() {
        return articleDao.getSize();
    }

    public List<Article> getForPrintArticles(String searchKeyword) {
        List<Article> forPrintArticles = new ArrayList<>();

        if (!searchKeyword.isEmpty() || searchKeyword != null) {
            System.out.println("검색어 : " + searchKeyword);

            for (Article article : articleDao.getArticles()) {
                if (article.getTitle().contains(searchKeyword)) {
                    forPrintArticles.add(article);
                }
            }
            if (forPrintArticles.isEmpty()) {
                System.out.println("해당 게시글이 없습니다.");
                return forPrintArticles;
            }
        }
        return forPrintArticles;
    }

    public Article getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public void remove(Article article) {
        articleDao.remove(article);
    }
}
