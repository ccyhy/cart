<%--
  Created by IntelliJ IDEA.
  User: zqh
  Date: 2017/12/7
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>商品列表</title>
    <!--jquery文件引入路径出错，导致不能执行jquery-->
    <!--
    <script src="http://cdn.static.runoob.com/libs/jquery/1.10.2/jquery.min.js"></script>
    -->
    <script src="/js/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#addCartSuccessMessage").hide();
            $("input.addCartButton").removeAttr("disabled");
            $("input.addCartButton").click(function () {
                $(this).attr("disabled","disabled");
                var button=$(this);
                var pid=$(this).attr("pid");
                //返回number类input的pid属性的值
                var number=$("input.number[pid="+pid+"]").val();
                //使用get请求方法
                //设置请求的域名
                var page="/OrderItemAddServlet";
                //请求
                $.get(
                    page,
                    //请求参数
                    {"num":number,"pid":pid},
                    //回调函数，result代表包含来自请求的结果数据
                    function (result) {
                        $("#addCartSuccessMessage").fadeIn(800);
                        $("#addCartSuccessMessage").fadeOut(800,function () {
                            button.removeAttr("disabled");
                        });
                    }
                );
            });
        });
    </script>
</head>
<body>
<c:if test="${!empty user}">
    <div align="center">
        <!--el表达式取出当前user对象中的name属性值-->
        当前用户:${user.name}
    </div>
</c:if>
<div align="center" style="height: 20px; margin: 20px">
    <span style="color:red" id="addCartSuccessMessage">加入购物车成功</span>
</div>
<table align="center" border="1" cellspacing="0">
    <tr>
        <td>id</td>
        <td>名称</td>
        <td>价格</td>
        <td>购买</td>
    </tr>
    <c:forEach items="${products}" var="product" varStatus="st">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>
                <fmt:formatNumber type="number" value="${product.price}" maxFractionDigits="2"/>
            </td>
            <td>
                <!--
                <form action="OrderItemAddServlet" method="post">
                    数量<input type="text" value="1" name="num">
                    <input type="hidden" name="pid" value="${product.id}">
                    <input type="submit" value="购买">
                </form>
                -->
                数量<input pid="${product.id}" class="number" type="text" value="1" name="num">
                <input class="addCartButton" pid="${product.id}" type="submit" value="加入购物车">
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="4" align="center"><a href="/ListOrderItemServlet">查看购物车</a> </td>
    </tr>
</table>
</body>
</html>
