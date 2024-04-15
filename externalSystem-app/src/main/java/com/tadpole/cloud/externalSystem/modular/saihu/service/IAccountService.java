package com.tadpole.cloud.externalSystem.modular.saihu.service;

import cn.stylefeng.guns.cloud.system.api.model.SaihuUser;

import java.util.List;

/**
 * @author: ty
 * @description: 赛狐token服务接口类
 * @date: 2024/2/19
 */
public interface IAccountService {

    List<SaihuUser> getAccountList();

    void changeAccountStatus(SaihuUser saihuUser);

    void createAccount(SaihuUser saihuUser);
}
