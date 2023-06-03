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
     *
     * @return Result
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> getAddressBookForList() {

        return Result.success(userAddressBookService.getAddressBookForList());
    }

    /**
     * 查询默认地址
     *
     * @return Result
     */
    @GetMapping("/default")
    public Result<AddressBook> getAddressBookByDefault() {

        return Result.success(userAddressBookService.getAddressBookByDefault());
    }

    /**
     * 根据Id查询地址
     *
     * @param id 地址Id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result<AddressBook> getAddressBookById(@PathVariable Long id) {

        return Result.success(userAddressBookService.getAddressBookById(id));
    }

    /**
     * 新增地址
     *
     * @param addressBook 地址Entity
     * @return Result
     */
    @PostMapping
    public Result<String> saveAddressBook(@RequestBody AddressBook addressBook) {

        userAddressBookService.saveAddressBook(addressBook);

        return Result.success();
    }

    /**
     * 更新默认地址
     *
     * @param addressBook 地址Entity
     * @return Result
     */
    @PutMapping("/default")
    public Result<String> updateAddressBookDefault(@RequestBody AddressBook addressBook) {

        userAddressBookService.updateAddressBookDefault(addressBook);

        return Result.success();
    }

    /**
     * 更新地址
     *
     * @param addressBook 地址Entity
     * @return Result
     */
    @PutMapping
    public Result<String> updateAddressBook(@RequestBody AddressBook addressBook) {

        userAddressBookService.updateAddressBook(addressBook);

        return Result.success();
    }
}
