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
}
