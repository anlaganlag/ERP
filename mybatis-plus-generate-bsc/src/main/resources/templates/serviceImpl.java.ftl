package ${package.ServiceImpl};

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import ${cfg.result}.*;
import ${cfg.params}.*;
import ${cfg.entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
/**
* <p>
* ${table.comment!} 服务实现类
* </p>
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

  @DataSource(name = "***")
  @Override
  public void save(${entity}Param param){
    ${entity} entity=new ${entity}();
    BeanUtils.copyProperties(param,entity);
    entity.setCreateName(LoginContext.me().getLoginUser().getName());
    this.baseMapper.insert(entity);
  }

  @DataSource(name = "${cfg.database}")
  @Override
  public ${entity}Result queryById(Long id){
    ${entity} entity =  this.baseMapper.selectById(id);
    ${entity}Result result=new ${entity}Result();
    if (entity!=null){
      BeanUtils.copyProperties(entity,result);
    }
    return result;
  }
}
</#if>
