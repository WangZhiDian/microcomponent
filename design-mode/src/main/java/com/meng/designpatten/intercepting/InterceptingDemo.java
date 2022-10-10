package com.meng.designpatten.intercepting;

public class InterceptingDemo {

    public static void main(String[] args) {

        FilterChainManager filterChainManager = new FilterChainManager(new Target());
        filterChainManager.addFilter(new DebugFilter());
        filterChainManager.addFilter(new AuthFilter());

        Client client = new Client();
        client.setFilterChainManager(filterChainManager);
        client.sendRequest("client request");
    }
}
