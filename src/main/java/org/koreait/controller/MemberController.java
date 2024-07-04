package org.koreait.controller;

import org.koreait.articleManager.Container;
import org.koreait.util.Util;
import org.koreait.dto.Member;

import java.util.ArrayList;

public class MemberController extends Controller {

    private String cmd;
    private int lastMemberId = 3;

    public MemberController() {
        members = Container.memberDao.members;
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

        switch (actionMethodName) {
            case "join":
                doJoin();
                break;
            case "login":
                doLogin();
                break;
            case "logout":
                doLogout();
                break;
            default:
                System.out.println("명령어 확인 (actionMethodName) 오류");
                break;
        }
    }

    private void doJoin() {
        System.out.println("== 회원가입 ==");
        int id = lastMemberId + 1;
        String regDate = Util.getNow();
        String loginId = null;
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = Container.getScanner().nextLine();

            if (loginId.isEmpty()) {
                System.out.println("아이디를 입력하세요.");
                continue;
            }

            if (!isJoinableLoginId(loginId)) {
                System.out.println("이미 사용중인 아이디입니다.");
                continue;
            }
            break;
        }
        String loginPw = null;
        while (true) {
            System.out.print("비밀번호 : ");
            loginPw = Container.getScanner().nextLine();

            if (loginPw.isEmpty()) {
                System.out.println("비밀번호를 입력하세요.");
                continue;
            }

            System.out.print("비밀번호 확인 : ");
            String loginPwConfirm = Container.getScanner().nextLine();

            if (!loginPw.equals(loginPwConfirm)) {
                System.out.println("비밀번호가 일치하지 않습니다.");
                continue;
            }
            break;
        }
        String name = null;
        while (true) {
            System.out.print("이름 : ");
            name = Container.getScanner().nextLine();

            if (name.isEmpty()) {
                System.out.println("이름을 입력해주세요.");
                continue;
            }

            Member member = new Member(id, regDate, loginId, loginPw, name);
            members.add(member);

            System.out.printf("[%s]님 회원가입을 환영합니다.\n", name);
            lastMemberId++;
            break;
        }
    }

    private void doLogin() {
        while (true) {
            System.out.println("== 로그인 ==");

            System.out.print("아이디 입력 : ");
            String enterLoginId = Container.getScanner().nextLine();
            System.out.print("비밀번호 입력 : ");
            String enterLoginPw = Container.getScanner().nextLine();

            Member member = null;

            for (Member m : members) {
                if (enterLoginId.equals(m.getLoginId())) {
                    member = m;
                    break;
                }
            }

            if (member == null) {
                System.out.printf("[%s]은(는) 존재하지 않는 아이디 입니다.\n", enterLoginId);
                continue;
            }

            if (!member.getLoginPw().equals(enterLoginPw)) {
                System.out.println("비밀번호를 확인해주세요.");
                continue;
            }

            loginedMember = member; // 누가 로그인 했는가?

            System.out.printf("[%s]님 환영합니다.\n", loginedMember.getName());
            break;
        }
    }

    public void doLogout() {
        loginedMember = null;

        System.out.println("로그아웃 되었습니다.");
    }

    private boolean isJoinableLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }

    public void makeTestData() {
        System.out.println("회원 테스트 데이터가 생성되었습니다.");
        members.add(new Member(1, Util.getNow(), "test1", "test1", "김철수"));
        members.add(new Member(2, Util.getNow(), "test2", "test2", "김영희"));
        members.add(new Member(3, Util.getNow(), "test3", "test3", "홍길동"));
    }
}
