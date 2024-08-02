package com.jerry.up.lala.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.up.lala.boot.entity.UserSetting;
import com.jerry.up.lala.boot.vo.UserSettingVO;

/**
* @author jerry
* @description 针对表【user_setting(账号配置表)】的数据库操作Service
* @createDate 2024-01-08 13:42:59
*/
public interface UserSettingService extends IService<UserSetting> {

    UserSettingVO info();

    void save(UserSettingVO userSettingVO);
}
