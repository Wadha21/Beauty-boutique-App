package com.example.myproducts.Model;

import java.util.List;

public class Request {

    private String username;
    private String name;
    private String address;
    private String total;
    private List<Order> orderList;
    private String status="0";


    public Request() {
    }

    public Request(String username, String name, String address, String total, List<Order> orderList) {
        this.username = username;
        this.name = name;
        this.address = address;
        this.total = total;
        this.orderList = orderList;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

}
