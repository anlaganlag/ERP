package com.tadpole.cloud.operationManagement.modular.shipment.constants;

/**
 * 字典类型异常枚举
 *
 * @author lsy
 */
public  class ShipmentConstants {




    //发货批次号
    public static String SHIPMENT_BATCH_NO ="SHIPMENT_BATCH_NO";
    public static String PRINT_LABEL_BATCH_NO ="Print_Label_Batch_No";




    /**
     * 提案管理枚举
     */
    public enum ProposalStatusEnum {


        上新模式_选品("选品", "选品"),
        上新模式_精品("精品", "精品"),
        上新模式_自主设计("自主设计", "自主设计"),


             ;



        ProposalStatusEnum(String code, String message) {
            this.code = code;
            this.message = message;
        }

        private String code;

        private String message;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

