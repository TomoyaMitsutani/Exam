<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
	
		<!-- 新規登録情報入力画面 -->

		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>	
		<br>
		
		<!-- 学生情報入力用フォーム -->
		<form action="StudentCreateExecute.action" method="post">
	
		    <!-- 入学年度 -->
		    <div class="col-12 mb-3">
		        <label class="form-label" for="student-entYear-select">入学年度</label>
		        <br>
		        <select class="form-select" id="student-entYear-select" name="entYear">
		            <option value="0">--------</option>
		            <c:forEach var="year" items="${ent_year_set}">
		                <option value="${year}" <c:if test="${year == entYear}">selected</c:if>>${year}</option>
		            </c:forEach>
		        </select>

				<!-- 入学年度を入力してください -->
	       	    <c:if test="${errors.entYear != null}">
		       	    <div class="mt-2">
			    		<span style="color:orange;">
			        		${errors.entYear}
			    		</span>
			    	</div>
				</c:if>

		    </div>
			
		    <!-- 学生番号 -->
		    <div class="col-12 mb-3">
		        <label class="form-label" for="student-no-select">学生番号</label>
		        <br>
		        <input type="text" class="form-control" id="student-no-select"
		         type="text" name="no" value="${param.no}" placeholder="学生番号を入力してください" maxlength="10" required>
		         
		         <!-- 学生番号が重複しています -->
	       	    <c:if test="${errors.no != null}">
		       	    <div class="mt-2">
			    		<span style="color:orange;">
			        		${errors.no}
			    		</span>
			    	</div>
				</c:if>

		    </div>

		    <!-- 氏名 -->
		    <div class="col-12 mb-3">
		        <label class="form-label" for="student-name-select">氏名</label>
		        <br>
		        <input type="text" class="form-control" id="student-name-select"
		         type="text" name="name" value="${param.name}" placeholder="氏名を入力してください" maxlength="30" required>
		    </div>
		
		    <!-- クラス番号 -->
		    <div class="col-12 mb-3">
		        <label class="form-label" for="student-classNum-select">クラス番号：</label>
		        <br>
		        <select class="form-select" name="classNum">
		            <c:forEach var="num" items="${class_num_set}">
		                <option value="${num}">${num}</option>
		            </c:forEach>
		        </select>
		    </div>
		    		    
		    <!-- 送信ボタン -->
			<div class="mb-1">
				<button class="btn btn-secondary" id="filter-button" name="end">登録して終了</button>
			</div>
		
		</form>
		
		<div class="my-2">
			<a href="StudentList.action">戻る</a>
		</div>
	
	</c:param>

</c:import>