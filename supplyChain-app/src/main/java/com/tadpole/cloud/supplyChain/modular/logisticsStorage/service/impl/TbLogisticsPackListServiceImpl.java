package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.model.result.FileInfoResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper.TbLogisticsPackListMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.CloseLogisticsInGoodsParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.CreateBindPackListParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.LogisticsInGoodsModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.LogisticsSingleBoxRecModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListResultModel;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.utils.PackListImportListener;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.utils.createPackListModel.*;
import com.tadpole.cloud.supplyChain.modular.manage.consumer.FileConsumer;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 亚马逊货件;(tb_logistics_pack_list)表服务实现类
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@Service
@Transactional
@Slf4j
public class TbLogisticsPackListServiceImpl extends ServiceImpl<TbLogisticsPackListMapper, TbLogisticsPackList> implements TbLogisticsPackListService {
    @Resource
    private TbLogisticsPackListMapper tbLogisticsPackListMapper;

    @Resource
    TbLogisticsPackListDetService tbLogisticsPackListDetService;
    @Resource
    TbLogisticsPackListDetToBoxService tbLogisticsPackListDetToBoxService;
    @Resource
    TbLogisticsPackListBoxRecDetService tbLogisticsPackListBoxRecDetService;

    @Resource
    TbLogisticsPackListBoxRecService tbLogisticsPackListBoxRecService;

    @Resource
    TbLogisticsPackingListService tbLogisticsPackingListService;

    @Resource
    TbBscOverseasWayService tbBscOverseasWayService;

    @Resource
    TbLogisticsPackingListDet1Service tbLogisticsPackingListDet1Service;

    @Resource
    TbLogisticsClaimToAmazonService tbLogisticsClaimToAmazonService;

    @Resource
    TbLogisticsClaimDetToAmazonService tbLogisticsClaimDetToAmazonService;

    @Resource
    FileConsumer fileConsumer;

    @Resource
    Environment env;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackList queryById(BigDecimal id) {
        return tbLogisticsPackListMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public PageResult<TbLogisticsPackListResultModel> pageQuery(TbLogisticsPackListParam param) {
        Page pageContext = param.getPageContext();
        IPage<TbLogisticsPackListResultModel> page = this.baseMapper.queryPage(pageContext, param);

        return new PageResult<>(page);

    }

    /**
     * 新增数据
     *
     * @param tbLogisticsPackList 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackList insert(TbLogisticsPackList tbLogisticsPackList) {
        tbLogisticsPackListMapper.insert(tbLogisticsPackList);
        return tbLogisticsPackList;
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public TbLogisticsPackList update(TbLogisticsPackListParam param) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbLogisticsPackList> wrapper = new LambdaUpdateChainWrapper<TbLogisticsPackList>(tbLogisticsPackListMapper);
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackCode()), TbLogisticsPackList::getPackCode, param.getPackCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPlanName()), TbLogisticsPackList::getPlanName, param.getPlanName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackTempUpPerName()), TbLogisticsPackList::getPackTempUpPerName, param.getPackTempUpPerName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackTempName()), TbLogisticsPackList::getPackTempName, param.getPackTempName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getShipmentId()), TbLogisticsPackList::getShipmentId, param.getShipmentId());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPlanId()), TbLogisticsPackList::getPlanId, param.getPlanId());
        wrapper.set(ObjectUtil.isNotEmpty(param.getShipTo()), TbLogisticsPackList::getShipTo, param.getShipTo());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackGenPerName()), TbLogisticsPackList::getPackGenPerName, param.getPackGenPerName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackAmaRecState()), TbLogisticsPackList::getPackAmaRecState, param.getPackAmaRecState());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackAmaRecStatePerName()), TbLogisticsPackList::getPackAmaRecStatePerName, param.getPackAmaRecStatePerName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getCountryCode()), TbLogisticsPackList::getCountryCode, param.getCountryCode());
        wrapper.set(ObjectUtil.isNotEmpty(param.getShopNameSimple()), TbLogisticsPackList::getShopNameSimple, param.getShopNameSimple());
        wrapper.set(ObjectUtil.isNotEmpty(param.getComWarehouseType()), TbLogisticsPackList::getComWarehouseType, param.getComWarehouseType());
        wrapper.set(ObjectUtil.isNotEmpty(param.getComWarehouseName()), TbLogisticsPackList::getComWarehouseName, param.getComWarehouseName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackShipmentRealStatus()), TbLogisticsPackList::getPackShipmentRealStatus, param.getPackShipmentRealStatus());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusPackRemark()), TbLogisticsPackList::getBusPackRemark, param.getBusPackRemark());
        wrapper.set(ObjectUtil.isNotEmpty(param.getBusShipmentName()), TbLogisticsPackList::getBusShipmentName, param.getBusShipmentName());
        wrapper.set(ObjectUtil.isNotEmpty(param.getPackListCode()), TbLogisticsPackList::getPackListCode, param.getPackListCode());
        //2. 设置主键，并更新
        wrapper.eq(TbLogisticsPackList::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(param.getId());
        } else {
            return null;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(BigDecimal id) {
        int total = tbLogisticsPackListMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> idList) {
        int delCount = tbLogisticsPackListMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 通过ShipmentID查询亚马逊货件明细数据
     *
     * @param shipmentId
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Map<String, Object> getLogisticsPackListDetail(String shipmentId) {

        List<TbLogisticsPackListDet> packListDetList = tbLogisticsPackListDetService.queryByShipmentId(shipmentId);
        List<TbLogisticsPackListDetToBox> packListDetToBoxes = tbLogisticsPackListDetToBoxService.queryByShipmentId(shipmentId);
        List<TbLogisticsPackListBoxRecDet> packListBoxRecDets = tbLogisticsPackListBoxRecDetService.queryByShipmentId(shipmentId);
        Map<String, Object> map = new HashMap<>();
        map.put("tbLogisticsPackListBoxRecDetList", packListBoxRecDets);
        map.put("tbLogisticsPackListDetList", packListDetList);
        map.put("tbLogisticsPackListDetToBoxList", packListDetToBoxes);
        return map;
    }

    @DataSource(name = "logistics")
    @Override
    public List<LogisticsSingleBoxRecModel> getSingleBoxRec(String shipmentId, String sku) {
        return tbLogisticsPackListMapper.getSingleBoxRec(shipmentId, sku);
    }


    @DataSource(name = "logistics")
    @Override
    public Map<String, Object> getLogisticsPackListDetailNew(String packListCode) {

        List<TbLogisticsPackListDet> packListDetList = tbLogisticsPackListDetService.queryByPackListCode(packListCode);
        List<TbLogisticsPackListDetToBox> packListDetToBoxes = tbLogisticsPackListDetToBoxService.queryByPackListCode(packListCode);
        List<TbLogisticsPackListBoxRecDet> packListBoxRecDets = tbLogisticsPackListBoxRecDetService.queryByPackListCode(packListCode);
        Map<String, Object> map = new HashMap<>();
        map.put("tbLogisticsPackListBoxRecDetList", packListBoxRecDets);
        map.put("tbLogisticsPackListDetList", packListDetList);
        map.put("tbLogisticsPackListDetToBoxList", packListDetToBoxes);
        return map;
    }

    @DataSource(name = "logistics")
    @Override
    public List<LogisticsSingleBoxRecModel> getSingleBoxRecNew(String packListCode, String sku) {
        return tbLogisticsPackListMapper.getSingleBoxRecNew(packListCode, sku);
    }

    /**
     * 导入PackList
     *
     * @param param
     * @param file
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public ResponseData packListImportNew(TbLogisticsPackListParam param, MultipartFile file) throws Exception {

        String fileAppCode = "supply-chain";

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String packListCode = now.format(formatter);
        String dateStr = packListCode.substring(0, 8);
        String dirStr = "/packListImportNew/" + param.getShopNameSimple() + "/" + dateStr + "/";

        String activeEnv = env.getProperty("spring.profiles.active");
        if (StringUtil.isNotEmpty(activeEnv)) {
            dirStr = activeEnv + dirStr;
        }

//        20221102104234534_PackList
        file = this.renameFile(file, packListCode + "_PackList");

        FileInfoResult upload = fileConsumer.upload(fileAppCode, dirStr, file);


        PackListImportListener packListImportListener = new PackListImportListener();
        packListImportListener.setPackListCode(packListCode);

        ExcelReader excelReader = EasyExcel.read(file.getInputStream(), packListImportListener).build();
        // 构建一个sheet 这里可以指定名字或者no
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        // 读取一个sheet
        excelReader.read(readSheet);


        TbLogisticsPackList tbLogisticsPackList = packListImportListener.getTbLogisticsPackList();
        List<TbLogisticsPackListDet> detList = packListImportListener.getDetList();


        LoginUser loginUser = LoginContext.me().getLoginUser();

        tbLogisticsPackList.setPackListCode(packListCode);
        tbLogisticsPackList.setPackTempUpDate(new Date());
        tbLogisticsPackList.setIsUpload(0);
        tbLogisticsPackList.setPackTempUpPerName(loginUser.getName());
        tbLogisticsPackList.setCountryCode(param.getCountryCode());
        tbLogisticsPackList.setShopNameSimple(param.getShopNameSimple());
        tbLogisticsPackList.setPackAmaRecState("New");
        tbLogisticsPackList.setComWarehouseType(param.getComWarehouseType());
        tbLogisticsPackList.setComWarehouseName(param.getComWarehouseName());
        tbLogisticsPackList.setPackShipmentRealStatus("进行中");
        tbLogisticsPackList.setBusPackRemark("人工导入");
        tbLogisticsPackList.setPackTempName(String.valueOf(upload.getFileId()));

        if (tbLogisticsPackList.getToTalSkus() > 0) {
            this.insert(tbLogisticsPackList);
            tbLogisticsPackListDetService.saveBatch(detList);
            return ResponseData.success();
        }
        return ResponseData.error("导入失败");
    }

    /**
     * PackList绑定出货清单(国内仓)（新版绑定）
     *
     * @param createBindPackListParam
     * @return
     */
    @Override
    @DataSource(name = "logistics")
    public ResponseData createBindPackListNew(CreateBindPackListParam createBindPackListParam) {

        LambdaQueryWrapper<TbLogisticsPackList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TbLogisticsPackList::getPackListCode, createBindPackListParam.getPackListCode());
        TbLogisticsPackList tbLogisticsPackList = this.getBaseMapper().selectOne(wrapper);

        List<TbLogisticsPackListBoxRecDet> boxRecDetList = tbLogisticsPackListBoxRecDetService.queryByPackListCode(createBindPackListParam.getPackListCode());


        //出货清单和亚马逊货件关系映射表 表头
        TbLogisticsPackListBoxRec logisticsPackListBoxRec = new TbLogisticsPackListBoxRec();
        BeanUtil.copyProperties(createBindPackListParam, logisticsPackListBoxRec);
        logisticsPackListBoxRec.setShipmentRealStatus(tbLogisticsPackList.getPackShipmentRealStatus());
        logisticsPackListBoxRec.setCreateTime(new Date());
        logisticsPackListBoxRec.setSysUpdateDate(new Date());

        // 出货清单和亚马逊货件关系映射-明细
        List<TbLogisticsPackListBoxRecDet> logisticsPackListBoxRecDetList = createBindPackListParam.getLogisticsPackListBoxRecDetlist();


        if (ObjectUtil.isNotEmpty(boxRecDetList)) {
            logisticsPackListBoxRecDetList.addAll(boxRecDetList);
        }

        //装箱数量检查
        Map<String, List<TbLogisticsPackListBoxRecDet>> skuPackListCodeMap = logisticsPackListBoxRecDetList.stream().collect(Collectors.groupingBy(d -> d.getMerchantSku() + "_" + d.getPackListCode()));
        for (Map.Entry<String, List<TbLogisticsPackListBoxRecDet>> entry : skuPackListCodeMap.entrySet()) {

            String skuPackListCode = entry.getKey();
            List<TbLogisticsPackListBoxRecDet> detList = entry.getValue();

            TbLogisticsPackListDet tbLogisticsPackListDet = tbLogisticsPackListDetService.queryByPackListCodeAndSku(skuPackListCode.split("_")[1], skuPackListCode.split("_")[0]);
            Integer sumQty = detList.stream().map(TbLogisticsPackListBoxRecDet::getQuantity).reduce(Integer::sum).orElse(0);
            if (!sumQty.equals(tbLogisticsPackListDet.getExpectedQty())) { //装箱数量不一致
                return ResponseData.error("SKU:" + skuPackListCode.split("_")[0] + "装箱数量不一致");
            }
        }

        TbLogisticsPackListBoxRec checkResult = tbLogisticsPackListBoxRecService.queryByPackListCodeAndPackCode(logisticsPackListBoxRec.getPackListCode(), logisticsPackListBoxRec.getPackCode());

        if (ObjectUtil.isNotNull(checkResult)) {
            ResponseData.error("该出货清单【" + logisticsPackListBoxRec.getPackCode() + "】已经和新版PackList导入的表格文件packListCode【" + logisticsPackListBoxRec.getPackListCode() + "】关联了");
        }

        TbLogisticsPackListBoxRec logisticsPackListBoxRecResult = tbLogisticsPackListBoxRecService.insert(logisticsPackListBoxRec);

        for (TbLogisticsPackListBoxRecDet det : logisticsPackListBoxRecDetList) {
            det.setSysId(logisticsPackListBoxRecResult.getSysId());
            det.setShipmentRealStatus("进行中");
        }
        tbLogisticsPackListBoxRecDetService.saveBatch(logisticsPackListBoxRecDetList);
        return ResponseData.success("出货清单绑定成功");

    }

    /**
     * PackList作废(新版PackList)
     *
     * @param packListCode
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public ResponseData newCancelPackList(String packListCode) {
        TbLogisticsPackListBoxRec boxRec = tbLogisticsPackListBoxRecService.queryByPackListCode(packListCode);
        if (ObjectUtil.isNotNull(boxRec)) {
            tbLogisticsPackingListService.upPackUploadStateAndPackAbnormalStatus(boxRec.getPackCode(), "未绑定", 0);
            tbLogisticsPackListBoxRecService.deleteByPackListCode(boxRec.getPackListCode());
            tbLogisticsPackListBoxRecDetService.deleteByPackListCode(boxRec.getPackListCode());
        }

        tbLogisticsPackListDetToBoxService.deleteByPackListCode(packListCode);

        tbLogisticsPackListDetService.deleteByPackListCode(packListCode);

        LambdaQueryWrapper<TbLogisticsPackList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbLogisticsPackList::getPackListCode, packListCode);
        this.baseMapper.delete(queryWrapper);

        return ResponseData.success("PackList作废成功");
    }

    /**
     * 编辑ShipmentId与ShipTo
     *
     * @param param
     * @return
     */
    @DataSource(name = "logistics")
    @Transactional
    @Override
    public ResponseData editShipmentInfo(TbLogisticsPackListParam param) {
//        stringBuilder.AppendLine($@"UPDATE dbo.TbLogisticsPackListDet SET ShipmentID = '{shipmentID}' WHERE packListCode='{packListCode}';");
        tbLogisticsPackListDetService.update(
                new LambdaUpdateWrapper<>(TbLogisticsPackListDet.class)
                        .set(TbLogisticsPackListDet::getShipmentId, param.getShipmentId())
                        .eq(TbLogisticsPackListDet::getPackListCode, param.getPackListCode()));

//        stringBuilder.AppendLine($@"UPDATE dbo.TbLogisticsPackListDetToBox SET ShipmentID = '{shipmentID}' WHERE packListCode='{packListCode}';");
        tbLogisticsPackListDetToBoxService.update(
                new LambdaUpdateWrapper<>(TbLogisticsPackListDetToBox.class)
                        .set(TbLogisticsPackListDetToBox::getShipmentId, param.getShipmentId())
                        .eq(TbLogisticsPackListDetToBox::getPackListCode, param.getPackListCode()));

//        stringBuilder.AppendLine($@"UPDATE dbo.TbLogisticsPackListBoxRecDet SET ShipmentID = '{shipmentID}' WHERE packListCode='{packListCode}';");
        tbLogisticsPackListBoxRecDetService.update(new LambdaUpdateWrapper<>(TbLogisticsPackListBoxRecDet.class)
                .set(TbLogisticsPackListBoxRecDet::getShipmentId, param.getShipmentId())
                .eq(TbLogisticsPackListBoxRecDet::getPackListCode, param.getPackListCode()));

//        stringBuilder.AppendLine($@"UPDATE dbo.TbLogisticsPackListBoxRec SET ShipmentID = '{shipmentID}',ShipTo='{shipTo}' WHERE packListCode='{packListCode}';");
        tbLogisticsPackListBoxRecService.update(new LambdaUpdateWrapper<>(TbLogisticsPackListBoxRec.class)
                .set(TbLogisticsPackListBoxRec::getShipmentId, param.getShipmentId())
                .set(TbLogisticsPackListBoxRec::getShipTo, param.getShipTo())
                .eq(TbLogisticsPackListBoxRec::getPackListCode, param.getPackListCode()));

//        stringBuilder.AppendLine($@"UPDATE dbo.TbLogisticsPackList SET ShipmentID = '{shipmentID}',ShipTo='{shipTo}' WHERE packListCode='{packListCode}';");
        this.update(new LambdaUpdateWrapper<>(TbLogisticsPackList.class)
                .set(TbLogisticsPackList::getShipmentId, param.getShipmentId())
                .set(TbLogisticsPackList::getShipTo, param.getShipTo())
                .eq(TbLogisticsPackList::getPackListCode, param.getPackListCode()));


        return ResponseData.success();
    }

    /**
     * 调整货件状态
     *
     * @param param
     * @return
     */
    @DataSource(name = "logistics")
    @Transactional
    @Override
    public ResponseData shipmentRealStatusChange(TbLogisticsPackListParam param) {

        // 更新PackList货件实际状态
        this.update(new LambdaUpdateWrapper<>(TbLogisticsPackList.class)
                .eq(TbLogisticsPackList::getShipmentId, param.getShipmentId())
                .set(TbLogisticsPackList::getPackShipmentRealStatus, param.getPackShipmentRealStatus()));


        //更新PackList货件明细实际状态
        tbLogisticsPackListDetService.update(
                new LambdaUpdateWrapper<>(TbLogisticsPackListDet.class)
                        .eq(TbLogisticsPackListDet::getShipmentId, param.getShipmentId())
                        .set(TbLogisticsPackListDet::getShipmentRealStatus, param.getPackShipmentRealStatus()));

        // 更新物流出货的货件实际状态
        tbLogisticsPackListBoxRecService.update(new LambdaUpdateWrapper<>(TbLogisticsPackListBoxRec.class)
                .eq(TbLogisticsPackListBoxRec::getShipmentId, param.getShipmentId())
                .set(TbLogisticsPackListBoxRec::getShipmentRealStatus, param.getPackShipmentRealStatus()));

        //更新物流出货明细的货件实际状态
        tbLogisticsPackListBoxRecDetService.update(new LambdaUpdateWrapper<>(TbLogisticsPackListBoxRecDet.class)
                .eq(TbLogisticsPackListBoxRecDet::getShipmentId, param.getShipmentId())
                .set(TbLogisticsPackListBoxRecDet::getShipmentRealStatus, param.getPackShipmentRealStatus()));

        // 更新发货数据的货件实际状态
        tbBscOverseasWayService.update(new LambdaUpdateWrapper<>(TbBscOverseasWay.class)
                .eq(TbBscOverseasWay::getShipmentId, param.getShipmentId())
                .set(TbBscOverseasWay::getShipmentRealStatus, param.getPackShipmentRealStatus()));

        return ResponseData.success();
    }


    /**
     * 通过ShipmentID查询来货信息 sku维度
     *
     * @param shipmentId
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public ResponseData queryLogisticsInGoodsList(String shipmentId) {

        List<LogisticsInGoodsModel> inGoodsModelList = tbLogisticsPackListMapper.queryLogisticsInGoodsList(shipmentId);

        return ResponseData.success(inGoodsModelList);
    }


    @DataSource(name = "logistics")
    @Override
    public ShipmentUploadExcelDTO getshipmentUploadExcelData(String packListCode) {

        List<TbLogisticsPackListDet> packListDetList = tbLogisticsPackListDetService.queryByPackListCode(packListCode);
        List<TbLogisticsPackListBoxRecDet> packListBoxRecDets = tbLogisticsPackListBoxRecDetService.queryByPackListCode(packListCode);
        List<TbLogisticsPackListDetToBox> packListDetToBoxes = tbLogisticsPackListDetToBoxService.queryByPackListCode(packListCode);
        if (ObjectUtil.isEmpty(packListBoxRecDets)) {
            return null;
        }


        List<String> packCodeList = packListBoxRecDets.stream().map(TbLogisticsPackListBoxRecDet::getPackCode).distinct().collect(Collectors.toList());

        if (ObjectUtil.isEmpty(packCodeList)) {
            return null;//没有绑定出货清单
        }


        AmzInBoundShipmentDTO amzInboundShipment = new AmzInBoundShipmentDTO();

        TbLogisticsPackList tbLogisticsPackList = this.baseMapper.selectOne(new LambdaUpdateWrapper<>(TbLogisticsPackList.class)
                .eq(TbLogisticsPackList::getPackListCode, packListCode));
        amzInboundShipment.setMaxBoXnum(tbLogisticsPackList.getMaxBoXnum());
        amzInboundShipment.setToTalSkus(tbLogisticsPackList.getToTalSkus());
        amzInboundShipment.setShipmentId(tbLogisticsPackList.getShipmentId());
        amzInboundShipment.setBusShipmentName(tbLogisticsPackList.getBusShipmentName());
        amzInboundShipment.setShipTo(tbLogisticsPackList.getShipTo());


        List<AmzBoxInfoDTO> boxInfoList = new ArrayList<>();
        List<TbLogisticsPackingListDet1> logisticsPackingListDet1List = tbLogisticsPackingListDet1Service.getByPackCode(packCodeList);
        for (TbLogisticsPackingListDet1 det1 : logisticsPackingListDet1List) {
            AmzBoxInfoDTO boxInfoDTO = new AmzBoxInfoDTO();
            boxInfoDTO.setPackDetBoxHeight(det1.getPackDetBoxHeight().doubleValue());
            boxInfoDTO.setPackDetBoxLength(det1.getPackDetBoxLength().doubleValue());
            boxInfoDTO.setPackDetBoxWidth(det1.getPackDetBoxWidth().doubleValue());
            boxInfoDTO.setPackDetBoxNum(det1.getPackDetBoxNum());
            boxInfoDTO.setPackDetBoxWeight(det1.getPackDetBoxWeight().doubleValue());

            boxInfoList.add(boxInfoDTO);
        }

        List<AmzBoxSkuInfoDTO> boxSkuInfoList = new ArrayList<>();
        for (TbLogisticsPackListBoxRecDet boxRecDet : packListBoxRecDets) {
            AmzBoxSkuInfoDTO boxSkuInfoDTO = new AmzBoxSkuInfoDTO();
            TbLogisticsPackListDet det = packListDetList.stream().filter(x -> x.getMerchantSku().equals(boxRecDet.getMerchantSku())).collect(Collectors.toList()).get(0);
            boxSkuInfoDTO.setAsin(det.getAsin());
            boxSkuInfoDTO.setMerchantSKU(det.getMerchantSku());
            boxSkuInfoDTO.setFnsku(det.getFnsku());
            boxSkuInfoDTO.setTitle(det.getTitle());
            boxSkuInfoDTO.setPackDetBoxNum(boxRecDet.getPackDetBoxNum());
            boxSkuInfoDTO.setQuantity(boxRecDet.getQuantity());
            boxSkuInfoDTO.setExpectedQty(det.getExpectedQty());
            boxSkuInfoDTO.setShipmentID(boxRecDet.getShipmentId());

            boxSkuInfoList.add(boxSkuInfoDTO);
        }

        String path = System.getProperty("user.dir") + "\\template\\";
        CreateExcel createExcel = new CreateExcel(amzInboundShipment, boxInfoList, boxSkuInfoList);
        String result = createExcel.create(path);

        return null;
    }


    @DataSource(name = "logistics")
    @Override
    public Workbook getWorkbook(String packListCode, String shopNameSimple, String packTempName) {
        byte[] excelBytes = fileConsumer.getFileBytes(Long.valueOf(packTempName));
        try {
            InputStream inputStream = new ByteArrayInputStream(excelBytes);
            Workbook workbook = WorkbookFactory.create(inputStream);
            return workbook;
        } catch (Exception e) {
            log.error("获取packList上传的原始模板失败", e);
        }
        return null;
    }


    @DataSource(name = "logistics")
    @Override
    public Workbook fillData(Workbook workbook, String packListCode) {

        List<TbLogisticsPackListDet> packListDetList = tbLogisticsPackListDetService.queryByPackListCode(packListCode);
        List<TbLogisticsPackListBoxRecDet> packListBoxRecDets = tbLogisticsPackListBoxRecDetService.queryByPackListCode(packListCode);
        List<TbLogisticsPackListDetToBox> packListDetToBoxes = tbLogisticsPackListDetToBoxService.queryByPackListCode(packListCode);
        if (ObjectUtil.isEmpty(packListBoxRecDets)) {
            return null;
        }


        List<String> packCodeList = packListBoxRecDets.stream().map(TbLogisticsPackListBoxRecDet::getPackCode).distinct().collect(Collectors.toList());

        if (ObjectUtil.isEmpty(packCodeList)) {
            return null;//没有绑定出货清单
        }


        TbLogisticsPackList tbLogisticsPackList = this.baseMapper.selectOne(new LambdaUpdateWrapper<>(TbLogisticsPackList.class)
                .eq(TbLogisticsPackList::getPackListCode, packListCode));

        Integer maxBoXnum = tbLogisticsPackList.getMaxBoXnum();
        Integer toTalSkus = tbLogisticsPackList.getToTalSkus();

        List<String> originPackDetBoxNumUploadList = packListBoxRecDets.stream()
                .map(TbLogisticsPackListBoxRecDet::getOriginPackDetBoxNumUpload)
                .distinct().collect(Collectors.toList());


        //按箱号过滤出 箱子尺寸重量信息
        List<TbLogisticsPackingListDet1> logisticsPackingListDet1List = tbLogisticsPackingListDet1Service.getByPackCode(packCodeList);
        List<AmzBoxInfoDTO> boxInfoList = new ArrayList<>();
        logisticsPackingListDet1List = logisticsPackingListDet1List.stream()
                .filter(x -> originPackDetBoxNumUploadList.contains(x.getPackDetBoxNumUpload()))
                .collect(Collectors.toList());

        for (TbLogisticsPackingListDet1 det1 : logisticsPackingListDet1List) {
            AmzBoxInfoDTO boxInfoDTO = new AmzBoxInfoDTO();
            boxInfoDTO.setPackDetBoxHeight(det1.getPackDetBoxHeight().doubleValue());
            boxInfoDTO.setPackDetBoxLength(det1.getPackDetBoxLength().doubleValue());
            boxInfoDTO.setPackDetBoxWidth(det1.getPackDetBoxWidth().doubleValue());
            boxInfoDTO.setPackDetBoxNum(det1.getPackDetBoxNum());
            boxInfoDTO.setPackDetBoxWeight(det1.getPackDetBoxWeight().doubleValue());

            boxInfoList.add(boxInfoDTO);
        }

        // 根据绑定关系 找出箱子里面sku的信息
        List<AmzBoxSkuInfoDTO> boxSkuInfoList = new ArrayList<>();
        for (TbLogisticsPackListBoxRecDet boxRecDet : packListBoxRecDets) {
            AmzBoxSkuInfoDTO boxSkuInfoDTO = new AmzBoxSkuInfoDTO();
            TbLogisticsPackListDet det = packListDetList.stream().filter(x -> x.getMerchantSku().equals(boxRecDet.getMerchantSku())).collect(Collectors.toList()).get(0);
            boxSkuInfoDTO.setAsin(det.getAsin());
            boxSkuInfoDTO.setMerchantSKU(det.getMerchantSku());
            boxSkuInfoDTO.setFnsku(det.getFnsku());
            boxSkuInfoDTO.setTitle(det.getTitle());
            boxSkuInfoDTO.setPackDetBoxNum(boxRecDet.getPackDetBoxNum());
            boxSkuInfoDTO.setQuantity(boxRecDet.getQuantity());
            boxSkuInfoDTO.setExpectedQty(det.getExpectedQty());
            boxSkuInfoDTO.setShipmentID(boxRecDet.getShipmentId());
            boxSkuInfoList.add(boxSkuInfoDTO);
        }


        //填充 sku发货数量 分配在不同箱子
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= toTalSkus; i++) {

            Row row = sheet.getRow(4 + i );//第一个sku sku的行基础偏移量4
            String sku = row.getCell(0).getStringCellValue();
            //过滤出对应的sku装在了那些箱子里面
            List<AmzBoxSkuInfoDTO> boxSkuInfoDTOS = boxSkuInfoList.stream().filter(x -> x.getMerchantSKU().equals(sku)).collect(Collectors.toList());
            for (AmzBoxSkuInfoDTO boxsku : boxSkuInfoDTOS) {
                Integer packDetBoxNum = boxsku.getPackDetBoxNum();
                row.createCell(11+packDetBoxNum).setCellValue(boxsku.getQuantity());//sku具体在某个箱子的，装箱数量，列基础偏移量11
            }
        }

        //填充 箱子的尺寸和重量信息

        Map<Integer, Double> weightMap = boxInfoList.stream().collect(Collectors.toMap(AmzBoxInfoDTO::getPackDetBoxNum, AmzBoxInfoDTO::getPackDetBoxWeight, (v1, v2) -> v1));
        Map<Integer, Double> heightMap = boxInfoList.stream().collect(Collectors.toMap(AmzBoxInfoDTO::getPackDetBoxNum, AmzBoxInfoDTO::getPackDetBoxHeight, (v1, v2) -> v1));
        Map<Integer, Double> widthMap = boxInfoList.stream().collect(Collectors.toMap(AmzBoxInfoDTO::getPackDetBoxNum, AmzBoxInfoDTO::getPackDetBoxWidth, (v1, v2) -> v1));
        Map<Integer, Double> lengthMap = boxInfoList.stream().collect(Collectors.toMap(AmzBoxInfoDTO::getPackDetBoxNum, AmzBoxInfoDTO::getPackDetBoxLength, (v1, v2) -> v1));

        for (int i = 1; i <= 4; i++) {

            Row row = sheet.getRow(4 + toTalSkus+ 1 + 1  + i );//sku的行基础偏移量4 + sku的行数 + 空白行 + 包装箱名称行

            if (i==1) { //包装箱重量（磅）   欧洲 ：包装重量

                String weightTitle = row.getCell(0).getStringCellValue();
                Double magnification;
                if (weightTitle.contains("磅") || weightTitle.contains("lb") || weightTitle.contains("LB")) {
                    magnification = 2.20462;
                } else {//默认KG
                    magnification = 1.00;
                }

                weightMap.forEach((boxNum, v) -> {
                    row.createCell(11+ boxNum ).setCellValue( v * magnification);
                });
                continue;
            }

            //长度单位倍率
            String title = row.getCell(0).getStringCellValue();
            Double lengthMagnification;
            if (title.contains("英寸") || title.contains("inch") || title.contains("Inch") || title.contains("IN")) {
                lengthMagnification = 0.393701;
            } else {//默认KG
                lengthMagnification = 1.00;
            }


            if (i==2) { //包装箱宽度（英寸）
                widthMap.forEach((boxNum, v) -> {
                    row.createCell(11+ boxNum ).setCellValue( v * lengthMagnification);
                });
                continue;
            }
            if (i==3) { //包装箱长度（英寸）
                lengthMap.forEach((boxNum, v) -> {
                    row.createCell(11+ boxNum ).setCellValue( v * lengthMagnification);
                });
                continue;
            }
            if (i==4) { //包装箱高度（英寸）
                heightMap.forEach((boxNum, v) -> {
                    row.createCell(11+ boxNum ).setCellValue( v * lengthMagnification);
                });
            }
        }
        return workbook;
    }

    @DataSource(name = "logistics")
    @Override
    public int deleteByPackCode(String packCode) {
        LambdaQueryWrapper<TbLogisticsPackList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TbLogisticsPackList::getPackCode, packCode);
        return tbLogisticsPackListMapper.delete(wrapper);
    }

    @DataSource(name = "EBMS")
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> getTbComShippingLableFromEbms(List<String> csfm) {
        return tbLogisticsPackListMapper.getTbComShippingLableFromEbms(csfm);
    }



    private MultipartFile renameFile(MultipartFile multipartFile, String newNameStr) throws Exception {

        InputStream inputStream = null;
        try {

            String originalFilename = multipartFile.getOriginalFilename();
            if (StringUtil.isEmpty(originalFilename)) {
                throw new Exception("文件未获取到原始名称");
            }
            String prefix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            if (!"xlsx".equals(suffix) && !"xls".equals(suffix)) {
                throw new Exception("文件格式不对，只支持xlsx或者xls格式文件");
            }

            // 临时文件
            File file = File.createTempFile(prefix, "." + suffix);
            multipartFile.transferTo(file);
            String fileName = newNameStr + "." + suffix;
            // 重命名
            file = FileUtil.rename(file, fileName, true, true);
            inputStream = new FileInputStream(file);
            // File转换成MultipartFile
            multipartFile = new MockMultipartFile(fileName, fileName,
                    ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);

            if (!file.delete()) {
                throw new Exception("文件重命名后删除失败");
            }

            return multipartFile;
        } catch (Exception e) {
            throw new Exception("文件重命名异常");
        } finally {

            if (inputStream != null) {
                inputStream.close();
            }
        }

    }


    @DataSource(name = "logistics")
    @Override
    @Transactional
    public ResponseData closeLogisticsInGoods(CloseLogisticsInGoodsParam model) {

        //查询进行中的来货视图是否还有相同账号、站点、FBA号、SKU的数据
        List<LogisticsInGoodsModel> inGoodsModelList = tbLogisticsPackListMapper.queryLogisticsInGoodsList(model.getBusShipmentID());
        if (ObjectUtil.isEmpty(inGoodsModelList)) {
            return ResponseData.error("ShipmentID:" + model.getBusShipmentID() + "未找到对应的来货信息");
        }
        List<LogisticsInGoodsModel> logisticsDet = inGoodsModelList.stream().filter(i -> i.getBusShopNameSimple().equals(model.getBusShopNameSimple())
                && i.getBusCountryCode().equals(model.getBusCountryCode())
                && i.getBusSku().equals(model.getBusSku())
        ).collect(Collectors.toList());

        if (ObjectUtil.isEmpty(logisticsDet)) {
            return ResponseData.error("ShipmentID:" + model.getBusShipmentID() + "按sku过滤后未找到对应的来货信息");
        }
//        var logisticsDet = con.Tables.ViewLogisticsInGoods.Where(x => x.BusShopNameSimple == model.BusShopNameSimple && x.BusCountryCode ==
//                model.BusCountryCode && x.BusShipmentID == model.BusShipmentID && x.BusSku == model.BusSku).ToList();

        //根据FBA号、Sku，更新PackList明细表货件实际状态
        tbLogisticsPackListDetService.update(new LambdaUpdateWrapper<>(TbLogisticsPackListDet.class)
                .eq(TbLogisticsPackListDet::getShipmentId, model.getBusShipmentID())
                .eq(TbLogisticsPackListDet::getMerchantSku, model.getBusSku())
                .set(TbLogisticsPackListDet::getShipmentRealStatus, "已完成")
                .set(TbLogisticsPackListDet::getBusSysUpdateDate, new Date())
        );

        //根据FBA号、Sku，更新PackList装箱记录明细表货件实际状态

        tbLogisticsPackListBoxRecDetService.update(new LambdaUpdateWrapper<>(TbLogisticsPackListBoxRecDet.class)
                .eq(TbLogisticsPackListBoxRecDet::getShipmentId, model.getBusShipmentID())
                .eq(TbLogisticsPackListBoxRecDet::getMerchantSku, model.getBusSku())
                .set(TbLogisticsPackListBoxRecDet::getShipmentRealStatus, "已完成")
        );

        //根据FBA号、Sku，更新发货汇总数据表货件实际状态

        tbBscOverseasWayService.update(new LambdaUpdateWrapper<>(TbBscOverseasWay.class)
                .eq(TbBscOverseasWay::getShipmentId, model.getBusShipmentID())
                .eq(TbBscOverseasWay::getSku, model.getBusSku())
                .set(TbBscOverseasWay::getShipmentRealStatus, "已完成")
//                .set(TbBscOverseasWay::getUpdateTime, new Date())
        );


        //判断此FBA号对应的货件下，所有SKU箱件的货件实际状态是否都是已完成

        boolean allFinish = false;
        List<TbLogisticsPackListBoxRecDet> boxRecDetList = tbLogisticsPackListBoxRecDetService.queryByShipmentId(model.getBusShipmentID());
        if (ObjectUtil.isNotEmpty(boxRecDetList)) {
            List<TbLogisticsPackListBoxRecDet> unFinishList = boxRecDetList.stream().filter(b -> !b.getShipmentRealStatus().equals("已完成")).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(unFinishList)) {
                allFinish = true;
            }
        }

        if (allFinish)
        {
            //根据FBA号，更新PackList表货件实际状态
            this.update(new LambdaUpdateWrapper<>(TbLogisticsPackList.class)
                            .eq(TbLogisticsPackList::getShipmentId, model.getBusShipmentID())
                            .set(TbLogisticsPackList::getPackShipmentRealStatus, "已完成")
            );

            //根据FBA号，更新PackList装箱记录表货件实际状态
            tbLogisticsPackListBoxRecService.update(new LambdaUpdateWrapper<>(TbLogisticsPackListBoxRec.class)
                    .eq(TbLogisticsPackListBoxRec::getShipmentId, model.getBusShipmentID())
                    .set(TbLogisticsPackListBoxRec::getShipmentRealStatus, "已完成")
            );
        }

        if ("是".equals(model.getBusIsClaims()))
        {
            //根据账号、站点、FBA号，查询Amazon索赔记录
            List<TbLogisticsClaimToAmazon> claimRecordList = tbLogisticsClaimToAmazonService.list(new LambdaQueryWrapper<>(TbLogisticsClaimToAmazon.class)
                    .eq(TbLogisticsClaimToAmazon::getBusShopNameSimple, model.getBusShipmentID())
                    .eq(TbLogisticsClaimToAmazon::getBusCountryCode, model.getBusShipmentID())
                    .eq(TbLogisticsClaimToAmazon::getBusShipmentId, model.getBusShipmentID())
            );

            if ( ObjectUtil.isEmpty(claimRecordList))
            {
                TbLogisticsPackList packList = this.getOne(new LambdaQueryWrapper<>(TbLogisticsPackList.class)
                        .eq(TbLogisticsPackList::getShopNameSimple, model.getBusShipmentID())
                        .eq(TbLogisticsPackList::getCountryCode, model.getBusShipmentID())
                        .eq(TbLogisticsPackList::getShipmentId, model.getBusShipmentID())
                );

                LoginUser loginUser = LoginContext.me().getLoginUser();

                //Amazon索赔表，新增一条索赔记录
                var createDate = new Date();
                TbLogisticsClaimToAmazon claimToAmazon = new TbLogisticsClaimToAmazon();

                        claimToAmazon.setPkClaimId(DateUtil.format(createDate, "yyyyMMddHHmmssSSSS"));
                        claimToAmazon.setSysCreateDate(createDate);
                        claimToAmazon.setSysCreatePerCode(loginUser.getAccount());
                        claimToAmazon.setSysCreatePerName(loginUser.getName());
                        claimToAmazon.setBusShopNameSimple(model.getBusShopNameSimple());
                        claimToAmazon.setBusCountryCode(model.getBusCountryCode());
                        claimToAmazon.setBusShipmentId(model.getBusShipmentID());
                        claimToAmazon.setBusShipmentStatus(model.getBusAmaRecState());
                        claimToAmazon.setBusShipTo(packList.getShipTo());
                        claimToAmazon.setBusTotalSkus(packList.getToTalSkus());
                        claimToAmazon.setBusTotalUnits(packList.getTotalUnits());

                tbLogisticsClaimToAmazonService.insert(claimToAmazon);
                claimRecordList.add(claimToAmazon);
            }

            //Amazon索赔明细表，根据输入的信息项新增索赔明细记录
            String pkClaimID = claimRecordList.get(0).getPkClaimId();
            List<TbLogisticsClaimDetToAmazon> pkClaimDetList = new ArrayList<>();
            for (LogisticsInGoodsModel l : logisticsDet) {
                TbLogisticsClaimDetToAmazon detToAmazon = new TbLogisticsClaimDetToAmazon();
                        detToAmazon.setPkClaimId(pkClaimID);
                        detToAmazon.setBusIsPod("是".equals(model.getBusIsPod()) ? "是" : "否");
                        detToAmazon.setBusComWarehouseName(l.getBusComWarehouseName());
                        detToAmazon.setBusLerSignDate(l.getBusLerSignDate());
                        detToAmazon.setBusLhrOddNumb(l.getBusLhrOddNumb());
                        detToAmazon.setBusLhrState(l.getBusLhrState());
                        detToAmazon.setBusLogTraMode1(l.getBusLogTraMode1());
                        detToAmazon.setBusLogTraMode2(l.getBusLogTraMode2());
                        detToAmazon.setBusSku(l.getBusSku());
                        detToAmazon.setBusSendQty(l.getBusSendQty());
                        detToAmazon.setBusStayDeliverQty(l.getBusStayDeliverQty());
                        detToAmazon.setBusIssuedQty(l.getBusIssuedQty());
                        detToAmazon.setBusReceiveQty(l.getBusReceiveQty());
                        detToAmazon.setBusInTransitQty(l.getBusInTransitQty());
                        detToAmazon.setBusDiscrepancy(l.getBusDiscrepancy());
                        detToAmazon.setBusRemark(l.getBusRemark());
                pkClaimDetList.add(detToAmazon);
            }

            if (ObjectUtil.isNotEmpty(pkClaimDetList)) {
                tbLogisticsClaimDetToAmazonService.saveBatch(pkClaimDetList);
            }
        }
        return ResponseData.success("【关闭在途】成功");
    }

}