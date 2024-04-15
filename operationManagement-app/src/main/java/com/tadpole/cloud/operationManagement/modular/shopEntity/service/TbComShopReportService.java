package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopReportResult;

import java.util.List;

/**
* 资源-店铺;(Tb_Com_Shop)--服务接口
* @author : LSY
* @date : 2023-7-28
*/
public interface TbComShopReportService extends IService<TbComShop> {






    Page<TbComShopReportResult> paginQueryShopReport(TbComShopParam tbComShop, long current, long size);



    List<String> shopNameList(boolean filter);




}