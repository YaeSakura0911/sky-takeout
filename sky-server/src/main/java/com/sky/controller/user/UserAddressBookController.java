package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.UserAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
public class UserAddressBookController {

    @Autowired
    private UserAddressBookService userAddressBookService;

    /**
     * 查询所有地址
     * @return Result
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> getAddressBookForList() {

        return Result.success(userAddressBookService.getAddressBookForList());
    }

    /**
     * 新增地址
     * @param addressBook 地址Entity
     * @return Result
     */
    @PostMapping
    public Result<String> saveAddressBook(@RequestBody AddressBook addressBook) {

        userAddressBookService.saveAddressBook(addressBook);

        return Result.success();
    }
}
