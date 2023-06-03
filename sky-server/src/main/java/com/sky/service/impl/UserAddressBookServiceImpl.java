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
     *
     * @return List<AddressBook>
     */
    @Override
    public List<AddressBook> getAddressBookForList() {

        return addressBookMapper.selectByUserId(BaseContext.getCurrentId());
    }

    /**
     * 查询默认地址
     *
     * @return AddressBook
     */
    @Override
    public AddressBook getAddressBookByDefault() {

        return addressBookMapper.selectByUserIdAndIsDefault(BaseContext.getCurrentId());
    }

    /**
     * 根据Id查询地址
     *
     * @param id 地址Id
     * @return AddressBook
     */
    @Override
    public AddressBook getAddressBookById(Long id) {

        return addressBookMapper.selectById(id);
    }

    /**
     * 新增地址
     *
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

    /**
     * 更新地址默认状态
     *
     * @param addressBook 地址Id
     */
    @Override
    public void updateAddressBookDefault(AddressBook addressBook) {

        // 先根据地址Id查出地址，如果地址是默认的就啥也不做，如果地址不是默认就先把默认地址查出来，然后把原来的默认地址设为非默认，再把新地址设为默认地址
        AddressBook newDefaultAddressBook = addressBookMapper.selectById(addressBook.getId());

        // 如果新地址已经是默认地址
        if (newDefaultAddressBook.getIsDefault() == 1) {
            return;
        }

        // 根据用户Id查询老的默认地址
        AddressBook defaultAddressBook = addressBookMapper.selectByUserIdAndIsDefault(BaseContext.getCurrentId());

        // 如果老的默认地址不为空
        if (defaultAddressBook != null) {

            // 设置老的默认地址状态
            defaultAddressBook.setIsDefault(0);

            // 执行更新老地址的默认地址状态
            addressBookMapper.update(defaultAddressBook);
        }

        // 设置新地址的默认地址状态
        newDefaultAddressBook.setIsDefault(1);

        // 执行更新新地址的默认地址状态
        addressBookMapper.update(newDefaultAddressBook);
    }

    /**
     * 更新地址
     *
     * @param addressBook 地址Entity
     */
    @Override
    public void updateAddressBook(AddressBook addressBook) {

        addressBookMapper.update(addressBook);
    }
}
