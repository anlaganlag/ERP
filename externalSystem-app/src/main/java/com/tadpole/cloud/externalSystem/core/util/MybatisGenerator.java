package com.tadpole.cloud.externalSystem.core.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis代码生成器
 */
public class MybatisGenerator {

  /**
   * MySQL 生成演示
   */
  public static void main(String[] args) {
    AutoGenerator mpg = new AutoGenerator();
    // 选择 freemarker 引擎，默认 Veloctiy
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());

    // 全局配置
    GlobalConfig gc = new GlobalConfig();
    String projectPath = System.getProperty("user.dir");
    gc.setOutputDir(projectPath + "/externalSystem-app/src/main/java");
    gc.setAuthor("S20190109");//创建人
    gc.setOpen(false);
    //时间类型(TIME_PACK、ONLY_DATE、SQL_PACK,分别对应LocalDateTime、Date、Timestamp)
    gc.setDateType(DateType.ONLY_DATE);
    //指定生成的主键的ID类型
    gc.setIdType(IdType.AUTO);
    // gc.setSwagger2(true); 实体属性 Swagger2 注解
    mpg.setGlobalConfig(gc);

    // mysql数据源配置
    /*DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl("jdbc:mysql://192.168.2.16:3306/jc_erp_replenish?useUnicode=true&useSSL=false&characterEncoding=utf8");
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("Jin@Dev001");
    dsc.setDbType(DbType.MYSQL);
    mpg.setDataSource(dsc);*/

//    // oracle数据源配置
//    DataSourceConfig dsc = new DataSourceConfig();
//    dsc.setUrl("jdbc:oracle:thin:@dev.jcintergl.com:1521/ORCLPDB1");
//    dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
//    dsc.setUsername("warehouse");
//    dsc.setPassword("Bsc_Dev001");
//    dsc.setDbType(DbType.ORACLE);
//    mpg.setDataSource(dsc);

    // sqlserver数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl("jdbc:sqlserver://192.168.0.228:1433;DatabaseName=EBMS");
    dsc.setDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    dsc.setUsername("sa");
    dsc.setPassword("JcEbms123");
    dsc.setDbType(DbType.SQL_SERVER);
    mpg.setDataSource(dsc);

    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setNaming(NamingStrategy.underline_to_camel); // 数据库表映射到实体的命名策略
    strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 数据库表字段映射到实体的命名策略
    strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作
    strategy.setRestControllerStyle(true); // restful api风格控制器
    strategy.setControllerMappingHyphenStyle(true); // url中驼峰转连字符
//    strategy.setTablePrefix("RP_");
    // 公共父类
//    strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
    // 写于父类中的公共字段
//    strategy.setSuperEntityColumns("id");
//    strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
    strategy.setInclude("tbComShop");

    strategy.setControllerMappingHyphenStyle(true);
//    strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀
//    strategy.setTablePrefix("STOCK_"); //生成实体时去掉表前缀
    mpg.setStrategy(strategy);

    String ModuleName = "ebms";
    // 包配置
    PackageConfig pc = new PackageConfig();
    pc.setParent("com.tadpole.cloud.externalSystem.modular." + ModuleName);
    pc.setXml("mapper.mapping");
    mpg.setPackageInfo(pc);
    // 自定义配置
    InjectionConfig cfg = new InjectionConfig() {
      @Override
      public void initMap() {
        // to do nothing
      }
    };

    // 如果模板引擎是 freemarker
    String templatePath = "/templates/mapper.xml.ftl";
    String paramTemplatePath = "/templates/param.java.ftl";
    String resultTemplatePath = "/templates/result.java.ftl";

    // 自定义输出配置
    List<FileOutConfig> focList = new ArrayList<>();

    // 自定义配置会被优先输出
    focList.add(new FileOutConfig(templatePath) {
      @Override
      public String outputFile(TableInfo tableInfo) {
        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/resources/mappers/" + pc.getModuleName()
        return projectPath + "/externalSystem-app/src/main/java/com/tadpole/cloud/externalSystem/modular/"
                + ModuleName
                + "/mapper/mapping/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
      }
    });

    focList.add(new FileOutConfig(paramTemplatePath) {
      @Override
      public String outputFile(TableInfo tableInfo) {
        return projectPath + "/externalSystem-app/src/main/java/com/tadpole/cloud/externalSystem/modular/"
                + ModuleName
                + "/model/params/" + tableInfo.getEntityName() + "Param" + StringPool.DOT_JAVA;
      }
    });

    focList.add(new FileOutConfig(resultTemplatePath) {
      @Override
      public String outputFile(TableInfo tableInfo) {
        return projectPath + "/externalSystem-app/src/main/java/com/tadpole/cloud/externalSystem/modular/"
                + ModuleName
                + "/model/result/" + tableInfo.getEntityName() + "Result" + StringPool.DOT_JAVA;
      }
    });

    cfg.setFileOutConfigList(focList);
    mpg.setCfg(cfg);

    // 配置模板
    TemplateConfig templateConfig = new TemplateConfig();

    // 配置自定义输出模板（如果不配置这些，则会按照官方的配置进行生成）
    //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
    templateConfig.setEntity("templates/entity.java");
    templateConfig.setMapper("templates/mapper.java");
    templateConfig.setXml(null);
    templateConfig.setController("templates/controller.java");
    templateConfig.setServiceImpl("templates/serviceImpl.java");
    //设置为空则代表不需要生成
    templateConfig.setXml(null);
    mpg.setTemplate(templateConfig);

    // 执行生成
    mpg.execute();

    // 打印注入设置【可无】
//    System.err.println(mpg.getCfg().getMap().get("abc"));
  }
}

