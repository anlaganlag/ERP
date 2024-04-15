package com.tadpole.cloud.operationManagement.modular.stock.controller;


import com.tadpole.cloud.operationManagement.modular.stock.service.ISpecialApplyInfoHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 特殊备货申请信息历史记录 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-24
*/
@RestController
@RequestMapping("/special-apply-info-history")
public class SpecialApplyInfoHistoryController {

    @Autowired
    private ISpecialApplyInfoHistoryService service;

    }
