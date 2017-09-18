Update SysRole Set sysTp = 2 Where rlId = 2;
Insert Into SysUser(usrId, rlId, Lgnnm, Pswd, Sts) Values(2, 2, 'cadmin', '441a5fb76039cb8cc1e0e58825edfcbf', 1);
Insert Into SysRole(rlid, rlnm, sts, cmnts, systp) Values(3, '供应商用户', 1, '供应商用户', 3);
Insert Into SysRole(rlid, rlnm, sts, cmnts, systp) Values(4, '一级经销商', 1, '一级经销商', 4);
Insert Into SysUser(usrId, rlId, Lgnnm, Pswd, Sts) Values(3, 3, 'padmin', 'c5edac1b8c1d58bad90a246d8f08f53b', 1);
Insert Into SysUser(usrId, rlId, Lgnnm, Pswd, Sts) Values(4, 4, 'dadmin', '8b5f63eefa7d3ca3bb5ba5aa3dc8c40b', 1);
