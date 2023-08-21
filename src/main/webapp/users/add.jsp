<%--
  Created by IntelliJ IDEA.
  User: Michał
  Date: 15.08.2023
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header.jsp" %>
<!-- Begin Page Content -->
<div class="container-fluid">
    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
        <a href='<c:url value="/user/list"/>' class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
                class="fas fa-download fa-sm text-white-50"></i> Lista użytkowników</a>
    </div>
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Dodaj użytkownika</h6>
        </div>
        <div class="card-body">
            <form action="/users/add" class="user" method="post">
                <div class="form-group">
                    <label for="inputName">Nazwa</label>
                    <input type="text" name="name" class="form-control form-control-user" id="inputName"
                           placeholder="Nazwa użytwkonika">
                </div>
                <div class="form-group">
                    <label for="inputEmail">E-mail</label>
                    <input type="email" name="email" class="form-control form-control-user" id="inputEmail"
                           placeholder="E-mail użytkownika">
                </div>
                <div class="form-group">
                    <label for="inputPassword">Hasło</label>
                    <input type="password" name="password" class="form-control form-control-user" id="inputPassword"
                           placeholder="Hasło użytwkonika">
                </div>
                <input type="submit" value="Zapisz">
            </form>
        </div>
    </div>
</div>

<!-- /.container-fluid -->
<%@ include file="/footer.jsp" %>