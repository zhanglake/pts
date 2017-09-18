<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="actionListUrl" value="actionList.do"/>

<c:set var="tit" value="威孚集团 | PTS"/>

<%--获取绝对路径--%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
