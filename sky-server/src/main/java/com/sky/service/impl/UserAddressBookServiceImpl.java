package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.UserAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressBookServiceImpl implements UserAddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 查询所有地址
     */
    @Override
    public List<AddressBook> getAddressBookForList() {
        return addressBookMapper.selectByUserId(BaseContext.getCurrentId());
    }

    /**
     * 新增地址
     * @param addressBook 地址Entity
     */
    @Override
    public void saveAddressBook(AddressBook addressBook) {

        // 设置用户Id
        addressBook.setUserId(BaseContext.getCurrentId());
        // 设置是否默认
        addressBook.setIsDefault(0);

        // 执行插入地址SQL
        addressBookMapper.insert(addressBook);
    }
}
