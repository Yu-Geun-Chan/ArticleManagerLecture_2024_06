package org.koreait.articleManager;

import com.sun.source.tree.MemberSelectTree;
import org.koreait.dao.ArticleDao;
import org.koreait.dao.MemberDao;
import org.koreait.dto.Article;
import org.koreait.service.ArticleService;
import org.koreait.service.MemberService;

import java.util.Scanner;

public class Container {
    public static ArticleDao articleDao;
    public static MemberDao memberDao;

    public static ArticleService articleService;
    public static MemberService memberService;

    private static Scanner sc;

    static {
        articleDao = new ArticleDao();
        memberDao = new MemberDao();
    }

    public static void init() {
        sc = new Scanner(System.in);
    }

    public static void close() {
        sc.close();
    }

    public static Scanner getScanner() {
        return sc;
    }
}
