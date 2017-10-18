<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>更新日志</title>
    <script type="text/javascript" src="<c:url value="/media/fm/jquery-1.10.1.min.js"/>"></script>
    <link href="<c:url value="/media/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css" />
    <style>
        body {
            background: #ecf2f1;
        }
        h1 {
            text-align: center;
        }
        .panel {
            width: 1024px;
            margin: auto;
            margin-bottom: 20px;
        }
        .panel-title {
            width: 984px;
            height: 40px;
            line-height: 40px;
            background-color: #005bae;
            border-radius: 10px 10px 0 0;
            padding: 0 20px;
            color: #fff;
        }
        .panel-body {
            width: 982px;
            line-height: 40px;
            background-color: #fff;
            border-radius: 0 0 10px 10px;
            border: 1px solid #ccc;
            padding: 20px;
            color: #666;
        }
        .body-title {
            width: 962px;
            margin: 0 10px;
            border-bottom: 1px dashed #ccc;
        }
        .body-content {
            width: 922px;
            margin: 0 10px;
            padding: 10px 20px;
            margin-bottom: 20px;
        }
        .content-line {
            display: block;
        }
        .del {
            display: inline-block;
            width: 10px;
            height: 10px;
            background-color: #2688c8;
            border-radius: 5px;
            margin-right: 10px;
        }
        .editor {
            float: right;
        }
        .editor > a {
            text-decoration: none;
            color: #fff;
            font-size: 14px;
        }
    </style>
</head>
<body>
<h1>更新日志</h1>
<div class="panel">
    <div class="panel-title">
        2017-10-16
        <span class="editor"><a href="mailto:zhenghua.zhang@weifu.com.cn?subject=关于溯源系统2017-10-16更新的疑问"><i class="icon-envelope"></i>&nbsp;张正华</a></span>
    </div>
    <div class="panel-body">
        <div class="body-title">
            功能优化
        </div>
        <div class="body-content">
            <span class="content-line"><i class="del"></i>非MS的二维码，在包装时自动入库，省去了仓库使用PDA入库的步骤</span>
            <span class="content-line"><i class="del"></i>MS的二维码，在出库时完成包装+入库+出库，省去了使用PDA包装和入库的步骤</span>
        </div>

        <div class="body-title">
            问题修复
        </div>
        <div class="body-content">
            <i class="del"></i>暂无
        </div>
    </div>
</div>
<div class="panel">
    <div class="panel-title">
        2017-10-11
        <span class="editor"><a href="mailto:zhenghua.zhang@weifu.com.cn?subject=关于溯源系统2017-10-11更新的疑问"><i class="icon-envelope"></i>&nbsp;张正华</a></span>
    </div>
    <div class="panel-body">
        <div class="body-title">
            功能优化
        </div>
        <div class="body-content">
            <span class="content-line"><i class="del"></i>入库/出库查询新增根据“生产流水号”关键字查询功能</span>
            <span class="content-line"><i class="del"></i>企业管理用户新增查看更新日志功能</span>
        </div>

        <div class="body-title">
            问题修复
        </div>
        <div class="body-content">
            <i class="del"></i>暂无
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function() {});
</script>
</html>
