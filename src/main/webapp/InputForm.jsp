<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>전화번호부 관리</title>
</head>
<body>
    <!-- 전화번호 입력/수정 폼 -->
    <c:choose>
        <c:when test="${not empty phonebook.id}">
            <h3>전화번호 수정</h3>
            <form action="${pageContext.request.contextPath}/phonebook/update" method="post">
                <input type="hidden" name="id" value="${phonebook.id}">
                <label for="name">이름:</label>
                <input type="text" id="name" name="name" value="${phonebook.name}"><br><br>
                <label for="hp">전화번호:</label>
                <input type="text" id="hp" name="hp" value="${phonebook.hp}"><br><br>
                <label for="memo">메모:</label>
                <input type="text" id="memo" name="memo" value="${phonebook.memo}"><br><br>
                <button type="submit">수정</button>
            </form>
            <form action="${pageContext.request.contextPath}/phonebook/delete/${phonebook.id}" method="post">
                <button type="submit">삭제</button>
            </form>
        </c:when>
        <c:otherwise>
            <h3>전화번호 입력</h3>
            <form action="${pageContext.request.contextPath}/phonebook/save" method="post">
                <input type="hidden" name="id" value="${phonebook.id}"> <!-- ID 값은 입력되지 않음 -->
                <label for="name">이름:</label>
                <input type="text" id="name" name="name" value="${phonebook.name}"><br><br>
                <label for="hp">전화번호:</label>
                <input type="text" id="hp" name="hp" value="${phonebook.hp}"><br><br>
                <label for="memo">메모:</label>
                <input type="text" id="memo" name="memo" value="${phonebook.memo}"><br><br>
                <button type="submit">저장</button>
            </form>
        </c:otherwise>
    </c:choose>

    <hr>

    <!-- 검색 기능 -->
    <form action="${pageContext.request.contextPath}/phonebook/list" method="get">
        <label for="search">검색:</label>
        <input type="text" id="search" name="search" value="${param.search}">
        <button type="submit">검색</button>
    </form>

    <hr>

    <!-- 전체 출력 기능 및 검색 결과 -->
    <h3>전화번호 목록</h3>
    <ul>
        <c:forEach var="phonebook" items="${phonebooks}">
            <li>
                <a href="${pageContext.request.contextPath}/phonebook/edit/${phonebook.id}">
                    ${phonebook.name} - ${phonebook.hp}
                </a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
