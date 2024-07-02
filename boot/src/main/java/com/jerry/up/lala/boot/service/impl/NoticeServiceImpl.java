package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.up.lala.boot.dto.NoticeQueryDTO;
import com.jerry.up.lala.boot.dto.UserDTO;
import com.jerry.up.lala.boot.dto.UserQueryDTO;
import com.jerry.up.lala.boot.entity.Notice;
import com.jerry.up.lala.boot.entity.NoticeUser;
import com.jerry.up.lala.boot.enums.NoticeSendState;
import com.jerry.up.lala.boot.enums.NoticeType;
import com.jerry.up.lala.boot.error.BootErrors;
import com.jerry.up.lala.boot.mapper.NoticeMapper;
import com.jerry.up.lala.boot.service.NoticeService;
import com.jerry.up.lala.boot.service.NoticeUserService;
import com.jerry.up.lala.boot.service.UserService;
import com.jerry.up.lala.boot.vo.*;
import com.jerry.up.lala.framework.core.common.DataBody;
import com.jerry.up.lala.framework.core.common.Errors;
import com.jerry.up.lala.framework.core.common.PageR;
import com.jerry.up.lala.framework.core.data.DataUtil;
import com.jerry.up.lala.framework.core.exception.ServiceException;
import com.jerry.up.lala.framework.core.data.CheckUtil;
import com.jerry.up.lala.framework.core.data.PageUtil;
import com.jerry.up.lala.framework.core.data.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【notice(通知表)】的数据库操作Service实现
 * @createDate 2024-01-10 09:51:19
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeUserService noticeUserService;

    @Override
    public PageR<NoticeVO> pageQuery(NoticeQueryVO noticeQueryVO) {
        Page<Notice> page = PageUtil.initPage(noticeQueryVO);
        try {
            NoticeQueryDTO queryDTO = DataUtil.toBean(noticeQueryVO, NoticeQueryDTO.class);
            LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
            // 标题
            String title = queryDTO.getTitle();
            if (StringUtil.isNotNull(title)) {
                queryWrapper.like(Notice::getTitle, title);
            }

            // 类型【1-消息，2-公告】
            Integer noticeType = queryDTO.getNoticeType();
            if (noticeType != null) {
                queryWrapper.eq(Notice::getNoticeType, noticeType);
            }

            // 发送状态 【0-未发送，1-已发送】
            Integer sendState = queryDTO.getSendState();
            if (sendState != null) {
                queryWrapper.eq(Notice::getSendState, sendState);
            }

            // 发送时间
            Date sendTimeStart = queryDTO.getSendTimeStart();
            if (sendTimeStart != null) {
                queryWrapper.ge(Notice::getSendTime, sendTimeStart);
            }
            Date sendTimeEnd = queryDTO.getSendTimeEnd();
            if (sendTimeEnd != null) {
                queryWrapper.le(Notice::getSendTime, sendTimeEnd);
            }

            Page<Notice> pageResult = page(page, queryWrapper);
            return PageUtil.toPageR(pageResult, NoticeVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public NoticeInfoVO info(Long id) {
        Notice notice = selectById(id);
        try {
            NoticeInfoVO result = DataUtil.toBean(notice, NoticeInfoVO.class);
            List<NoticeUser> noticeUserList = noticeUserService.list(new LambdaQueryWrapper<NoticeUser>().eq(NoticeUser::getNoticeId, id));
            result.setUserIds(noticeUserList.stream().map(NoticeUser::getUserId).collect(Collectors.toList()));
            return result;
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public PageR<NoticeUserAllVO> userPage(NoticeUserAllQueryVO noticeUserAllQueryVO) {
        return noticeUserService.noticeQuery(noticeUserAllQueryVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(NoticeSaveVO noticeSaveVO) {
        List<UserDTO> userList = checkSaveArgs(noticeSaveVO);
        try {
            // 入库消息表
            Notice notice = DataUtil.toBean(noticeSaveVO, Notice.class);
            notice.setSendState(NoticeSendState.UNSENT.getValue());
            save(notice);
            // 入库消息用户表
            noticeUserService.save(notice.getId(), userList);
        } catch (Exception e) {
            throw ServiceException.error(Errors.SAVE_ERROR, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(Long id, NoticeSaveVO noticeSaveVO) {
        Notice notice = checkNotice(id);
        List<UserDTO> userList = checkSaveArgs(noticeSaveVO);
        try {
            DataUtil.copy(noticeSaveVO, notice);
            updateById(notice);
            // 入库消息用户表
            noticeUserService.save(notice.getId(), userList);
        } catch (Exception e) {
            throw ServiceException.error(Errors.UPDATE_ERROR, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(Long id) {
        Notice notice = checkNotice(id);
        try {
            notice.setSendState(NoticeSendState.SENT.getValue());
            notice.setSendTime(new Date());
            updateById(notice);
            // 发送状态更新
            noticeUserService.send(notice.getId());
        } catch (Exception e) {
            throw ServiceException.error(Errors.UPDATE_ERROR, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Notice notice = checkNotice(id);
        try {
            removeById(notice);
            // 删除用户消息
            noticeUserService.delete(notice.getId());
        } catch (Exception e) {
            throw ServiceException.error(Errors.DELETE_ERROR, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(DataBody<List<Long>> dataBody) {
        List<Long> ids = CheckUtil.dataNull(dataBody);
        List<Notice> noticeList = listByIds(ids);
        if (CollectionUtil.isEmpty(noticeList)) {
            throw ServiceException.notFound();
        }
        boolean send = noticeList.stream().anyMatch(item -> !NoticeSendState.UNSENT.getValue().equals(item.getSendState()));
        if (send) {
            throw ServiceException.error(BootErrors.NOTICE_SEND_STATE_UNSENT);
        }
        List<Long> noticeIds = noticeList.stream().map(Notice::getId).collect(Collectors.toList());
        removeByIds(noticeIds);
        // 删除用户消息
        noticeUserService.delete(noticeIds);
    }

    private List<UserDTO> checkSaveArgs(NoticeSaveVO noticeSaveVO) {
        // 标题
        String title = noticeSaveVO.getTitle();
        // 类型【1-消息，2-公告】
        Integer noticeType = noticeSaveVO.getNoticeType();
        // 通知内容
        String html = noticeSaveVO.getHtml();
        // 接收账号ID列表
        List<String> userIds = noticeSaveVO.getUserIds();
        if (StringUtil.isNull(title) || NoticeType.fromValue(noticeType) == null || StringUtil.isNull(html) || CollectionUtil.isEmpty(userIds)) {
            throw ServiceException.args();
        }
        // 查询目标用户
        List<UserDTO> userList = userService.listDTO(new UserQueryDTO().setState(true).setIds(userIds), null);
        if (CollectionUtil.isEmpty(userList)) {
            throw ServiceException.error(BootErrors.NOTICE_USER_EMPTY);
        }
        return userList;
    }

    private Notice checkNotice(Long id) {
        Notice notice = selectById(id);
        // 未发送状态才允许操作
        if (!NoticeSendState.UNSENT.getValue().equals(notice.getSendState())) {
            throw ServiceException.error(BootErrors.NOTICE_SEND_STATE_UNSENT);
        }
        return notice;
    }

    private Notice selectById(Long id) {
        if (id == null) {
            throw ServiceException.args();
        }
        Notice notice = getById(id);
        if (notice == null) {
            throw ServiceException.notFound();
        }
        return notice;
    }
}




