<%-- 成績登録JSP --%>
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

			<h2 class="h3 mb-4 bg-secondary bg-opacity-10 py-2 px-4">
				成績登録
			</h2>

			<p class="mx-3">新規登録を行う成績情報を入力してください</p>

			<form action="TestCreateExecute.action" method="post">

				<div class="row border mx-3 px-2 mb-3 py-3 rounded gx-3 gy-2">

				<!-- 学生番号 -->
				<div class="col-3">
					<label class="form-label">
						学生番号
					</label>
				
					<select class="form-select" name="studentNo" required>
				
						<option value="">
							--------
						</option>
				
						<c:forEach var="student" items="${student_list}">
				
							<option value="${student.no}"
								<c:if test="${student.no == studentNo}">
									selected
								</c:if>>
				
								${student.no}
				
							</option>
				
						</c:forEach>
				
					</select>
				</div>
				
				<!-- 科目 -->
				<div class="col-3">
					<label class="form-label">
						科目
					</label>
				
					<select class="form-select" name="subjectCd" required>
				
						<option value="">
							--------
						</option>
				
						<c:forEach var="subject" items="${subject_list}">
				
							<option value="${subject.cd}"
								<c:if test="${subject.cd == subjectCd}">
									selected
								</c:if>>
								${subject.name}
							</option>
				
						</c:forEach>
				
					</select>
				</div>
				
				<!-- 回数 -->
				<div class="col-2">
				
					<label class="form-label">
						回数
					</label>
				
					<select class="form-select" name="num" required>
				
						<option value="">
							--
						</option>
				
						<c:forEach var="i" begin="1" end="2">
				
							<option value="${i}"
								<c:if test="${i == num}">
									selected
								</c:if>>
				
								${i}
				
							</option>
				
						</c:forEach>
				
					</select>
				</div>
				
				<!-- 得点 -->
				<div class="col-2">
					<label class="form-label">
						得点
					</label>
					<input type="number" class="form-control" name="point"
						   value="${point}"  min="0" max="100" required>
				</div>

					<!-- 登録ボタン -->
					<div class="col-2 text-center align-self-end">

						<button type="submit" class="btn btn-secondary">
							登録
						</button>

					</div>

					<!-- エラーメッセージ -->
					<c:if test="${errors != null}">
						<div class="col-12 text-warning">
							${errors.create}
						</div>
					</c:if>

				</div>

			</form>

		</section>
		
		<div class="my-2">
			<a href="TestRegist.action">戻る</a>
		</div>

	</c:param>

</c:import>