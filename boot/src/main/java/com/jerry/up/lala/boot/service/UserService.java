package com.jerry.up.lala.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.up.lala.boot.bo.UserLoadBO;
import com.jerry.up.lala.boot.dto.UserDTO;
import com.jerry.up.lala.boot.dto.UserQueryDTO;
import com.jerry.up.lala.boot.entity.User;
import com.jerry.up.lala.boot.vo.*;
import com.jerry.up.lala.framework.common.model.DataIdBody;
import com.jerry.up.lala.framework.common.r.PageR;

import java.util.List;

/**
* @author jerry
* @description 针对表【user(账号表)】的数据库操作Service
* @createDate 2023-09-06 09:56:37
*/
public interface UserService extends IService<User> {

    List<UserDTO> listDTO(UserQueryDTO queryDTO, UserLoadBO userLoadBO);

    List<UserVO> list(UserQueryVO userQueryVO);

    PageR<UserVO> pageQuery(UserQueryVO userQueryVO);

    UserInfoVO info(String id);

    UserPersonalVO personalInfo();

    String password(String id);

    void personalUpdatePassWord(UserPersonalPassWordVO userPersonalPassWordVO);

    void verify(DataIdBody<String, String> dataIdBody);

    void add(UserSaveVO userSaveVO);

    void update(String id, UserSaveVO userSaveVO);

    void personalSave(UserPersonalSaveVO userPersonalSaveVO);

    void checkUserSaveArg(UserSaveVO userSaveVO);

    void state(UserStateVO userStateVO);
}
