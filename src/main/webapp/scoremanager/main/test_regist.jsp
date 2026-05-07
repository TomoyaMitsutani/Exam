<%-- 成績管理JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		
		<section class="me-4">
		
			<h2 class="h3 mb-4 bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			
			<form method="get">
			
				<div class="row border mx-3 mb-3 py-2 align-items-end rounded g-3" id="filter">

					<!-- 入学年度 -->
					<div class="col-2">
						<label class="form-label">入学年度</label>
						<select class="form-select" name="entYear">
							<option value="">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}" <c:if test="${year == entYear}">selected</c:if>>
									${year}
								</option>
							</c:forEach>
						</select>
					</div>
		
					<!-- クラス -->
					<div class="col-2">
						<label class="form-label">クラス</label>
						<select class="form-select" name="classNum">
							<option value="">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num == classNum}">selected</c:if>>
									${num}
								</option>
							</c:forEach>
						</select>
					</div>
				
					<!-- 科目 -->
					<div class="col-3">
						<label class="form-label">科目</label>
						<select class="form-select" name="subjectCd">
							<option value="">--------</option>
							<c:forEach var="sub" items="${subject_list}">
								<option value="${sub.cd}" <c:if test="${sub.cd == subjectCd}">selected</c:if>>
									${sub.name}
								</option>
							</c:forEach>
						</select>
					</div>
				
					<!-- 回数 -->
					<div class="col-2">
						<label class="form-label">回数</label>
						<select class="form-select" name="num">
							<option value="">--</option>
							<c:forEach var="i" begin="1" end="2">
								<option value="${i}" <c:if test="${i == num}">selected</c:if>>
									${i}
								</option>
							</c:forEach>
						</select>
					</div>
		
					<!-- ボタン -->
					<div class="col-3 text-center">
						<button class="btn btn-secondary mt-4" name="search" value="true">検索</button>
					</div>					
					
					<c:if test="${errors.search != null}">
						<div class="mt-2">
				    		<span style="color:orange;">
								${errors.search}
							</span>
						</div>
					</c:if>					
				
				</div>
			
			</form>
		
			<c:if test="${not empty testList}">

				<div>科目：${testList[0].subject.name}（${num}回）</div>
			
				<form action="TestRegistExecute.action" method="post">
			
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>点数</th>
						</tr>
			
					<c:forEach var="test" items="${testList}" varStatus="st">
					
						<tr>
							<td>${test.student.entYear}</td>
							<td>${test.classNum}</td>
							<td>${test.student.no}</td>
							<td>${test.student.name}</td>
					
							<td>
								<input type="hidden"
									   name="studentNo"
									   value="${test.student.no}">
					
								<input type="number"
									   name="point"
									   value="${test.point}">
								
								<c:if test="${not empty pointErrors[st.index]}">
									<div style="color:orange;">
										${pointErrors[st.index]}
									</div>
								</c:if>
								
								
							</td>
						</tr>
					
					</c:forEach>
			
					</table>
			
					<!-- 検索条件も送る -->
					<input type="hidden" name="entYear" value="${entYear}">
					<input type="hidden" name="classNum" value="${classNum}">
					<input type="hidden" name="subjectCd" value="${subjectCd}">
					<input type="hidden" name="num" value="${num}">
			
					<div class="mt-4">
						<button class="btn btn-secondary">登録して終了</button>
					</div>
			
				</form>
			
			</c:if>
		
		</section>
	</c:param>
</c:import>