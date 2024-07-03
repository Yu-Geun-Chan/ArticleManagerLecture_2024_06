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
    private Member loginMember;

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
                doLogin();
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
            if (isJoinableLoginId(loginId) == false) {
                System.out.println("이미 사용중이야");
                continue;
            }
            break;
        }
        String loginPw = null;
        while (true) {
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine();
            System.out.print("비밀번호 확인 : ");
            String loginPwConfirm = sc.nextLine();

            if (loginPw.equals(loginPwConfirm) == false) {
                System.out.println("비번 다시 확인해");
                continue;
            }
            break;
        }

        System.out.print("이름 : ");
        String name = sc.nextLine();

        Member member = new Member(id, regDate, loginId, loginPw, name);
        members.add(member);

        System.out.println(id + "번 회원이 가입되었습니다");
        lastMemberId++;
    }

    private void doLogin() {
        System.out.println("== 로그인 ==");
        if (loginMember != null) {
            System.out.println("로그아웃 후 이용해주세요.");
            return;
        }

        String enterLoginId = null;
        String enterLoginPw = null;
        Member member = null;

        while (true) {
            System.out.print("아이디 입력 : ");
            enterLoginId = sc.nextLine();

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
            break;
        }
        while (true) {
            System.out.print("비밀번호 입력 : ");
            enterLoginPw = sc.nextLine();

            for (Member m : members) {
                if (enterLoginPw.equals(m.getLoginPw())) {
                    member = m;
                    break;
                }
            }

            if (!enterLoginPw.equals(member.getLoginPw())) {
                System.out.println("비밀번호를 확인해주세요.");
                continue;
            }
            loginMember = member;
            System.out.printf("[%s]님 환영합니다.\n", member.getName());
            break;
        }
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
