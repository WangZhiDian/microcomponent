package com.meng.designpatten.intercepting;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {

    List<Filter> list = new ArrayList<>();
    Target target;

    public void addFilter(Filter filter) {
        list.add(filter);
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void execute() {
        list.forEach(Filter::doFilter);
        target.execute();
    }
}
