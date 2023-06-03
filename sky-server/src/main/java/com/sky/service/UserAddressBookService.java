package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface UserAddressBookService {

    /**
     * 查询所有地址
     */
    List<AddressBook> getAddressBookForList();

    /**
     * 新增地址
     * @param addressBook 地址Entity
     */
    void saveAddressBook(AddressBook addressBook);
}
