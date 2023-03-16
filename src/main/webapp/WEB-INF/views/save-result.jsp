<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
성공
<ul>
  <li>id=${member.id}</li> <%-- 기존 <%= %> 를 안쓰고 ${} jsp 표현식으로 프로퍼티(getter setter)형식으로 가져와짐--%>
  <li>username=${member.username}</li>
  <li>age=${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
