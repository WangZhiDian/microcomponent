package com.meng.designpatten.observe;

import java.util.ArrayList;
import java.util.List;

/*被观察者，拥有通知对象链条*/
public class Subject {

    List<Observer> list = new ArrayList<Observer>();

    int status;

    int getStatus() {
        return status;
    }

    public void addObserver(Observer observer) {
        list.add(observer);
    }

    public void setStatus(int status) {
        this.status = status;
        notifyObserver();
    }

    private void notifyObserver() {
        for (Observer observer: list) {
            observer.update();
        }
    }

}
