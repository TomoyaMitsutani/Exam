<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
	
		<!-- 登録完了画面 -->
	
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
		
		<div id="wrap_box">
			<p class="text-center py-1" style="background-color:#8CC3A9">変更が完了しました</p>
		</div>
		
		<div style="margin-top: 140px; margin-left: 20px;">
		    <a href="StudentList.action">学生一覧</a>
		</div>

	</c:param>

</c:import>