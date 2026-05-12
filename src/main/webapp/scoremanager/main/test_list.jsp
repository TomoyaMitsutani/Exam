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
			
			<%-- 検索フォーム全体 --%>
			<div class="border rounded mx-3 mb-3">
			
				<%-- 科目検索フォーム --%>
				<form action="TestListSubjectExecute.action" method="get" class="m-0">
			
					<div class="row py-2 px-2 align-items-center g-2">
			
						<!-- タイトル -->
						<div class="col-auto px-4">
							<div>科目情報</div>
						</div>
			
						<!-- 入学年度 -->
						<div class="col-2">
							<label class="form-label mb-1" for="student-f1-select">入学年度</label>
							<select class="form-select" id="student-f1-select" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}"
										<c:if test="${year == f1}">selected</c:if>>
										${year}
									</option>
								</c:forEach>
							</select>
						</div>
			
						<!-- クラス -->
						<div class="col-2">
							<label class="form-label mb-1" for="student-f2-select">クラス</label>
							<select class="form-select" id="student-f2-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}"
										<c:if test="${num == f2}">selected</c:if>>
										${num}
									</option>
								</c:forEach>
							</select>
						</div>
			
						<!-- 科目 -->
						<div class="col-4">
							<label class="form-label mb-1" for="student-f3-select">科目</label>
							<select class="form-select" id="student-f3-select" name="f3">
								<option value="0">--------</option>
								<c:forEach var="sub" items="${subject_set}">
									<option value="${sub.cd}"
										<c:if test="${sub.cd == f3}">selected</c:if>>
										${sub.name}
									</option>
								</c:forEach>
							</select>
						</div>
			
						<!-- ボタン -->
						<div class="col-2 text-center">
							<button type="submit" class="btn btn-secondary" id="filter-button">
								検索
							</button>
						</div>
			
						<!-- エラーメッセージ -->
						<div class="col-12 px-4">
							<div class="text-warning">
								${errors.get("error_1")}
							</div>
						</div>
					</div>
				</form>

				<%-- 区切り線 --%>
				<div class="px-3">
					<hr class="my-0">
				</div>

				<%-- 学生番号検索フォーム --%>
				<form action="TestListStudentExecute.action" method="get" class="m-0">
			
					<div class="row py-2 px-2 align-items-center g-2">
			
						<!-- タイトル -->
						<div class="col-auto px-4">
							<div>学生情報</div>
						</div>
			
						<!-- 学生番号 -->
						<div class="col-4">
							<label class="form-label mb-1" for="student-f4-input">学生番号</label>
							<input type="text" class="form-control" id="student-f4-input" name="f4" value="${f4}">
						</div>
			
						<!-- ボタン -->
						<div class="col-2 text-center">
							<button type="submit" class="btn btn-secondary" id="filter-button">
								検索
							</button>
						</div>
			
						<!-- エラーメッセージ -->
						<div class="col-12">
							<div class="text-warning">
								${errors.get("f4")}
							</div>
						</div>
			
					</div>
				</form>

			</div>
			
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