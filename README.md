# 房屋说明书 Android 多dimens文件适配方案 (手机/平板)
![](https://i.imgur.com/TpEbtzs.png)

## 步骤

### 1.下载Jar包 
[GenerateValueDimensFiles.jar](https://github.com/zzice/GenerateValueDimensFiles/blob/master/GenerateValueDimensFiles.jar?raw=true)
### 2.运行Jar包 

#### 运行命令: 

` java -jar GenerateValueDimensFiles.jar {isHasLandscape} `

#### 参数说明:	
`isHasLandscape` - 是否输出横屏dimens文件
(Example.需平板 即` java -jar GenerateValueDimensFiles.jar true `)

#### 运行说明: 
	1. 若无平板需求，双击GenerateValueDimensFiles.jar即可运行创建res文件夹，否则运行命令创建。
	2. 默认基准图大小  1080x1920 xxhdpi

------

##### 以下暂时废弃

![](https://i.imgur.com/Fe2sdth.png)
![](https://i.imgur.com/3Yi3opc.png)
![](https://i.imgur.com/R05fqvi.png)

### 3.复制res文件夹到项目自身res文件夹替换
### 4.开发使用
![](https://i.imgur.com/5KgzZul.png)

