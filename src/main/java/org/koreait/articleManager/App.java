package org.koreait.articleManager;

import org.koreait.controller.ArticleController;
import org.koreait.controller.Controller;
import org.koreait.controller.MemberController;

public class App {
    public void run() {
        System.out.println("== 프로그램 시작 ==");

        MemberController memberController = new MemberController();
        ArticleController articleController = new ArticleController();

        articleController.makeTestData();
        memberController.makeTestData();

        Controller controller = null;

        while (true) {
            System.out.print("명령어) ");
            String cmd = Container.getScanner().nextLine();

            if (cmd.length() == 0) {
                System.out.println("명령어를 입력하세요");
                continue;
            }
            if (cmd.equals("exit")) {
                break;
            }

            String[] cmdBits = cmd.split(" ");

            String controllerName = cmdBits[0];

            if (cmdBits.length == 1) {
                System.out.println("명령어 확인해");
                continue;
            }

            String actionMethodName = cmdBits[1];

            String forLoginCheck = controllerName + "/" + actionMethodName;

            switch (forLoginCheck) {
                case "article/write":
                case "article/delete":
                case "article/modify":
                case "member/logout":
                    if (!Controller.isLogined()) {
                        System.out.println("로그인 후 이용하세요.");
                        continue;
                    }
                    break;
            }

            switch (forLoginCheck) {
                case "member/login":
                case "member/join":
                    if (Controller.isLogined()) {
                        System.out.println("로그아웃 후 이용하세요.");
                        continue;
                    }
                    break;
            }

            if (controllerName.equals("article")) {
                controller = articleController;
            } else if (controllerName.equals("member")) {
                controller = memberController;
            } else {
                System.out.println("사용불가 명령어");
                continue;
            }

            controller.doAction(cmd, actionMethodName);

        }
        System.out.println("== 프로그램 종료 ==");
    }
}
