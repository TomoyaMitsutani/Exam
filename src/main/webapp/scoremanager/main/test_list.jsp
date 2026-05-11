<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
			
			<%-- 科目検索フォーム --%>
			<form action="TestListSubjectExecute.action" method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
				
					<div class="col-4">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					
					<div class="col-4">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					
					<div class="col-4">
						<label class="form-label" for="student-f3-select">科目</label>
						<select class="form-select" id="student-f3-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="sub" items="${subject_set}">
								<%-- 現在のsubと選択されていたf3が一致していた場合selectedを追記 --%>
								<option value="${sub.cd}" <c:if test="${sub.cd==f3}">selected</c:if>>${sub.name}</option>
							</c:forEach>
						</select>
					</div>
					
					<div class="col-2 text-center">
						<button type="submit" class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1")}</div>
				</div>
			</form>
			
			<%-- 学生番号検索フォーム --%>
			<form action="TestListStudentExecute.action" method="get" method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
				
					<div class="col-4">
						<label class="form-label" for="student-f4-input">学生番号</label>
						<input type="text" class="form-control" id="student-f1-input" name="f4" value="${f4}">
					</div>
					
					<div class="col-2 text-center">
						<button type="submit" class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1")}</div>
					
				</div>
			</form>
			
			<c:choose>
			
				<%-- 初期表示：リストが null の場合 --%>
				<c:when test="${testListSubject == null && testListStudent == null}">
					<div class="text-info">
					    科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
					</div>
				</c:when>
	
				<%-- 科目検索結果のリストがある場合 --%>
				<c:when test="${testListSubject.size()>0}">
				<div>検索結果：${testListSubject.size()}件</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>１回</th>
							<th>２回</th>
						</tr>
						<c:forEach var="test" items="${testListSubject}">
							<tr>
								<td>${test.entYear}</td>
								<td>${test.classNum}</td>
								<td>${test.studentNo}</td>
								<td>${test.studentName}</td>
								<td>${test.getPoint(1)}</td>
								<td>${test.getPoint(2)}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
								
				<%-- 学生番号検索結果のリストがある場合 --%>
				<c:when test="${testListStudent.size() > 0}">
					<div>検索結果：${testListStudent.size()}件</div>
					<table class="table table-hover">
						<tr>
							<th>科目名</th>
							<th>科目コード</th>
							<th>回数</th>
							<th>点数</th>
						</tr>
						<c:forEach var="test" items="${testListStudent}">
							<tr>
								<td>${test.subjectName}</td>
								<td>${test.subjectCd}</td>
								<td>${test.num}</td>
								<td>${test.point}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				
				<%-- 検索結果が存在しなかった場合 --%>
				<c:otherwise>
					<div>学生情報が存在しませんでした</div>
				</c:otherwise>
			
			</c:choose>
		
		</section>
	</c:param>
</c:import>