package org.koreait.controller;

import org.koreait.Container;
import org.koreait.dto.Member;
import org.koreait.util.Util;
import org.koreait.dto.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {

    private List<Article> articles;
    private String cmd;

    private int lastArticleId = 3;

    public ArticleController() {
        articles = new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

        switch (actionMethodName) {
            case "write":
                doWrite();
                break;
            case "list":
                showList();
                break;
            case "detail":
                showDetail();
                break;
            case "modify":
                doModify();
                break;
            case "delete":
                doDelete();
                break;
            default:
                System.out.println("올바르지 않은 명령어 입니다.(actionMethodName) 오류");
                break;
        }
    }

    private void doWrite() {
        System.out.println("== 게시글 작성 ==");

        int id = lastArticleId + 1;
        String regDate = Util.getNow();
        String updateDate = regDate;
        System.out.print("제목 : ");
        String title = Container.getScanner().nextLine();
        System.out.print("내용 : ");
        String body = Container.getScanner().nextLine();

        Article article = new Article(id, regDate, updateDate, loginedMember.getId(), title, body);
        articles.add(article);

        System.out.printf("%d번 글이 생성되었습니다\n", id);
        lastArticleId++;
    }

    private void showList() {
        System.out.println("== 게시글 목록 ==");
        if (articles.isEmpty()) {
            System.out.println("작성된 게시글이 없습니다.");
            return;
        }

        String searchKeyword = cmd.substring("article list".length()).trim();

        List<Article> forPrintArticles = articles;

        if (!searchKeyword.isEmpty()) {
            System.out.println("검색어 : " + searchKeyword);
            forPrintArticles = new ArrayList<>();

            for (Article article : articles) {
                if (article.getTitle().contains(searchKeyword)) {
                    forPrintArticles.add(article);
                }
            }
            if (forPrintArticles.isEmpty()) {
                System.out.println("해당 게시글이 없습니다.");
                return;
            }
        }

        System.out.println("  번호   /    날짜   /   작성자   /  제목   /   내용   ");
        for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
            Article article = forPrintArticles.get(i);

            if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                System.out.printf("  %d   /   %s      /   %s   /  %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[1], loginedMember.getId(), article.getTitle(), article.getBody());
            } else {
                System.out.printf("  %d   /   %s     /   %s   /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[0], loginedMember.getId(), article.getTitle(), article.getBody());
            }

        }

    }

    private void showDetail() {
        System.out.println("== 게시글 상세보기 ==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다.");
            return;
        }


        System.out.println("번호 : " + foundArticle.getId());
        System.out.println("작성날짜 : " + foundArticle.getRegDate());
        System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
        System.out.println("작성자 : " + loginedMember.getId());
        System.out.println("제목 : " + foundArticle.getTitle());
        System.out.println("내용 : " + foundArticle.getBody());
    }

    private void doDelete() {
        System.out.println("== 게시글 삭제 ==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다.");
            return;
        }

        if (foundArticle.getMemberId() != loginedMember.getId()) {
            System.out.println("해당 게시글에 대한 권한이 없습니다.");
            return;
        }

        articles.remove(foundArticle);
        System.out.printf("%d번 게시글이 삭제되었습니다.\n", foundArticle.getId());
    }

    private void doModify() {
        System.out.println("== 게시글 수정 ==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다.");
            return;
        }

        if (foundArticle.getMemberId() != loginedMember.getId()) {
            System.out.println("해당 게시글에 대한 권한이 없습니다.");
            return;
        }

        System.out.println("기존 제목 : " + foundArticle.getTitle());
        System.out.println("기존 내용 : " + foundArticle.getBody());
        System.out.print("새 제목 : ");
        String newTitle = Container.getScanner().nextLine();
        System.out.print("새 내용 : ");
        String newBody = Container.getScanner().nextLine();

        foundArticle.setTitle(newTitle);
        foundArticle.setBody(newBody);
        foundArticle.setUpdateDate(Util.getNow());

        System.out.printf("%d번 게시글이 수정되었습니다.\n", foundArticle.getId());
    }


    private Article getArticleById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    public void makeTestData() {
        System.out.println("게시글 테스트 데이터가 생성되었습니다.");
        articles.add(new Article(1, "2023-12-12 12:12:12", "2023-12-12 12:12:12", 1, "제목123", "내용1"));
        articles.add(new Article(2, Util.getNow(), Util.getNow(), 2,  "제목72", "내용2"));
        articles.add(new Article(3, Util.getNow(), Util.getNow(), 1, "제목1233", "내용3"));
    }
}
