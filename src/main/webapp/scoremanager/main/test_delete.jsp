<%-- 成績削除確認JSP --%>
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
				成績管理
			</h2>

			<div class="border rounded mx-3 p-4">

				<div class="mb-4">
					以下の成績情報を削除します。よろしいですか？
				</div>

				<table class="table table-bordered">

					<tr>
						<th style="width: 20%;">入学年度</th>
						<td>${test.student.entYear}</td>
					</tr>

					<tr>
						<th>クラス</th>
						<td>${test.classNum}</td>
					</tr>

					<tr>
						<th>学生番号</th>
						<td>${test.student.no}</td>
					</tr>

					<tr>
						<th>氏名</th>
						<td>${test.student.name}</td>
					</tr>

					<tr>
						<th>科目</th>
						<td>${test.subject.name}</td>
					</tr>

					<tr>
						<th>回数</th>
						<td>${test.no}回</td>
					</tr>

					<tr>
						<th>点数</th>
						<td>${test.point}</td>
					</tr>

				</table>

				<form action="TestDeleteExecute.action" method="post">

					<!-- 削除対象情報 -->
					<input type="hidden" name="studentNo" value="${test.student.no}">
					<input type="hidden" name="subjectCd" value="${test.subject.cd}">
					<input type="hidden" name="num" value="${test.no}">

					<!-- 戻り用検索条件 -->
					<input type="hidden" name="entYear" value="${entYear}">
					<input type="hidden" name="classNum" value="${classNum}">
					<input type="hidden" name="subjectCdSearch" value="${subjectCd}">
					<input type="hidden" name="numSearch" value="${num}">

					<div class="mt-4 d-flex gap-2">

						<button type="submit" class="btn btn-danger">
							削除
						</button>

						<a href="TestList.action?entYear=${entYear}&classNum=${classNum}&subjectCd=${subjectCd}&num=${num}&search=true"
						   class="btn btn-secondary">
							キャンセル
						</a>

					</div>

				</form>

			</div>

		</section>

	</c:param>

</c:import>