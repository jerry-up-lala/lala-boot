package com.jerry.up.lala.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.up.lala.boot.entity.UserSetting;
import com.jerry.up.lala.boot.mapper.UserSettingMapper;
import com.jerry.up.lala.boot.service.UserSettingService;
import com.jerry.up.lala.boot.vo.UserSettingVO;
import com.jerry.up.lala.framework.core.common.Errors;
import com.jerry.up.lala.framework.core.data.DataUtil;
import com.jerry.up.lala.framework.core.exception.ServiceException;
import com.jerry.up.lala.framework.core.satoken.SaTokenUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author jerry
 * @description 针对表【user_setting(账号配置表)】的数据库操作Service实现
 * @createDate 2024-01-08 13:42:59
 */
@Service
public class UserSettingServiceImpl extends ServiceImpl<UserSettingMapper, UserSetting> implements UserSettingService {

    @Override
    public UserSettingVO info() {
        try {
            UserSetting userSetting = userSetting();
            return DataUtil.toBean(userSetting, UserSettingVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public void save(UserSettingVO userSettingVO) {
        try {
            UserSetting userSetting = userSetting();
            boolean add = userSetting == null;
            if (add) {
                userSetting = new UserSetting();
            }
            DataUtil.copy(userSettingVO, userSetting);
            if (add) {
                userSetting.setCreateUser(SaTokenUtil.currentUser().getUserId());
                userSetting.setCreateTime(new Date());
                save(userSetting);
            } else {
                userSetting.setUpdateUser(SaTokenUtil.currentUser().getUserId());
                userSetting.setUpdateTime(new Date());
                updateById(userSetting);
            }
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    private UserSetting userSetting() {
        String userId = SaTokenUtil.currentUser().getUserId();
        return getOne(new LambdaQueryWrapper<UserSetting>().eq(UserSetting::getUserId, userId));
    }
}




