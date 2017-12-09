<%--
  Created by IntelliJ IDEA.
  User: zqh
  Date: 2017/12/8
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8"  import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>list order</title>
</head>
<body>
<h1 align="center">购物车</h1>
<table border="1" align="center" cellspacing="0">
    <tr>
        <td>商品名称</td>
        <td>数量</td>
        <td>价格</td>
        <td>小计</td>
        <td>操作</td>
    </tr>

    <c:forEach items="${orderItemList}" var="orders" varStatus="st">
        <tr>
            <td>${orders.product.name}</td>
            <td>${orders.product.price}</td>
            <td>${orders.num}</td>
            <td>
                <fmt:formatNumber type="number" value="${orders.product.price*orders.num}" maxFractionDigits="2"/>
            </td>
            <td align="center" valign="center">
                <!--
                <form action="deleteOrderItem" method="post">
                <input type="hidden" name="pid_delete" value="${orders.product.id}">
                    <input type="hidden" name="num_delete" value="${orders.num}">
                    <input type="submit" value="删除">
                </form>
                -->
                <!--直接用href + 超链接的方式更简便-->
                <a  style="text-decoration: none" href="deleteOrderItem?pid=${orders.product.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${!empty orderItemList}">
        <tr>
            <td colspan="5" align="center">
                <a style="text-decoration: none" href="createOrderServlet">生成订单</a>
            </td>
        </tr>
    </c:if>
</table>
</body>
</html>
