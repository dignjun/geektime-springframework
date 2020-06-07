package org.geekbang.thingking.in.spring.ioc.overview.domain;

import org.geekbang.thingking.in.spring.ioc.overview.annotation.Super;

/**
 * 超级用户
 *
 * Created by DINGJUN on 2020.06.07.
 */
@Super
public class SuperUser extends User{
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}
