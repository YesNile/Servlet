<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>folder</title>
    <link rel="stylesheet" href="https://unpkg.com/tachyons@4.12.0/css/tachyons.min.css"/>
    <style type="text/css">
        html {
            font-family: "sans-serif", serif;
            font-size: 16px;
            color: black;
        }

        li {
            list-style-type: none;
        }

        .container {
            max-width: 50%;
            float: left;
            display: flex;
            flex-direction: column;
        }

        .row {
            display: flex;
        }

        .column {
            margin-left: 20px;
        }

        .item{
            height: 30px;
        }

        .item::after{
            content: "";
            position: absolute;
            display: block;
            height: 1px;
            width: max-content;

        }

        .up-btn {
            margin: 20px 20px;
            height: max-content;
        }

        .icon {
            width: 1em;
            height: 1em;
        }

        .flex-row .pa2:not(:first-child) {
            margin-top: 0;
            margin-left: 8px;
        }

        .ma0 {
            width: max-content;
        }

        ul .inline-flex {
            margin-top: 8px;
        }

        a {
            text-decoration: none;
            color: black;
        }
    </style>
</head>
<body>
<h4>${date}</h4>
<h1>${path}</h1>
<hr>
<div class="container">
    <div class="up-btn">
        <i data-feather="arrow-up-circle" class="icon"></i>
        <a href="?path=${requestScope.path.getParent().toFile().getAbsolutePath().substring(0,requestScope.path.getParent().toFile().getAbsolutePath().lastIndexOf("\\")).replace('\\', '/')}/">Наверх/</a><br>
    </div>
    <div class="row">
        <div class="column">
            <span class="b">Директория</span>
            <ul class="flex-column flex ma0 pa0">
                <c:forEach var="directory" items="${requestScope.directories}">
                    <div class="item">
                        <i data-feather="folder" class="red icon"></i>
                        <a href="./?path=${directory.file.getAbsolutePath().split("JavaServlet")[1].replace('\\', '/')}"
                           class="ml2">${directory.file.getName()}/</a>
                    </div>
                </c:forEach>
                <c:forEach var="file" items="${requestScope.files}">
                    <div class="item">
                        <i data-feather="file" class="yellow icon"></i>
                        <a href="./?path=${file.file.getAbsolutePath().split("JavaServlet")[1].replace('\\', '/')}"
                           class="ml2">${file.file.getName()}</a>
                    </div>
                </c:forEach>
            </ul>
        </div>
        <div class="column">
            <span class="b">Размер (в байтах)</span>
            <ul class="flex-column flex ma0 pa0">
                <c:forEach var="directory" items="${requestScope.directories}">
                    <li class="item">
                        <span>${directory.length}</span>
                    </li>
                </c:forEach>
                <c:forEach var="file" items="${requestScope.files}">
                    <li class="item">
                        <span>${file.length}</span>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="column">
            <span class="b">Дата изменения</span>
            <ul class="flex-column flex ma0 pa0">
                <c:forEach var="directory" items="${requestScope.directories}">
                    <li class="item">
                        <span>${directory.lastModified}</span>
                    </li>
                </c:forEach>
                <c:forEach var="file" items="${requestScope.files}">
                    <li class="item">
                        <span>${file.lastModified}</span>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

</div>
</body>
</html>