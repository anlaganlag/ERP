package com.tadpole.cloud.externalSystem.modular.k3.service;

import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3BankAccountInfo;

/**
 * K3数据库服务，主要用于查询k3数据库里面的数据，直接查询
 */
public interface K3DatabaseService {
    /**
     * 根据银行账号查询科目信息
     * @param bankNo
     * @param orgCode
     * @return
     */
    K3BankAccountInfo queryK3BankAccountInfo(String bankNo,String orgCode);
}
