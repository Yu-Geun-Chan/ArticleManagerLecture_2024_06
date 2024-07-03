package org.koreait.controller;

import org.koreait.dto.Member;

public abstract class Controller {

    protected static Member loginedMember = null;
    // 부모 클래스에 doAction 메서드가 남아있는 이유는 버튼 유지용이다.
    // -> 형변환(캐스팅)할 때 부모 클래스에 doAction이 없다면 자식클래스들은 비슷해 보이기 위해 doAction을 지운다.
    public abstract void doAction(String cmd, String actionMethodName);

    public void makeTestData() {
    }

    public boolean isLogined() {
        return loginedMember != null;
    }
}
