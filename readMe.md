域名：http://config.17doubao.com

接口说明：
  1.项目接口
    1.1 项目参数说明
       configKey  主键
       application 项目应用名称
       profile 项目应用模块
       type  0表示基本配置比如 mysql,activeMQ    1 表示项目配置比如 客服，理赔
       
    1.2  type== 0的项目可配置具体项目属性 
        比如 MySQL项目可以配置 datasource.url   datasource.username  datasource.password
    

    2.1 .新增项目  configRepository/save 
        
    3.1  更新项目   configRepository/update   
        
    4.1  删除项目    configRepository/delete     
    
    5.1  查询项目   configRepository/findAll
         可根据项目参数查询
         
    
  2.项目属性接口
    2.1 项目属性参数说明
       propertiesKey 主键
       configKey 项目主键
       propertiesName 属性名
       propertiesValue 属性值
       
    2.2 新增项目属性  configProperties/save    
    
    2.3 更新项目属性 configProperties/update
    
    2.4 删除项目属性  configProperties/delete
    
    2.5 查询项目属性  configProperties/findAll
       可根据项目属性参数查询
    



