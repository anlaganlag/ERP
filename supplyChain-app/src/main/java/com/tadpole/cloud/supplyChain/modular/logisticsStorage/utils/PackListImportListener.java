package com.tadpole.cloud.supplyChain.modular.logisticsStorage.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.fastjson.JSON;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackList;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListDet;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class PackListImportListener extends AnalysisEventListener<Map<Integer, CellData>> {

    //总sku个数
    private  Integer totalSku  =0;
    //总箱数
    private  Integer totalBox =0;
    private  Integer rowMaxIndex =0;
    private  String packListCode = "";

    private TbLogisticsPackList tbLogisticsPackList =new TbLogisticsPackList();

    private List<TbLogisticsPackListDet> detList=new ArrayList<>();
    @Override
    public void invoke(Map<Integer, CellData> data, AnalysisContext context) {

        Integer rowIndex = context.readRowHolder().getRowIndex();
        if (rowIndex == 2) {
            //总sku个数
            String totalSkuStr = String.valueOf(data.get(0));//"SKU 总数：21（1987 件商品）"
            totalSku = Integer.valueOf(totalSkuStr.split("：")[1].split("（")[0]) ;
            //总箱数
            totalBox = Integer.valueOf(String.valueOf(data.get(12)));
            rowMaxIndex = 11 + totalSku;//从0开始
            tbLogisticsPackList.setToTalSkus(totalSku);
            tbLogisticsPackList.setMaxBoXnum(totalBox);
            tbLogisticsPackList.setPackTempName(packListCode+"_PackList");
            tbLogisticsPackList.setTotalUnits(Integer.valueOf(totalSkuStr.split("（")[1].split("件商品")[0].trim()))  ;
            return;
        }

        if (rowIndex < 5) {
            return;
        }

        //sku信息已经sku分布在那些箱号及其数量
        if ( rowIndex < rowMaxIndex -6 ) {
            TbLogisticsPackListDet det = new TbLogisticsPackListDet();
            det.setMerchantSku(String.valueOf(data.get(0)));
            det.setTitle(String.valueOf(data.get(1)));
//            det.setTitle(String.valueOf(data.get(2)));//id
            det.setAsin(String.valueOf(data.get(4)));
            det.setFnsku(String.valueOf(data.get(5)));
//            det.set(String.valueOf(data.get(4))); //状况  NewItem
            det.setPrepType(String.valueOf(data.get(6))); //预处理类型
//            det.setPrepType(String.valueOf(data.get(7))); //商品预处理方是谁？
            det.setWhoWillLable(String.valueOf(data.get(8))); //商品贴标方是谁？
            det.setExpectedQty(Integer.valueOf(String.valueOf(data.get(9))));//预计数量
            det.setBoxedQty(0);//装箱数量
            det.setMaxBoxNum(totalBox);//最大箱数号
            det.setPackListCode(packListCode);
            detList.add(det);
            return;
        }

        //空行跳过
        if ( rowIndex == rowMaxIndex -6 ) {
            return;
        }

        //箱子信息 长宽高 总量
        if ( rowIndex < rowMaxIndex ) {
            return;
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("发货总的sku数量："+totalSku);
        System.out.println("发货总的装箱数量："+totalBox);
        for (TbLogisticsPackListDet det : detList) {
            System.out.println("每条sku信息："+ JSON.toJSONString(det));
        }
        System.out.println("发货先基本信息："+ JSON.toJSONString(tbLogisticsPackList));

        System.out.println("所有数据解析完成！》》》》》》》》》》》》》》》》》》》》》》》");
    }
}
