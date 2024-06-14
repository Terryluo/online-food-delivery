package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * show address lists
     * @param addressBook
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * add new address
     * @param addressBook
     */
    @Insert("insert into address_book (user_id, consignee, phone, gender, address_detail, is_default) " +
    "values (#{userId}, #{consignee}, #{phone}, #{gender}, #{addressDetail}, #{isDefault})")
    void insert(AddressBook addressBook);

    /**
     * get address by id
     * @param id
     * @return
     */
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);

    /**
     * modify address by id
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * set default by user id
     * @param addressBook
     */
    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefaultByUserId(AddressBook addressBook);

    /**
     * delete address by id
     * @param id
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);

}
