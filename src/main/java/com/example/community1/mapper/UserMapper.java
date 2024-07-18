package com.example.community1.mapper;

import com.example.community1.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert()
    public void insert(User user);


}
