package com.tadpole.cloud.product.modular.productplan.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ljx
 * @description: 预案阶段
 * @date: 2022/7/21
 */
@Getter
public enum RdPreProposalEnum {
    YA_STATE_WAIT_APP("待申请"),
    YA_STATE_WAIT_FEBK("待反馈"),
    YA_STATE_WAIT_EXAM("待审核"),
    YA_STATE_WAIT_APPR("待审批"),
    YA_STATE_WAIT_ARCH("已归档"),
    YA_ARCH_TYPE_CXGD("撤销归档"),
    YA_ARCH_TYPE_FKGD("反馈归档"),
    YA_ARCH_TYPE_SHGD("审核归档"),
    YA_ARCH_TYPE_SPGD("审批归档"),
    YA_FEB_RESULT_YES("提案立项"),
    YA_FEB_RESULT_NO("不立项"),
    YA_EXAM_RESULT_YES("同意"),
    YA_EXAM_RESULT_FEBK("重新反馈"),
    YA_EXAM_RESULT_NO("不同意"),
    YA_APPR_RESULT_YES("通过"),
    YA_APPR_RESULT_NO("不通过"),
    YA_DEV_METHOND_QX("全新品-现货"),
    YA_DEV_METHOND_QD("全新品-定制"),
    YA_DEV_METHOND_PX("派生品-现货"),
    YA_DEV_METHOND_PG("派生品-改良"),
    YA_DEV_METHOND_PT("派生品-拓新"),
    YA_SCENE_DEV("开发预案"),
    YA_SCENE_OPR("需求预案"),
    YA_SCENE_PUS("推样预案"),
    YA_PAGE_NEW("新增"),
    YA_PAGE_EDIT("编辑"),
    YA_FUNC_SAVE("保存"),
    YA_FUNC_SUBMIT("提交"),
    YA_IS_AUTO_APPR_YES("是"),
    YA_IS_AUTO_APPR_NO("否"),
    YA_SYS_DEFAULT_CODE("系统"),
    YA_SYS_DEFAULT_NAME("系统")
    ;


    /**
     * 海外仓入库管理签收状态
     */
    private String name;

    RdPreProposalEnum(String name) {
        this.name = name;
    }

}
