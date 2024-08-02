package com.jerry.up.lala.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.up.lala.boot.entity.Crud;
import com.jerry.up.lala.boot.vo.CrudExportQueryVO;
import com.jerry.up.lala.boot.vo.CrudInfoVO;
import com.jerry.up.lala.boot.vo.CrudQueryVO;
import com.jerry.up.lala.boot.vo.CrudSaveVO;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.r.PageR;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 针对表【crud(crud表)】的数据库操作Service
 *
 * @author FMJ
 */
public interface CrudService extends IService<Crud> {

    /**
     * <p>Description: 分页查询
     *
     * @param crudQueryVO 查询条件
     * @return 分页数据
     * @author FMJ
     * @date 2023/8/16 17:58
     * @since v1.0.0
     */
    PageR<CrudInfoVO> pageQuery(CrudQueryVO crudQueryVO);

    /**
     * <p>Description: 查询
     *
     * @param id 主键
     * @return crud信息
     * @author FMJ
     * @date 2023/8/16 16:02
     * @since v1.0.0
     */
    CrudInfoVO info(String id);

    /**
     * <p>Description: 新增
     *
     * @param crudSaveVO 表单
     * @author FMJ
     * @date 2023/8/16 16:02
     * @since v1.0.0
     */
    void add(CrudSaveVO crudSaveVO);

    /**
     * <p>Description: 更新
     *
     * @param id         主键
     * @param crudSaveVO 表单
     * @author FMJ
     * @date 2023/8/16 16:02
     * @since v1.0.0
     */
    void update(String id, CrudSaveVO crudSaveVO);

    /**
     * <p>Description: 删除
     *
     * @param id 主键
     * @author FMJ
     * @date 2023/8/16 16:02
     * @since v1.0.0
     */
    void delete(String id);

    /**
     * <p>Description: 批量删除
     *
     * @param dataBody 主键ID
     * @author FMJ
     * @date 2023/10/26 16:02
     * @since v1.0.0
     */
    void batchDelete(DataBody<List<String>> dataBody);

    /**
     * <p>Description: 上传文件
     *
     * @param file 文件
     * @return 上传数量
     * @author FMJ
     * @date 2023/11/14 14:52
     * @since v1.0.0
     */
    Integer upload(MultipartFile file);

    /**
     * <p>Description: 导出数据
     * @param crudExportQueryVO 查询条件
     * @return 导出流
     * @author FMJ
     * @date 2023/11/15 23:02
     * @since v1.0.0
     */
    Object export(CrudExportQueryVO crudExportQueryVO);
}
