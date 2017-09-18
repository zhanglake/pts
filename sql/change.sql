-- 2016-02-22 --
alter table SY_COMPANY_DEALER add inner_code nvarchar2(20) null;

comment on column SY_COMPANY_DEALER.inner_code
  is '企业内部编码';
  
alter table SY_COMPANY_SUPPLIER add inner_code nvarchar2(20) null;

comment on column SY_COMPANY_SUPPLIER.inner_code
  is '企业内部编码';
  
-- 2016-02-22 --
alter table SY_PDA_DEVICE add stock_id INTEGER;

comment on column SY_PDA_DEVICE.stock_id
  is '库位号';
  
-- 2016-02-22 --  
alter table SY_PURCHASE_ORDER modify order_no NVARCHAR2(50);
alter table SY_PURCHASE_ORDER add creator NVARCHAR2(20);
alter table SY_PURCHASE_ORDER modify order_type NVARCHAR2(20);
alter table SY_PURCHASE_ORDER modify order_from NVARCHAR2(50);
alter table SY_PURCHASE_ORDER modify create_time NVARCHAR2(20);
alter table SY_PURCHASE_ORDER modify remark NVARCHAR2(100);

alter table SY_PURCHASE_ORDER add status INTEGER;
comment on column SY_PURCHASE_ORDER.status
  is '二维码生成状态';
comment on column SY_PURCHASE_ORDER.creator
  is '创建人';
  
-- 2016-02-26 --

alter table SY_ORDER_ITEM_MAP add supplier_name NVARCHAR2(50);
alter table SY_ORDER_ITEM_MAP add product_code NVARCHAR2(50);
alter table SY_ORDER_ITEM_MAP modify product_name NVARCHAR2(100);

comment on column SY_ORDER_ITEM_MAP.product_code
  is '产品编码';

comment on column SY_ORDER_ITEM_MAP.supplier_id
  is '生产供应商ID';
comment on column SY_ORDER_ITEM_MAP.supplier_name
  is '生产供应商编码';
  
 
-- 2016-03-03 --

alter table SYSUSER add token NVARCHAR2(50);
alter table SYSUSER add expire_time NVARCHAR2(20);

comment on column SYSUSER.token
  is 'API的token';
comment on column SYSUSER.expire_time
  is '过期时间';
  
-- 2016-03-04 --
alter table SY_PRODUCT modify code NVARCHAR2(50);
alter table SY_PRODUCT modify name NVARCHAR2(100);
alter table SY_PRODUCT modify category NVARCHAR2(20);
alter table SY_PRODUCT add price number(8,2);
alter table SY_PRODUCT add specno nvarchar2(50);

comment on column SY_PRODUCT.price
  is '售价';
comment on column SY_PRODUCT.specno
  is '规格型号';
  
alter table SY_PACKAGE add code_old NVARCHAR2(50);
comment on column SY_PACKAGE.code_old
  is '旧包装编码';
  
-- 2016-03-08 --
CREATE TABLE "PTS"."SY_QRCODE" 
   (	"ID" NUMBER(*,0), 
	"QRCODE" NVARCHAR2(100), 
	"PRODUCT_ID" NUMBER(*,0), 
	"COMPANY_ID" NUMBER(*,0), 
	"SUPPLIER_ID" NUMBER(*,0), 
	"STATUS" NUMBER(*,0), 
	"CREATE_TIME" NVARCHAR2(20), 
	"PRINT_TIME" NVARCHAR2(20), 
	"REMARK" NVARCHAR2(100), 
	"PACKAGE_LEVEL" NUMBER(*,0), 
	"PACKAGE_ID" NUMBER(*,0), 
	"SALT" NVARCHAR2(10)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 16384 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "PTS" ;
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."ID" IS '自增长ID';
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."QRCODE" IS '二维码';
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."PRODUCT_ID" IS '产品ID';
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."COMPANY_ID" IS '企业ID';
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."SUPPLIER_ID" IS '生产供应商ID';
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."STATUS" IS '状态 0-不可用 1-已生成 2-已打印 3-已包装 4-已入库 5-已出库 6-已扫描 7-已退货 8-已换货  9-已作废';
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."CREATE_TIME" IS '生成时间';
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."PRINT_TIME" IS '打印时间';
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."REMARK" IS '备注';
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."PACKAGE_LEVEL" IS '包装层级 1-内包装二维码 2-外包装二维码';
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."PACKAGE_ID" IS '包装ID';
 
   COMMENT ON COLUMN "PTS"."SY_QRCODE"."SALT" IS '加密salt';
 
   COMMENT ON TABLE "PTS"."SY_QRCODE"  IS '二维码表';
   
   
  -- 2016-03-08 --
  
  create table SY_MESSAGE  (
   ID                   INTEGER,
   MOBILE               NVARCHAR2(20),
   CODE                 NVARCHAR2(10),
   CONTENT              NVARCHAR2(200),
   SEND_TIME            NVARCHAR2(20)
);

comment on table SY_MESSAGE is
'短信表';

comment on column SY_MESSAGE.ID is
'自增长ID';

comment on column SY_MESSAGE.MOBILE is
'手机号';

comment on column SY_MESSAGE.CODE is
'验证码';

comment on column SY_MESSAGE.CONTENT is
'短信内容';

comment on column SY_MESSAGE.SEND_TIME is
'发送时间';


create table "SY_SCANNING_RECORD"  (
   ID                   INTEGER,
   QRCODE               NVARCHAR2(100),
   CREATE_TIME          NVARCHAR2(20),
   OPERATOR             NVARCHAR2(50),
   OPERATOR_ID          INTEGER,
   ACTION_TYPE          INTEGER
);

comment on table "sy_scanning_record" is
'扫描记录';

comment on column "sy_scanning_record".ID is
'自增长ID';

comment on column "sy_scanning_record".QRCODE is
'二维码';

comment on column "sy_scanning_record".CREATE_TIME is
'扫描时间';

comment on column "sy_scanning_record".OPERATOR is
'操作人';

comment on column "sy_scanning_record".OPERATOR_ID is
'操作人ID';

comment on column "sy_scanning_record".ACTION_TYPE is
'操作类型 1-包装 2-入库 3-出库 4-经销商入库 5-经销商溯源 6-终端用户溯源 7-内部溯源';


-- 2016-03-15 --
create table SY_PACKAGE_QRCODE_MAP  (
   ID                   INTEGER,
   QRCODE_INNER         NVARCHAR2(50),
   QRCODE_OUTER         NVARCHAR2(50)
);

comment on table SY_PACKAGE_QRCODE_MAP is
'二维码包装关系表';

comment on column SY_PACKAGE_QRCODE_MAP.ID is
'自增长ID';

comment on column SY_PACKAGE_QRCODE_MAP.QRCODE_INNER is
'内包装二维码';

comment on column SY_PACKAGE_QRCODE_MAP.QRCODE_OUTER is
'外包装二维码';

-- 2016-03-16 --
Update SysFunction Set fnctnnm = '生产信息', page = 'productInfo/productInfoPage.do' Where fnctnid = 'ProductManager'

-- 2016-03-18 --
comment on column SY_QRCODE.status
  is '状态 0-已生成 1-已绑定商品 2-已打印 3-已包装 4-已入库 5-已出库 6-已扫描 7-已作废';

-- 2016-03-19 --

create table SY_APPLY_APPROVAL  (
   ID                   INTEGER                         not null,
   APPLY_ID             INTEGER,
   SUPPLIER_ID          INTEGER,
   APPROVAL_ID          INTEGER,
   TYPE                 INTEGER,
   STATUS               INTEGER,
   COMMENTS             NVARCHAR2(200),
   PRODUCT_ID           INTEGER,
   COUNT                INTEGER,
   constraint PK_SY_APPLY_APPROVAL primary key (ID)
);

comment on table SY_APPLY_APPROVAL is
'申请审批表';

comment on column SY_APPLY_APPROVAL.ID is
'自增长ID';

comment on column SY_APPLY_APPROVAL.APPLY_ID is
'申请人ID';

comment on column SY_APPLY_APPROVAL.SUPPLIER_ID is
'生产供应商ID';

comment on column SY_APPLY_APPROVAL.APPROVAL_ID is
'审批人';

comment on column SY_APPLY_APPROVAL.TYPE is
'类型';

comment on column SY_APPLY_APPROVAL.STATUS is
'状态 0-申请 1-已审批';

comment on column SY_APPLY_APPROVAL.COMMENTS is
'审批内容';

comment on column SY_APPLY_APPROVAL.PRODUCT_ID is
'产品ID';

comment on column SY_APPLY_APPROVAL.COUNT is
'产品数量';


--2016-03-21--终端用户 平台用户--

Insert into SysFunction Values ('DeviceUser', '终端用户', 1, 'account/endUserPage.do', 3, 1, 'SystemModule', null, 2, 1);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (111, 'DeviceUser', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 1 And m.fnctnID = 'DeviceUser';

Insert Into sysfunction(fnctnid, fnctnnm, mdlid, page, stno, sts, prntid, icn, pgtp, systp) 
Values('CompanyPDAUser', '企业出入库用户', 2, 'account/pdaUserPage.do', 1, 1, 'AccountModule', '', 2, 2);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (112, 'CompanyPDAUser', 'btnView');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (113, 'CompanyPDAUser', 'btnRefresh');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (114, 'CompanyPDAUser', 'btnAddNew');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (115, 'CompanyPDAUser', 'btnEdit');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 1 And m.fnctnID = 'CompanyPDAUser';


--2016-03-29--
Update SysFunction Set fnctnnm = '二维码查询', page = 'qrCode/qrCodePage.do' Where fnctnid = 'QRCodeModule';
Update SysFunction Set sts = 0 Where fnctnid = 'QRCodeList';
Update SysFunction Set fnctnnm = '出入库记录', page = 'trace/inOutPage.do' Where fnctnid = 'TraceQueryModule';
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (117, 'TraceQueryModule', 'btnRefresh');


--2016-03-31--
Update SysFunction Set page = '' Where fnctnid = 'TraceQueryModule';

Insert into SysFunction Values ('InQuery', '入库记录', 7, 'trace/inQuery.do', 1, 1, 'TraceQueryModule', '', 2, 2);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (117, 'InQuery', 'btnView');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (118, 'InQuery', 'btnRefresh');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'InQuery';

Insert into SysFunction Values ('OutQuery', '出库记录', 7, 'trace/outQuery.do', 2, 1, 'TraceQueryModule', '', 2, 2);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (119, 'OutQuery', 'btnView');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (120, 'OutQuery', 'btnRefresh');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'OutQuery';

Insert into SysFunction Values ('RecieveQuery', '收货记录', 2, 'trace/receiveQuery.do', 2, 1, 'SYS', 'icon-table', 1, 4);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (121, 'RecieveQuery', 'btnView');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (122, 'RecieveQuery', 'btnRefresh');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 4 And m.fnctnID = 'RecieveQuery';
Update SysFunction Set prntid = 'SYS' Where fnctnid = 'RecieveQuery'

Insert into SysFunction Values ('SupplierUserModule', '用户管理', 1, '', 1, 1, 'SYS', 'icon-user', 1, 3);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (123, 'SupplierUserModule', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 3 And m.fnctnID = 'SupplierUserModule';

Insert into SysFunction Values ('PDAPackageUser', '包装用户', 1, 'account/pkgUser.do', 1, 1, 'SupplierUserModule', '', 2, 3);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (124, 'PDAPackageUser', 'btnView');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (125, 'PDAPackageUser', 'btnRefresh');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (126, 'PDAPackageUser', 'btnAdd');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (127, 'PDAPackageUser', 'btnEdit');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 3 And m.fnctnID = 'PDAPackageUser';


--2016-04-12--
Update SysFunction Set page = '', fnctnnm = '订单管理' Where fnctnid = 'OrderModule';

Insert into SysFunction Values ('PurchaseOrder', '采购订单', 6, 'order/orderPage.do', 1, 1, 'OrderModule', '', 2, 2);

Update SysActionFunctionMap Set fnctnId = 'PurchaseOrder' Where fnctnId = 'OrderModule';

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (130, 'OrderModule', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'OrderModule';

Insert into SysFunction Values ('SalesOrder', '出库订单', 6, 'order/salesOrder.do', 2, 1, 'OrderModule', '', 2, 2);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (128, 'SalesOrder', 'btnView');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (129, 'SalesOrder', 'btnRefresh');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'SalesOrder';

Update SysFunction Set mdlId = 5 Where fnctnid = 'OrderModule';
Update SysFunction Set mdlId = 6 Where fnctnid = 'QRCodeModule';
Update SysFunction Set mdlId = 5 Where prntId = 'OrderModule';

Update SysFunction Set icn = 'icon-tasks' Where fnctnid = 'OrderModule';

--2016-04-28--kzj
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (131, 'SalesOrder', 'btnAdd');
Insert into SysPermission (rlid, Mpid) values (2, 131);

--2016-04-29--
Insert Into SysAction(Actnid, Actnlbl, Atcnky, Stno) Values('btnSync', '同步', 'doSync', 10);
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (132, 'PurchaseOrder', 'btnSync');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (133, 'SalesOrder', 'btnSync');
Insert into SysPermission (rlid, Mpid) values (2, 132);
Insert into SysPermission (rlid, Mpid) values (2, 133);

--2016-05-03--
Select * From SysFunction Where fnctnID = 'APPDeviceMgr';
Insert into SysFunction Values ('BasicDataPlat', '基础数据', 1, '', 1, 1, 'SYS', 'icon-cogs', 1, 1);
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (134, 'BasicDataPlat', 'btnView');
Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 1 And m.fnctnID = 'BasicDataPlat';

Update SysFunction Set sysTp = 1, prntId = 'BasicDataPlat', mdlid = 3, stno = 3, fnctnnm = 'APP装机量' Where fnctnId = 'APPDeviceMgr';
Update SysPermission Set rlid = 1 Where mpid = 15 or mpid = 55;

Update SysFunction Set fnctnnm = '用户管理', icn = 'icon-user', mdlid = 2, stno = 2 Where fnctnId = 'SystemModule';
Update SysFunction Set mdlid = 3, stno = 3 Where fnctnid = 'LogModuleSys';
Update SysFunction Set prntid = 'BasicDataPlat' Where fnctnid = 'CompanyMgr' Or fnctnid = 'DealerManager';

Update SysFunction Set mdlid = 1, stno = 1 Where fnctnid = 'CompanyMgr';
Update SysFunction Set mdlid = 2, stno = 2 Where fnctnid = 'DealerManager';

Update SysFunction Set fnctnnm = '销售订单' Where fnctnid = 'SalesOrder';

--2016-05-06--
Update SysFunction Set fnctnnm = '二维码管理', page = '' Where fnctnId = 'QRCodeModule';
Update SysFunction Set sts = 1, mdlId = 6 Where fnctnId = 'QRCodeList';
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (135, 'QRCodeList', 'btnRefresh');
Insert into SysPermission (rlid, Mpid) values (2, 135);

Update SysFunction Set fnctnnm = '物品类型' Where fnctnnm = '产品类型';
Update SysFunction Set fnctnnm = '产品管理' Where fnctnnm = '物品管理';
Update SysFunction Set fnctnnm = '包装记录' Where fnctnnm = '包装记录查询';
Update SysFunction Set mdlId = 1 Where fnctnId = 'PurchaseOrder';
Update SysFunction Set mdlId = 2 Where fnctnId = 'SalesOrder';
Update SysFunction Set mdlId = 3 Where fnctnId = 'ProductManager';
Update SysFunction Set mdlId = 4 Where fnctnId = 'PackageQuery';

--2016-05-13--
Insert Into SysAction(Actnid, Actnlbl, Atcnky, Stno) Values('btnCreate', '生成二维码', 'doCreate', 11);
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (136, 'PurchaseOrder', 'btnCreate');
Insert into SysPermission (rlid, Mpid) values (2, 136);

--2016-05-25--
Alter Table Sy_Qrcode Add serialNo NVARCHAR2(50);

--2016-05-26--
Alter Table Sy_Sales_Order Add isRestrict Integer;

Insert Into SysDataType(dctcd, tpcd, tpnm, tpid, cmnts, stno, sts, Company_Id)
Values('0', '0', '销售单出库约束', 300, '约束', 1, 1, 2);

--2016-06-03--
Insert Into SysDataType(dctcd, tpcd, tpnm, tpid, cmnts, stno, sts, Company_Id)
Values('WFTECH', 'WFTECH-A', '威孚企业', 400, '威孚', 1, 1, 2);
Insert Into SysDataType(dctcd, tpcd, tpnm, tpid, cmnts, stno, sts, Company_Id)
Values('TLTECH', 'TLTECH-A', '天力企业', 401, '天力', 1, 1, 3);

--2016-06-06--
Alter Table Sy_Sales_Order Add UNITNO NVARCHAR2(50);
Alter Table Sy_Sales_Order Add UNITNAME NVARCHAR2(200);
Alter Table Sy_Sales_Order Add SYNCDATE NVARCHAR2(20);

--2016-06-08--
Insert into SysFunction Values ('OrderRecord', '订货查询', 2, 'order/order.do', 2, 1, 'SYS', 'icon-tasks', 1, 4);
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (138, 'OrderRecord', 'btnView');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (139, 'OrderRecord', 'btnRefresh');
Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 1 And m.fnctnID = 'OrderRecord';
Update SysFunction Set mdlid = 3, stno = 3 Where fnctnid = 'RecieveQuery';


Insert Into SysAction(Actnid, Actnlbl, Atcnky, Stno) Values('btnImport', '批量导入', 'doImport', 12);
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (140, 'DealerManagerComp', 'btnImport');
Insert into SysPermission (rlid, Mpid) values (2, 140);

--2016-06-28--

--产品加创建时间、修改时间、下发二维码到MS字段--
Alter Table Sy_Product Add (CREATETIME NVARCHAR2(20), UPDATETIME NVARCHAR2(20), ISISSUED INTEGER);
--产品管理改为二级菜单--
Update SysFunction Set page = '' Where fnctnid = 'ProductModule';
Insert into SysFunction Values ('ProductInfo', '产品信息', 3, 'product/productPage.do', 1, 1, 'ProductModule', '', 1, 2);
Update SysActionFunctionMap Set fnctnId = 'ProductInfo' Where mpId in (67, 68, 69, 101);
--删除采购订单编辑按钮--
Delete From SysPermission Where mpId = 96 And rlid = 2;


--2016-07-04 企业出入库记录操作员 --
Update SysFunction Set fnctnnm = 'PDA出入库用户' Where fnctnnm = '企业出入库用户';

Insert Into SysFunction(fnctnid, fnctnnm, mdlid, page, stno, sts, prntid, icn, pgtp, systp) 
Values('CompanyOtherUser', '其他操作用户', 2, 'account/otherUser.do', 4, 1, 'AccountModule', '', 2, 2);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (141, 'CompanyOtherUser', 'btnView');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (142, 'CompanyOtherUser', 'btnRefresh');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (143, 'CompanyOtherUser', 'btnAdd');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (144, 'CompanyOtherUser', 'btnEdit');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'CompanyOtherUser';

--2016-07-07--
Alter Table Sy_Qrcode Add CANPRINT INTEGER;
COMMENT ON COLUMN Sy_Qrcode.CANPRINT IS '可打印标记 1-已标记 0-未标记';

--2016-07-18--
Insert Into SysAction(Actnid, Actnlbl, Atcnky, Stno) Values('btnExport', '导出', 'doExport', 13);
Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(145, 'CompanyPDAUser', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 145);

--2016-07-20--
Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(146, 'QRCodeList', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 146);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(147, 'LogModule', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 147);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(148, 'LogModule', 'btnRefresh');
Insert Into Syspermission(rlid, mpid) Values(2, 148);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(149, 'OperateLog', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(1, 149);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(150, 'ExceptionLog', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(1, 150);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(151, 'InQuery', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 151);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(152, 'OutQuery', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 152);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(153, 'PackageQuery', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(3, 153);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(154, 'OrderQuery', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(3, 154);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(155, 'PDAPackageUser', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(3, 155);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(156, 'ProductInfo', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 156);

Insert into SysFunction Values ('PackageSetting', '包装设置', 3, 'product/packagePage.do', 2, 1, 'ProductModule', '', 2, 2);
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (157, 'PackageSetting', 'btnView');
Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'PackageSetting';

Delete From SysPermission Where mpid = 101 And rlId = 2;


--2016-07-21--
Alter Table Sy_Qrcode Add (ISSUCCESS INTEGER Default 0, ORDERNO NVARCHAR2(50));
comment on column Sy_Qrcode.isSuccess is '下发MS是否成功';
comment on column Sy_Qrcode.Orderno is '订单ID';

Insert Into SysAction(Actnid, Actnlbl, Atcnky, Stno) Values('btnIssue', '下发MS', 'doIssue', 14);
Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(158, 'PurchaseOrder', 'btnIssue');
Insert Into Syspermission(rlid, mpid) Values(2, 158);

--2016-07-22--
Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(159, 'ProductManager', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(3, 159);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(160, 'SupplierMgr', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 160);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(161, 'UserMgr', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 161);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(162, 'CompanyOtherUser', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 162);

Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(163, 'DealerMgr', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 163);

--2016-07-25--
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (164, 'ProductInfo', 'btnImport');
Insert into SysPermission (rlid, Mpid) values (2, 164);

--2016-08-01--
Alter Table Sy_Sales_Order Add (ORDTYPE INTEGER);
Comment On Column Sy_Sales_Order.ordType is '订单类型 1-手动创建 2-金算盘同步';

/*修改原字段名kingId为name_tmp*/
Alter Table Sy_Sales_Order Rename Column kingId to name_tmp;

/*增加一个和原字段名同名的字段kingId*/
Alter Table Sy_Sales_Order Add kingId nvarchar2(50);

/*将原字段name_tmp数据更新到增加的字段kingId*/
Update Sy_Sales_Order Set kingId = Trim(name_tmp);

/*更新完，删除原字段name_tmp*/
Alter Table Sy_Sales_Order Drop Column name_tmp;

Update Sy_Sales_Order Set ordType = 2 Where kingId is not null;
Update Sy_Sales_Order Set ordType = 1 Where kingId is  null;

--2016-08-17--
Alter Table Sy_Qrcode Add SALEORDERID INTEGER DEFAULT 0;
Comment On Column Sy_Qrcode.SALEORDERID is '销售单ID';

--2016-08-19--
Update SysAction Set stno = 15 Where actnId = 'btnPackage';
Update SysAction Set stno = 16 Where actnId = 'btnIn';
Update SysAction Set stno = 17 Where actnId = 'btnOut';
Update SysAction Set stno = 18 Where actnId = 'btnEmOut';
Update SysAction Set stno = 19 Where actnId = 'btnReturn';
Update SysAction Set stno = 20 Where actnId = 'btnVoid';
Update SysAction Set stno = 21 Where actnId = 'btnRecruit';

Update SysFunction Set mdlId = 9, stno = 9 Where fnctnid = 'LogModule' And systp = 2 And prntid = 'SYS';
Update SysFunction Set stNo = 2 Where fnctnid = 'AccountModule' And systp = 2 And prntid = 'SYS';
Update SysFunction Set stNo = 3 Where fnctnid = 'ProductModule' And systp = 2 And prntid = 'SYS';
Update SysFunction Set stNo = 4 Where fnctnid = 'PackageModule' And systp = 2 And prntid = 'SYS';
Update SysFunction Set stNo = 5 Where fnctnid = 'OrderModule' And systp = 2 And prntid = 'SYS';
Update SysFunction Set stNo = 6 Where fnctnid = 'QRCodeModule' And systp = 2 And prntid = 'SYS';
Update SysFunction Set stNo = 7 Where fnctnid = 'TraceQueryModule' And systp = 2 And prntid = 'SYS';
Update SysFunction Set stNo = 11 Where fnctnid = 'DeviceModule' And systp = 2 And prntid = 'SYS';

Insert into SysFunction Values ('DataReport', '数据报表', 8, '', 8, 1, 'SYS', 'icon-list', 1, 2);
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (165, 'DataReport', 'btnView');
Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'DataReport';

Insert into SysFunction Values ('EndUserPointReport', '用户积分报表', 1, 'report/endUserPoints.do', 1, 1, 'DataReport', '', 2, 2);
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (166, 'EndUserPointReport', 'btnView');
Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(167, 'EndUserPointReport', 'btnRefresh');
Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'EndUserPointReport';

--2016-10-13--
Insert into SysFunction Values ('SalesReport', '销售报表', 8, 'report/salesReport.do', 1, 1, 'DataReport', '', 2, 2);
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (168, 'SalesReport', 'btnView');
Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(169, 'SalesReport', 'btnRefresh');
Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'SalesReport';

Insert into SysFunction Values ('ScanReport', '扫码报表', 8, 'report/scanReport.do', 2, 1, 'DataReport', '', 2, 2);
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (170, 'ScanReport', 'btnView');
Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(171, 'ScanReport', 'btnRefresh');
Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'ScanReport';

Update SysFunction Set stno = 3, mdlId = 8 Where fnctnId = 'EndUserPointReport';

--2016-10-14--
Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(172, 'SalesReport', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 172);
Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(173, 'ScanReport', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 173);

--2016-10-24--
Insert Into Sysactionfunctionmap(mpid, Fnctnid, Actnid) Values(174, 'EndUserPointReport', 'btnExport');
Insert Into Syspermission(rlid, mpid) Values(2, 174);
