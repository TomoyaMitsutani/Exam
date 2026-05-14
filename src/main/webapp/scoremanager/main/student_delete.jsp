<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">

        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報削除</h2>
        <br>

        <form action="StudentDeleteExecute.action" method="post">

			<span>「${student.name}(${student.no})」を削除してもよろしいですか</span>			
			
            <!-- 科目コード：データ送信用 -->
			<input type="hidden" name="no" value="${student.no}">
            
            <!-- 送信ボタン -->
            <div class="mt-3">
                <button type="submit" class="btn btn-danger">削除</button>
            </div>

        </form>

        <div class="my-3">
            <a href="StudentList.action">戻る</a>
        </div>

    </c:param>
</c:import>