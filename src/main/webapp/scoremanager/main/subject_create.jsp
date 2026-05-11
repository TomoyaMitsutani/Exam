<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">

        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
        <br>

        <form action="SubjectCreateExecute.action" method="post">

            <!-- 科目コード -->
            <div class="col-12 mb-3">
                <label class="form-label" for="subject-cd-input">科目コード</label>
                <br>
                <input type="text" class="form-control" id="subject-cd-input"
                    name="cd" value="${param.cd}" placeholder="科目コードを入力してください" maxlength="10" required>

                <c:if test="${errors.cd != null}">
                    <div class="mt-2">
                        <span style="color:orange;">${errors.cd}</span>
                    </div>
                </c:if>
            </div>

            <!-- 科目名 -->
            <div class="col-12 mb-3">
                <label class="form-label" for="subject-name-input">科目名</label>
                <br>
                <input type="text" class="form-control" id="subject-name-input"
                    name="name" value="${param.name}" placeholder="科目名を入力してください" maxlength="30" required>

                <c:if test="${errors.name != null}">
                    <div class="mt-2">
                        <span style="color:orange;">${errors.name}</span>
                    </div>
                </c:if>
            </div>

            <!-- 送信ボタン -->
            <div class="mb-1">
                <button class="btn btn-secondary" id="filter-button">登録して終了</button>
            </div>

        </form>

        <div class="my-2">
            <a href="SubjectList.action">戻る</a>
        </div>

    </c:param>
</c:import>