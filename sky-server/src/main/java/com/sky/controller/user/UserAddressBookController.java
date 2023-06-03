package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.UserAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
