<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Registration</title>
  <style type="text/css">
    .container {
      display: flex;
      width: 40%;
      height: 30%;
      margin: auto;
      justify-content: center;
      flex-direction: column;
      align-content: center;
      align-items: flex-start;
      flex-wrap: wrap;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>Registration</h2>
  <form class="form-1" method="post">
    <h3 style="color: red">${requestScope.errorMessage}</h3>
    <p class="login">
      <input type="text" name="login" placeholder="Login">
      <i class="icon-user icon-large"></i>
    </p>
    <p class="password">
      <label>
        <input type="password" name="password" placeholder="Password">
      </label>
      <i class="icon-lock icon-large"></i>
    </p>
    <p class="email">
      <input type="text" name="email" placeholder="Email">
      <i class="icon-lock icon-large"></i>
    </p>
    <p class="submit">
      <button type="submit" name="submit"><i class="icon-arrow-right icon-large"></i>Register</button>
    </p>
  </form>
</div>
</body>
</html>