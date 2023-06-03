package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface UserAddressBookService {

    /**
     * 查询所有地址
     *
     * @return List<Address> 地址列表
     */
    List<AddressBook> getAddressBookForList();

    /**
     * 查询默认地址
     *
     * @return AddressBook
     */
    AddressBook getAddressBookByDefault();

    /**
     * 根据Id查询地址
     *
     * @param id 地址Id
     * @return AddressBook
     */
    AddressBook getAddressBookById(Long id);

    /**
     * 新增地址
     *
     * @param addressBook 地址Entity
     */
    void saveAddressBook(AddressBook addressBook);

    /**
     * 更新地址默认状态
     *
     * @param addressBook 地址Entity
     */
    void updateAddressBookDefault(AddressBook addressBook);

    /**
     * 更新地址
     *
     * @param addressBook 地址Entity
     */
    void updateAddressBook(AddressBook addressBook);
}
