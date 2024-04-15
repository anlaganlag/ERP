package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 领星添加类目返回结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinXingCategoryAddResp {
         private Integer code;
         private String msg;
         private String require_id;
         private String id;
         private String req_time_sequence;
         private Integer update_code;

/*    {
    "code": 1,
    "msg": "success",
    "require_id": "5066FB02-FBBC-FB07-96A6-71F3C20D6CD0",
    "id": "29190",
    "req_time_sequence": "/api/product_extend/categoryAdd$$1",
    "update_code": 1
}*/

    /**
     * 添加类目成功的判断条件
     * @return
     */
   public boolean isSuccess() {
       if ((this.code==1 && "success".equals(this.msg)) || (this.code==306005 && this.msg.contains("同一分支下分类名称重复")) ) {
           return true;
       }
       return false;
    }
}

