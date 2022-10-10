package com.meng.designpatten.intercepting;

public class Client {

    FilterChainManager filterChainManager;

    public void setFilterChainManager(FilterChainManager filterChainManager) {
        this.filterChainManager = filterChainManager;
    }

    public void sendRequest(String request) {
        filterChainManager.filterRequest(request);
    }
}
