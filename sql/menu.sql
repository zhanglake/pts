Update SysFunction Set SysTp = 2;
Update SysFunction Set SysTp = 1 Where fnctnid = 'SystemModule';
Update SysFunction Set SysTp = 1 Where fnctnid = 'RoleMgr';






--平台用户菜单--
--用户管理
Insert into SysFunction Values ('UserManeger', '用户管理', 1, 'account/userPage.do', 2, 1, 'SystemModule', null, 2, 1);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (21, 'UserManeger', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 1 And m.fnctnID = 'UserManeger';

Update SysFunction Set pgTp = 2 Where prntid = 'SystemModule';

--企业管理
Insert into SysFunction Values ('CompanyMgr', '企业管理', 2, 'company/comPage.do', 1, 1, 'SYS', 'icon-user-md', 1, 1);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (22, 'CompanyMgr', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 1 And m.fnctnID = 'CompanyMgr';

--经销商管理
Insert into SysFunction Values ('DealerManager', '经销商管理', 3, 'dealer/dealerPage.do', 1, 1, 'SYS', 'icon-sitemap', 1, 1);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (23, 'DealerManager', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 1 And m.fnctnID = 'DealerManager';

--日志管理--
Insert into SysFunction Values ('LogModuleSys', '日志管理', 1, '', 1, 1, 'SYS', null, 1, 1);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (31, 'LogModuleSys', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 1 And m.fnctnID = 'LogModuleSys';

--操作日志--
Insert into SysFunction Values ('OperateLog', '操作日志', 1, 'sysLog/operateLog.do', 1, 1, 'LogModuleSys', null, 2, 1);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (32, 'OperateLog', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 1 And m.fnctnID = 'OperateLog';

--异常日志--
Insert into SysFunction Values ('ExceptionLog', '异常日志', 1, 'sysLog/exceptionLog.do', 2, 1, 'LogModuleSys', null, 2, 1);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (33, 'ExceptionLog', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 1 And m.fnctnID = 'ExceptionLog';


--权限--

Update SysPermission Set rlid = 2 Where mpid not in (1, 8, 21, 22, 23);









--供应商菜单--

--产品管理
Insert into SysFunction Values ('ProductManager', '产品管理', 1, 'product/productPage.do', 1, 1, 'SYS', 'icon-road', 1, 3);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (24, 'ProductManager', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 3 And m.fnctnID = 'ProductManager';

--订单查询
Insert into SysFunction Values ('OrderQuery', '订单查询', 2, 'order/orderPage.do', 1, 1, 'SYS', 'icon-tasks', 1, 3);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (25, 'OrderQuery', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 3 And m.fnctnID = 'OrderQuery';

--包装记录查询
Insert into SysFunction Values ('PackageQuery', '包装记录查询', 3, 'package/packagePage.do', 1, 1, 'SYS', ' icon-th-list', 1, 3);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (26, 'PackageQuery', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 3 And m.fnctnID = 'PackageQuery';










--经销商菜单--

--用户管理
Insert into SysFunction Values ('DealerUserMgr', '用户管理', 1, 'account/userPage.do', 1, 1, 'SYS', ' icon-user', 1, 4);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (27, 'DealerUserMgr', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 4 And m.fnctnID = 'DealerUserMgr';






--企业用户菜单--

--基础数据
Insert into SysFunction Values ('BasicData', '基础数据', 1, '', 1, 1, 'SYS', 'icon-bar-chart', 1, 2);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (28, 'BasicData', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'BasicData';

--供应商管理
Insert into SysFunction Values ('SupplierManager', '供应商管理', 1, '', 1, 1, 'BasicData', 'supplier/supplierPage.do', 2, 2);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (29, 'SupplierManager', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'SupplierManager';

--经销商管理
Insert into SysFunction Values ('DealerManagerComp', '经销商管理', 1, '', 2, 1, 'BasicData', 'dealer/dealerPage.do', 2, 2);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (30, 'DealerManagerComp', 'btnView');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'DealerManagerComp';

Update SysFunction Set sts = 0 Where fnctnid = 'DeviceModule';
Update SysFunction Set prntid = 'BasicData' Where prntid = 'DeviceModule';
Update SysFunction Set mdlid = 1 Where prntid = 'BasicData';
Update SysFunction Set stno = 3 Where fnctnid = 'APPDeviceMgr';
Update SysFunction Set stno = 4 Where fnctnid = 'PDADeviceMgr';
Update SysFunction Set icn = 'icon-qrcode' Where fnctnnm = '二维码管理';
Update SysFunction Set icn = 'icon-briefcase', fnctnnm = '包装管理' Where fnctnnm = '包装规则管理';
Update SysFunction Set icn = 'icon-sitemap', fnctnnm = '采购订单' Where fnctnnm = '采购订单管理';
Update SysFunction Set icn = 'icon-file-text' Where fnctnnm = '日志管理';
Update SysFunction Set icn = 'icon-upload-alt' Where fnctnnm = '溯源查询';
Update SysFunction Set icn = 'icon-table' Where fnctnnm = '产品管理';
Update SysFunction Set page = '' Where fnctnid = 'QRCodeModule';
Update SysFunction Set page = 'qrCode/qrCodeListPage.do' Where fnctnid = 'QRCodeList';

--产品类型--
Insert into SysFunction Values ('ProductType', '产品类型', 1, 'sysDataType/toPage.do?type=50', 5, 1, 'BasicData', '', 2, 2);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (81, 'ProductType', 'btnView');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (82, 'ProductType', 'btnAdd');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (83, 'ProductType', 'btnRefresh');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (84, 'ProductType', 'btnEdit');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'ProductType';


--包装类型--
Insert into SysFunction Values ('PackageType', '包装类型', 1, 'sysDataType/toPage.do?type=51', 6, 1, 'BasicData', '', 2, 2);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (85, 'PackageType', 'btnView');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (86, 'PackageType', 'btnAdd');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (87, 'PackageType', 'btnRefresh');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (88, 'PackageType', 'btnEdit');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'PackageType';

-- 库位管理 2016-02-23 --
Insert into SysFunction Values ('StockMgr', '库位管理', 1, 'stock/stockPage.do', 7, 1, 'BasicData', '', 2, 2);

--二维码尺寸2016-02-25--
Insert into SysFunction Values ('QRCodeSize', '二维码尺寸', 1, 'sysDataType/toQRPage.do?type=52', 7, 1, 'BasicData', '', 2, 2);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (97, 'QRCodeSize', 'btnView');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (98, 'QRCodeSize', 'btnAdd');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (99, 'QRCodeSize', 'btnRefresh');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (100, 'QRCodeSize', 'btnEdit');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 2 And m.fnctnID = 'QRCodeSize';

--PDA权限管理 2016-03-04--
Insert into SysFunction Values ('PDAPermission', 'PDA权限管理', 4, '', 1, 1, 'SYS', '', 1, 3);

Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (102, 'PDAPermission', 'btnPackage');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (103, 'PDAPermission', 'btnOut');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (104, 'PDAPermission', 'btnIn');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (105, 'PDAPermission', 'btnEmOut');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (106, 'PDAPermission', 'btnReturn');
Insert into SysActionFunctionMap (mpID, fnctnID, ActnID) Values (107, 'PDAPermission', 'btnVoid');

Insert into SysPermission (rlID, mpID)
Select r.rlID, m.mpID
From SysRole r, SysActionFunctionMap m
Where r.SysTp = 3 And m.fnctnID = 'PDAPermission';