package com.meng.designpatten.observe;

public class ObserverDemo {


    public static void main(String[] args) {


        Subject subject = new Subject();
        KasaObserver kasaObserver = new KasaObserver(subject);
        CbcObserver cbcObserver = new CbcObserver(subject);

        subject.setStatus(10);

    }

}
