<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/easyUI/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/easyUI/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/easyUI/themes/demo.css">
    <script type="text/javascript" src="<%=basePath%>/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="<%=basePath%>/easyUI/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/easyUI/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.form.js"></script>
</head>
<body>
</body>
</html>