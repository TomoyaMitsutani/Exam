<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
	
		<!-- 更新情報入力画面 -->

		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報更新</h2>	
		<br>
		
		<!-- 学生情報入力用フォーム -->
		<form action="StudentUpdateExecute.action" method="post">
	
		    <!-- 入学年度（変更不可） -->
		    <div class="mb-2">
		        <label>入学年度</label>
				<input type="text" class="form-control border-0 bg-transparent" name="entYear" value="${student.entYear}" readonly>
		    </div>
		
		    <!-- 学生番号（変更不可） -->
		    <div class="mb-2">
		        <label>学生番号</label>
				<input type="text" class="form-control border-0 bg-transparent" name="no" value="${student.no}" readonly>
		    </div>
		
		    <!-- 氏名 -->
		    <div class="mb-3">
		        <label>氏名</label>
		        <input class="form-control" type="text" name="name"
		               value="${student.name}" maxlength="10" required>
		    </div>
		
		    <!-- クラス番号 -->
		    <div class="mb-3">
		        <label>クラス番号</label>
		        <select class="form-select" id="student-classNum-select" name="classNum">
		            <c:forEach var="num" items="${class_num_set}">
		                <option value="${num}"
		                    <c:if test="${num == student.classNum}">selected</c:if>>
		                    ${num}
		                </option>
		            </c:forEach>
		        </select>
		    </div>
		
		    <!-- 在学中 -->
			<div class="form-check d-flex align-items-center ps-0 gap-4 mb-3">
			    <label class="form-check-label me-2" for="attend">
					在学中
			    </label>
			
			    <input class="form-check-input" type="checkbox"
			           id="attend" name="isAttend" value="t"<c:if test="${student.attend}">checked</c:if>>
			</div>
		
		    <!-- 送信ボタン -->
			<div class="mb-1">
				<button type="submit" class="btn btn-primary" id="filter-button">変更</button>
			</div>
		
		</form>
		
		<div class="my-2">
			<a href="StudentList.action">戻る</a>
		</div>
	
	</c:param>

</c:import>