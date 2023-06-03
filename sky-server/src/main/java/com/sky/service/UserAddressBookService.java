package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface UserAddressBookService {

    /**
     * 查询所有地址
     */
    List<AddressBook> getAddressBookForList();
}
