package org.koreait.articleManager;

import org.koreait.dao.ArticleDao;
import org.koreait.dao.MemberDao;

import java.util.Scanner;

public class Container {
    public static ArticleDao articleDao;
    public static MemberDao memberDao;
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
