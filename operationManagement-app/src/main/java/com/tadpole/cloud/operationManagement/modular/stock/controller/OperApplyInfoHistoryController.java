package com.tadpole.cloud.operationManagement.modular.stock.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.operationManagement.modular.stock.service.IOperApplyInfoHistoryService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 备货申请信息历史记录 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
@RestController
@RequestMapping("/oper-apply-info-history")
public class OperApplyInfoHistoryController {

    @Autowired
    private IOperApplyInfoHistoryService service;

    }
