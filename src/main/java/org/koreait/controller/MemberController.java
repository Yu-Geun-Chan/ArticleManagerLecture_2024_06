package org.koreait.controller;

import org.koreait.util.Util;
import org.koreait.dto.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {

    private Scanner sc;
    private List<Member> members;
    private String cmd;
    private Member loginMember = null;

    private int lastMemberId = 3;

    public MemberController(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

        switch (actionMethodName) {
            case "join":

                doJoin();
                break;
            case "login":
                if (isLogined()) {
                    System.out.println("로그인 후 이용해주세요.");
                    return;
                }
                doLogin();
                break;
            case "logout":
                if (!isLogined()) {
                    System.out.println("로그인 후 이용해주세요.");
                    return;
                }
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
            loginId = sc.nextLine().trim();

            if (loginId.isEmpty()) {
                System.out.println("아이디를 입력하세요.");
                continue;
            }

            if (!isJoinableLoginId(loginId)) {
                System.out.println("이미 사용중이야");
                continue;
            }
            break;
        }
        String loginPw = null;
        while (true) {
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine();

            if (loginPw.isEmpty()) {
                System.out.println("비밀번호를 입력하세요.");
                continue;
            }

            System.out.print("비밀번호 확인 : ");
            String loginPwConfirm = sc.nextLine();

            if (!loginPw.equals(loginPwConfirm)) {
                System.out.println("비번 다시 확인해");
                continue;
            }
            break;
        }
        String name = null;
        while (true) {
            System.out.print("이름 : ");
            name = sc.nextLine();

            if (name.isEmpty()) {
                System.out.println("이름을 입력해주세요");
                continue;
            }

            Member member = new Member(id, regDate, loginId, loginPw, name);
            members.add(member);

            System.out.println(id + "번 회원이 가입되었습니다");
            lastMemberId++;
        }
    }

    private void doLogin() {
        while (true) {
            System.out.println("== 로그인 ==");

            System.out.print("아이디 입력 : ");
            String enterLoginId = sc.nextLine();
            System.out.print("비밀번호 입력 : ");
            String enterLoginPw = sc.nextLine();

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
        System.out.println("회원 테스트 데이터 생성");
        members.add(new Member(1, Util.getNow(), "test1", "test1", "test1"));
        members.add(new Member(2, Util.getNow(), "test2", "test2", "test2"));
        members.add(new Member(3, Util.getNow(), "test3", "test3", "test3"));
    }
}
