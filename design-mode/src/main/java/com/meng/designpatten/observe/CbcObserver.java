package com.meng.designpatten.observe;

public class CbcObserver extends Observer {

    CbcObserver(Subject subject) {
        super.subject = subject;
        subject.addObserver(this);
    }

    @Override
    public void update() {
        System.out.println(" i am cbc object, i accept the notice, status:" + subject.getStatus());
    }
}
