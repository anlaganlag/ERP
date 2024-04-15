package com.tadpole.cloud.operationManagement.modular.stock.controller;


import com.tadpole.cloud.operationManagement.modular.stock.service.IVerifInfoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 审核记录信息 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
@RestController
@RequestMapping("/verif-info-record")
public class VerifInfoRecordController {

    @Autowired
    private IVerifInfoRecordService service;

    }
