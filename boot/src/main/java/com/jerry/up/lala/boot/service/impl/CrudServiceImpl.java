package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.up.lala.boot.bo.CrudExcelBO;
import com.jerry.up.lala.boot.dto.CrudQueryDTO;
import com.jerry.up.lala.boot.entity.Crud;
import com.jerry.up.lala.boot.enums.SysDictKey;
import com.jerry.up.lala.boot.mapper.CrudMapper;
import com.jerry.up.lala.boot.service.CrudService;
import com.jerry.up.lala.boot.service.SysDictItemService;
import com.jerry.up.lala.boot.vo.CrudExportQueryVO;
import com.jerry.up.lala.boot.vo.CrudInfoVO;
import com.jerry.up.lala.boot.vo.CrudQueryVO;
import com.jerry.up.lala.boot.vo.CrudSaveVO;
import com.jerry.up.lala.framework.core.common.*;
import com.jerry.up.lala.framework.core.data.DataUtil;
import com.jerry.up.lala.framework.core.excel.ExcelCheckErrorBO;
import com.jerry.up.lala.framework.core.excel.ExcelUtil;
import com.jerry.up.lala.framework.core.exception.ServiceException;
import com.jerry.up.lala.framework.core.satoken.SaTokenUtil;
import com.jerry.up.lala.framework.core.data.CheckUtil;
import com.jerry.up.lala.framework.core.data.PageUtil;
import com.jerry.up.lala.framework.core.data.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 针对表【crud(crud表)】的数据库操作Service实现
 *
 * @author FMJ
 */
@Service
public class CrudServiceImpl extends ServiceImpl<CrudMapper, Crud> implements CrudService {

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private MultipartProperties multipartProperties;

    @Autowired
    private SysDictItemService sysDictItemService;

    @Override
    public PageR<CrudInfoVO> pageQuery(CrudQueryVO crudQueryVO) {
        CrudQueryDTO queryDTO = DataUtil.toBean(crudQueryVO, CrudQueryDTO.class);
        IPage<Crud> pageResult = pageByDTO(crudQueryVO, queryDTO);
        try {
            return PageUtil.toPageR(pageResult, CrudInfoVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public CrudInfoVO info(String id) {
        Crud crud = get(id);
        return DataUtil.toBean(crud, CrudInfoVO.class);
    }

    @Override
    public void add(CrudSaveVO crudSaveVO) {
        checkCrudSaveVO(crudSaveVO);
        try {
            Crud crud = DataUtil.toBean(crudSaveVO, Crud.class);
            save(crud);
        } catch (Exception e) {
            throw ServiceException.error(Errors.SAVE_ERROR, e);
        }
    }

    @Override
    public void update(String id, CrudSaveVO crudSaveVO) {
        checkCrudSaveVO(crudSaveVO);
        Crud oldCrud = get(id);
        try {
            DataUtil.copy(crudSaveVO, oldCrud);
            updateById(oldCrud);
        } catch (Exception e) {
            throw ServiceException.error(Errors.UPDATE_ERROR, e);
        }
    }

    @Override
    public void delete(String id) {
        if (StringUtil.isNull(id)) {
            throw ServiceException.args();
        }
        try {
            removeById(id);
        } catch (Exception e) {
            throw ServiceException.error(Errors.DELETE_ERROR, e);
        }
    }

    @Override
    public void batchDelete(DataBody<List<String>> dataBody) {
        List<String> value = CheckUtil.dataNull(dataBody);
        try {
            remove(new LambdaQueryWrapper<Crud>().in(Crud::getId, value));
        } catch (Exception e) {
            throw ServiceException.error(Errors.DELETE_ERROR, e);
        }
    }

    @Override
    public Integer upload(MultipartFile file) {
        List<CrudExcelBO> uploadList = ExcelUtil.read(file, CrudExcelBO.class);
        String checkUpload = checkUpload(uploadList);
        if (StringUtil.isNotNull(checkUpload)) {
            throw ServiceException.error(Errors.dynamicMsgError(Errors.UPLOAD_ERROR, checkUpload));
        }
        try {
            Map<String, String> stateLabelValueMap = sysDictItemService.labelValueMap(SysDictKey.STATE);

            Map<String, String> listLabelValueMap = sysDictItemService.labelValueMap(SysDictKey.CRUD_LIST);

            Map<String, String> treeLabelValueMap = sysDictItemService.labelValueMap(SysDictKey.CRUD_TREE);

            List<Crud> crudList = uploadList.stream().map(
                    item -> {
                        Crud crud = DataUtil.toBean(item, Crud.class);
                        crud.setId(IdWorker.getIdStr());
                        crud.setSwitchInfo(stateLabelValueMap.get(item.getSwitchInfo()));

                        crud.setRadio(listLabelValueMap.get(item.getRadio()));
                        crud.setSelectInfo(listLabelValueMap.get(item.getSelectInfo()));

                        crud.setCascader(treeLabelValueMap.get(item.getCascader()));
                        crud.setTreeSelect(treeLabelValueMap.get(item.getTreeSelect()));

                        crud.setCheckboxes(DataUtil.valuesStr(item.getCheckboxes(), listLabelValueMap));
                        crud.setTransfers(DataUtil.valuesStr(item.getTransfers(), treeLabelValueMap));
                        crud.setCreateUser(SaTokenUtil.currentUser().getUserId());
                        crud.setCreateTime(new Date());
                        crud.setDeleted(false);
                        return crud;
                    }
            ).collect(Collectors.toList());

            List<List<Crud>> partitionList = ListUtil.partition(crudList, commonProperties.getUpload().getPartition());

            partitionList.forEach(item -> getBaseMapper().insertBatch(item));

            return crudList.size();
        } catch (Exception e) {
            throw ServiceException.error(Errors.UPLOAD_ERROR, e);
        }
    }

    @Override
    public Object export(CrudExportQueryVO crudExportQueryVO) {
        CrudQueryDTO queryDTO = DataUtil.toBean(crudExportQueryVO, CrudQueryDTO.class);
        List<Crud> list = BooleanUtil.isTrue(crudExportQueryVO.getCurrentPage()) ?
                pageByDTO(crudExportQueryVO, queryDTO.setIds(null)).getRecords() : listByDTO(queryDTO);
        if (CollectionUtil.isEmpty(list)) {
            throw ServiceException.error(Errors.EXPORT_EMPTY_ERROR);
        }
        Map<String, String> listValueLabelMap = sysDictItemService.valueLabelMap(SysDictKey.CRUD_LIST);

        Map<String, String> treeValueLabelMap = sysDictItemService.valueLabelMap(SysDictKey.CRUD_TREE);

        Map<String, String> stateValueLabelMap = sysDictItemService.valueLabelMap(SysDictKey.STATE);


        List<CrudExcelBO> exportList = list.stream().map(item -> {
            CrudExcelBO crudExcelBO = DataUtil.toBean(item, CrudExcelBO.class);
            // 开关(STATE),单选
            crudExcelBO.setSwitchInfo(stateValueLabelMap.get(String.valueOf(item.getSwitchInfo())));
            // 单选框(CRUD_LIST),单选
            crudExcelBO.setRadio(listValueLabelMap.get(String.valueOf(item.getRadio())));
            // 复选框(CRUD_LIST),多选
            crudExcelBO.setCheckboxes(DataUtil.valuesStr(item.getCheckboxes(), listValueLabelMap));
            // 选择器(CRUD_LIST),单选
            crudExcelBO.setSelectInfo(listValueLabelMap.get(String.valueOf(item.getSelectInfo())));
            // 级联选择(CRUD_TREE),单选
            crudExcelBO.setCascader(treeValueLabelMap.get(item.getCascader()));
            // 树选择(CRUD_TREE),单选
            crudExcelBO.setTreeSelect(treeValueLabelMap.get(item.getTreeSelect()));
            // 数据穿梭框(CRUD_LIST),多选
            crudExcelBO.setTransfers(DataUtil.valuesStr(item.getTransfers(), listValueLabelMap));
            return crudExcelBO;
        }).collect(Collectors.toList());


        String fileName = "crud导出-" + DatePattern.PURE_DATETIME_MS_FORMAT.format(new Date()) + ".xlsx";
        return ExcelUtil.export(fileName, exportList, CrudExcelBO.class);
    }

    private List<Crud> listByDTO(CrudQueryDTO crudQueryDTO) {
        try {
            LambdaQueryWrapper<Crud> queryWrapper = loadQuery(crudQueryDTO);
            return list(queryWrapper);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    private IPage<Crud> pageByDTO(PageQuery pageQuery, CrudQueryDTO crudQueryDTO) {
        Page<Crud> page = PageUtil.initPage(pageQuery);
        try {
            LambdaQueryWrapper<Crud> queryWrapper = loadQuery(crudQueryDTO);
            return page(page, queryWrapper);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    private Crud get(String id) {
        if (StringUtil.isNull(id)) {
            throw ServiceException.args();
        }
        Crud crud = getById(id);
        if (crud == null) {
            throw ServiceException.notFound();
        }
        return crud;
    }

    private void checkCrudSaveVO(CrudSaveVO crudSaveVO) {
        if (crudSaveVO == null) {
            throw ServiceException.args();
        }
        if (crudSaveVO.getRate() != null && new BigDecimal(5).compareTo(crudSaveVO.getRate()) < 0) {
            throw ServiceException.args();
        }
    }

    private LambdaQueryWrapper<Crud> loadQuery(CrudQueryDTO crudQueryDTO) {
        LambdaQueryWrapper<Crud> queryWrapper = new LambdaQueryWrapper<>();
        if (crudQueryDTO != null) {
            List<String> ids = crudQueryDTO.getIds();
            if (CollectionUtil.isNotEmpty(ids)) {
                queryWrapper.in(Crud::getId, ids);
            }
            String input = crudQueryDTO.getInput();
            if (StringUtil.isNotNull(input)) {
                queryWrapper.like(Crud::getInput, input);
            }

            BigDecimal inputNumberStart = crudQueryDTO.getInputNumberStart();
            if (inputNumberStart != null) {
                queryWrapper.ge(Crud::getInputNumber, inputNumberStart);
            }

            BigDecimal inputNumberEnd = crudQueryDTO.getInputNumberEnd();
            if (inputNumberEnd != null) {
                queryWrapper.le(Crud::getInputNumber, inputNumberEnd);
            }

            List<String> inputTagList = crudQueryDTO.getInputTagList();
            if (CollectionUtil.isNotEmpty(inputTagList)) {
                inputTagList.forEach(item -> queryWrapper.apply(" FIND_IN_SET(" + item + ", input_tags) "));
            }

            String autoComplete = crudQueryDTO.getAutoComplete();
            if (StringUtil.isNotNull(autoComplete)) {
                queryWrapper.like(Crud::getAutoComplete, autoComplete);
            }

            String mention = crudQueryDTO.getMention();
            if (StringUtil.isNotNull(mention)) {
                queryWrapper.like(Crud::getMention, mention);
            }

            String textArea = crudQueryDTO.getTextArea();
            if (StringUtil.isNotNull(textArea)) {
                queryWrapper.like(Crud::getTextArea, textArea);
            }

            BigDecimal rateStart = crudQueryDTO.getRateStart();
            if (rateStart != null) {
                queryWrapper.ge(Crud::getRate, rateStart);
            }

            BigDecimal rateEnd = crudQueryDTO.getRateEnd();
            if (rateEnd != null) {
                queryWrapper.le(Crud::getRate, rateEnd);
            }

            Integer sliderStart = crudQueryDTO.getSliderStart();
            if (sliderStart != null) {
                queryWrapper.ge(Crud::getSlider, sliderStart);
            }

            Integer sliderEnd = crudQueryDTO.getSliderEnd();
            if (sliderEnd != null) {
                queryWrapper.le(Crud::getSlider, sliderEnd);
            }

            String switchInfo = crudQueryDTO.getSwitchInfo();
            if (StringUtil.isNotNull(switchInfo)) {
                queryWrapper.eq(Crud::getSwitchInfo, switchInfo);
            }

            List<String> radioList = crudQueryDTO.getRadioList();
            if (CollectionUtil.isNotEmpty(radioList)) {
                queryWrapper.in(Crud::getRadio, radioList);
            }

            List<String> checkboxList = crudQueryDTO.getCheckboxList();
            if (CollectionUtil.isNotEmpty(checkboxList)) {
                queryWrapper.apply(checkboxList.stream().map(item -> " FIND_IN_SET(" + item + ", checkboxes) ")
                        .collect(Collectors.joining(" or ")));
            }

            List<String> selectInfoList = crudQueryDTO.getSelectInfoList();
            if (CollectionUtil.isNotEmpty(selectInfoList)) {
                queryWrapper.in(Crud::getSelectInfo, selectInfoList);
            }

            List<String> cascaderList = crudQueryDTO.getCascaderList();
            if (CollectionUtil.isNotEmpty(cascaderList)) {
                queryWrapper.in(Crud::getCascader, sysDictItemService.dictValuesList(SysDictKey.CRUD_TREE, cascaderList));
            }

            List<String> treeSelectList = crudQueryDTO.getTreeSelectList();
            if (CollectionUtil.isNotEmpty(treeSelectList)) {
                queryWrapper.in(Crud::getTreeSelect, sysDictItemService.dictValuesList(SysDictKey.CRUD_TREE, treeSelectList));
            }

            Date dateTimePickerStart = crudQueryDTO.getDateTimePickerStart();
            if (dateTimePickerStart != null) {
                queryWrapper.ge(Crud::getDateTimePicker, dateTimePickerStart);
            }

            Date dateTimePickerEnd = crudQueryDTO.getDateTimePickerEnd();
            if (dateTimePickerEnd != null) {
                queryWrapper.le(Crud::getDateTimePicker, dateTimePickerEnd);
            }

            List<String> transferList = crudQueryDTO.getTransferList();
            if (CollectionUtil.isNotEmpty(transferList)) {
                queryWrapper.apply(sysDictItemService.dictValuesList(SysDictKey.CRUD_TREE, transferList)
                        .stream().map(item -> " FIND_IN_SET(" + item + ", transfers) ")
                        .collect(Collectors.joining(" or ")));
            }

        }
        return queryWrapper;
    }

    private String checkUpload(List<CrudExcelBO> uploadList) {
        if (CollectionUtil.isEmpty(uploadList)) {
            return StringUtil.fontRed("导入数据不能为空");
        }
        Integer max = commonProperties.getUpload().getMax();
        if (uploadList.size() > commonProperties.getUpload().getMax()) {
            return StringUtil.fontRed("导入数据不能超过" + max + "条");
        }
        List<String> stateLabels = sysDictItemService.dictLabelsList(SysDictKey.STATE);

        List<String> listLabels = sysDictItemService.dictLabelsList(SysDictKey.CRUD_LIST);

        List<String> treeLabels = sysDictItemService.dictLabelsList(SysDictKey.CRUD_TREE);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < uploadList.size(); i++) {
            CrudExcelBO crudExcelBO = uploadList.get(i);
            List<ExcelCheckErrorBO> rowCheckList = ExcelUtil.check(crudExcelBO, (fieldName, value) -> {
                if (LambdaUtil.getFieldName(CrudExcelBO::getSwitchInfo).equals(fieldName)) {
                    return checkCrudLabel(stateLabels, value);
                }
                if (LambdaUtil.getFieldName(CrudExcelBO::getRadio).equals(fieldName)) {
                    return checkCrudLabel(listLabels, value);
                }
                if (LambdaUtil.getFieldName(CrudExcelBO::getSelectInfo).equals(fieldName)) {
                    return checkCrudLabel(listLabels, value);
                }
                if (LambdaUtil.getFieldName(CrudExcelBO::getCascader).equals(fieldName)) {
                    return checkCrudLabel(treeLabels, value);
                }
                if (LambdaUtil.getFieldName(CrudExcelBO::getTreeSelect).equals(fieldName)) {
                    return checkCrudLabel(treeLabels, value);
                }
                if (LambdaUtil.getFieldName(CrudExcelBO::getCheckboxes).equals(fieldName)) {
                    return checkCrudLabelMultiple(listLabels, value);
                }
                if (LambdaUtil.getFieldName(CrudExcelBO::getTransfers).equals(fieldName)) {
                    return checkCrudLabelMultiple(listLabels, value);
                }
                return null;
            });
            if (CollectionUtil.isNotEmpty(rowCheckList)) {
                for (ExcelCheckErrorBO rowCheck : rowCheckList) {
                    result.append(ExcelUtil.checkMessage(i + 1, rowCheck));
                }
            }
        }
        return result.toString();
    }

    private ExcelCheckErrorBO checkCrudLabel(List<String> labelList, Object value) {
        String checkValue = ObjectUtil.toString(value);
        if (StringUtil.isNotNull(checkValue)) {
            if (!CollectionUtil.contains(labelList, checkValue)) {
                return new ExcelCheckErrorBO().setValue(checkValue).setErrorMessage("必须为" + StrUtil.join(",", labelList));
            }
        }
        return null;
    }

    private ExcelCheckErrorBO checkCrudLabelMultiple(List<String> labelList, Object value) {
        String checkValue = ObjectUtil.toString(value);
        if (StringUtil.isNotNull(checkValue)) {
            String[] labelArray = labelList.toArray(new String[0]);
            if (!ArrayUtil.containsAll(labelArray, checkValue.replace("，", ",").split(","))) {
                return new ExcelCheckErrorBO().setValue(checkValue)
                        .setErrorMessage("必须为" + StrUtil.join(",", labelList) + ",并用逗号分隔");
            }
        }
        return null;
    }

}




