package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.service.NoticeUserService;
import com.jerry.up.lala.boot.vo.NoticeInfoUserVO;
import com.jerry.up.lala.boot.vo.NoticeUserQueryVO;
import com.jerry.up.lala.boot.vo.NoticeUserVO;
import com.jerry.up.lala.framework.core.common.Api;
import com.jerry.up.lala.framework.core.common.DataBody;
import com.jerry.up.lala.framework.core.common.PageR;
import com.jerry.up.lala.framework.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 我的消息
 *
 * @author FMJ
 * @date 2024/1/9 17:14
 */
@RestController
@RequestMapping("/notice-user")
public class NoticeUserCtrl {

    @Autowired
    private NoticeUserService noticeUserService;

    @GetMapping("/page")
    @Api(value = "我的消息-查询")
    public R<PageR<NoticeUserVO>> page(NoticeUserQueryVO noticeUserQueryVO) {
        PageR<NoticeUserVO> pageR = noticeUserService.pageQuery(noticeUserQueryVO);
        return R.succeed(pageR);
    }

    @GetMapping("/unread/count")
    @Api(value = "我的消息-未读数量")
    public R<Map<Integer, Long>> unreadCount() {
        Map<Integer, Long> groupCountMap = noticeUserService.unreadCount();
        return R.succeed(groupCountMap);
    }

    @GetMapping("/info/{id}")
    @Api(value = "我的消息-详情")
    public R<NoticeInfoUserVO> info(@PathVariable("id") Long id) {
        NoticeInfoUserVO noticeUserInfoVO = noticeUserService.info(id);
        return R.succeed(noticeUserInfoVO);
    }

    @PutMapping("/read")
    @Api(value = "我的消息-已读")
    public R read(DataBody<List<Long>> dataBody) {
        noticeUserService.read(dataBody);
        return R.empty();
    }

    @PutMapping("/read/all")
    @Api(value = "我的消息-全部已读")
    public R readAll() {
        noticeUserService.readAll();
        return R.empty();
    }

    @DeleteMapping("/{id}")
    @Api(value = "我的消息-删除")
    public R delete(@PathVariable("id") Long id) {
        noticeUserService.userDelete(id);
        return R.empty();
    }

    @DeleteMapping
    @Api(value = "我的消息-批量删除")
    public R batchDelete(@RequestBody DataBody<List<Long>> dataBody) {
        noticeUserService.userBatchDelete(dataBody);
        return R.empty();
    }


}
