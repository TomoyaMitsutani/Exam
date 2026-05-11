<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">

        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
        <br>

        <form action="SubjectDeleteExecute.action" method="post">

            <!-- 科目コード（変更不可） -->
            <div class="mb-2">
                <label>科目コード</label>
                <input type="text" class="form-control border-0 bg-transparent" name="cd" value="${subject.cd}" readonly>
            </div>

            <!-- 科目名（変更不可） -->
            <div class="mb-3">
                <label>科目名</label>
                <input type="text" class="form-control border-0 bg-transparent" name="name" value="${subject.name}" readonly>
            </div>

            <!-- 送信ボタン -->
            <div class="mb-1">
                <button type="submit" class="btn btn-danger">削除</button>
            </div>

        </form>

        <div class="my-2">
            <a href="SubjectList.action">戻る</a>
        </div>

    </c:param>
</c:import>