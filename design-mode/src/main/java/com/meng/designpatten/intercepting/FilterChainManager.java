package com.meng.designpatten.intercepting;

public class FilterChainManager {

    FilterChain filterChain = new FilterChain();

    public FilterChainManager(Target target) {
        this.filterChain.setTarget(target);
    }

    public void addFilter(Filter filter) {
        filterChain.addFilter(filter);
    }

    public void filterRequest(String request) {
        filterChain.execute();
    }
}
