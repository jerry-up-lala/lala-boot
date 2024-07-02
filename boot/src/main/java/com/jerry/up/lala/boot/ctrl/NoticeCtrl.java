package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.access.AccessNoticeConstant;
import com.jerry.up.lala.boot.service.NoticeService;
import com.jerry.up.lala.boot.vo.*;
import com.jerry.up.lala.framework.core.common.Api;
import com.jerry.up.lala.framework.core.common.DataBody;
import com.jerry.up.lala.framework.core.common.PageR;
import com.jerry.up.lala.framework.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: 通知管理
 *
 * @author FMJ
 * @date 2024/1/9 17:14
 */
@RestController
@RequestMapping("/notice")
public class NoticeCtrl {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/page")
    @Api(value = "通知管理-查询", accessCodes = {AccessNoticeConstant.NOTICE, AccessNoticeConstant.NOTICE_QUERY})
    public R<PageR<NoticeVO>> page(NoticeQueryVO noticeQueryVO) {
        PageR<NoticeVO> pageR = noticeService.pageQuery(noticeQueryVO);
        return R.succeed(pageR);
    }

    @GetMapping("/info/{id}")
    @Api(value = "通知管理-详情", accessCodes = {AccessNoticeConstant.NOTICE_ADD, AccessNoticeConstant.NOTICE_UPDATE})
    public R<NoticeInfoVO> info(@PathVariable("id") Long id) {
        NoticeInfoVO noticeInfoVO = noticeService.info(id);
        return R.succeed(noticeInfoVO);
    }

    @GetMapping("/user-page")
    @Api(value = "通知管理-接收情况", accessCodes = AccessNoticeConstant.NOTICE_USER)
    public R<PageR<NoticeUserAllVO>> userPage(NoticeUserAllQueryVO noticeUserAllQueryVO) {
        PageR<NoticeUserAllVO> pageR = noticeService.userPage(noticeUserAllQueryVO);
        return R.succeed(pageR);
    }

    @PostMapping
    @Api(value = "通知管理-新增", accessCodes = AccessNoticeConstant.NOTICE_ADD)
    public R add(@RequestBody NoticeSaveVO noticeSaveVO) {
        noticeService.add(noticeSaveVO);
        return R.empty();
    }

    @PutMapping("/{id}")
    @Api(value = "通知管理-编辑", accessCodes = AccessNoticeConstant.NOTICE_UPDATE)
    public R edit(@PathVariable("id") Long id, @RequestBody NoticeSaveVO noticeSaveVO) {
        noticeService.edit(id, noticeSaveVO);
        return R.empty();
    }

    @PutMapping("/send/{id}")
    @Api(value = "通知管理-发送", accessCodes = AccessNoticeConstant.NOTICE_SEND)
    public R send(@PathVariable("id") Long id) {
        noticeService.send(id);
        return R.empty();
    }

    @DeleteMapping("/{id}")
    @Api(value = "通知管理-删除", accessCodes = AccessNoticeConstant.NOTICE_DELETE)
    public R delete(@PathVariable("id") Long id) {
        noticeService.delete(id);
        return R.empty();
    }

    @DeleteMapping
    @Api(value = "通知管理-批量删除", accessCodes = AccessNoticeConstant.NOTICE_BATCH_DELETE)
    public R batchDelete(@RequestBody DataBody<List<Long>> dataBody) {
        noticeService.batchDelete(dataBody);
        return R.empty();
    }

}
