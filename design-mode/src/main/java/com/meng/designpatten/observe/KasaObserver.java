package com.meng.designpatten.observe;

public class KasaObserver extends Observer {

    KasaObserver(Subject subject) {
        super.subject = subject;
        subject.addObserver(this);
    }

    @Override
    public void update() {
        System.out.println(" i am kasa object, i accept the notice, status:" + subject.getStatus());
    }
}
