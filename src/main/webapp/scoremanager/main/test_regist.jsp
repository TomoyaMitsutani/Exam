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

			<h2 class="h3 mb-4 bg-secondary bg-opacity-10 py-2 px-4">
				成績登録
			</h2>

			<div class="text-end px-4 my-2">
				<a href="TestCreate.action">
					新規登録
				</a>
			</div>

			<!-- 検索フォーム -->
			<form action="TestRegist.action" method="get">

				<div class="row border mx-3 px-2 mb-3 py-3 align-items-end rounded gx-3 gy-1"
					 id="filter">

					<!-- 入学年度 -->
					<div class="col-2">

						<label class="form-label">
							入学年度
						</label>

						<select class="form-select" name="entYear">

							<option value="">
								--------
							</option>

							<c:forEach var="year" items="${ent_year_set}">

								<option value="${year}"
									<c:if test="${year == entYear}">
										selected
									</c:if>>

									${year}

								</option>

							</c:forEach>

						</select>

					</div>

					<!-- クラス -->
					<div class="col-2">

						<label class="form-label">
							クラス
						</label>

						<select class="form-select" name="classNum">

							<option value="">
								--------
							</option>

							<c:forEach var="numItem" items="${class_num_set}">

								<option value="${numItem}"
									<c:if test="${numItem == classNum}">
										selected
									</c:if>>

									${numItem}

								</option>

							</c:forEach>

						</select>

					</div>

					<!-- 科目 -->
					<div class="col-3">

						<label class="form-label">
							科目
						</label>

						<select class="form-select" name="subjectCd">

							<option value="">
								--------
							</option>

							<c:forEach var="sub" items="${subject_list}">

								<option value="${sub.cd}"
									<c:if test="${sub.cd == subjectCd}">
										selected
									</c:if>>

									${sub.name}

								</option>

							</c:forEach>

						</select>

					</div>

					<!-- 回数 -->
					<div class="col-2">

						<label class="form-label">
							回数
						</label>

						<select class="form-select" name="num">

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

					<!-- 検索ボタン -->
					<div class="col-3 text-center">

						<button class="btn btn-secondary mt-4"
								name="search"
								value="true">

							検索

						</button>

					</div>

					<!-- エラー -->
					<c:if test="${errors.search != null}">

						<div class="mt-2 text-warning mt-4">

							${errors.search}

						</div>

					</c:if>

				</div>

			</form>

			<!-- 削除成功 -->
			<c:if test="${message_dlt != null}">

				<div class="text-success mt-3">

					${message_dlt}

				</div>

			</c:if>

			<!-- 登録成功 -->
			<c:if test="${message_crt != null}">

				<div class="text-success mt-3">

					${message_crt}

				</div>

			</c:if>

			<!-- 検索結果 -->
			<c:if test="${not empty testList}">

				<div>

					科目：${testList[0].subject.name}（${num}回）

				</div>

				<!-- 点数登録フォーム -->
				<form action="TestRegistExecute.action" method="post">

					<table class="table table-hover">

						<tr>

							<th>
								入学年度
							</th>

							<th>
								クラス
							</th>

							<th>
								学生番号
							</th>

							<th>
								氏名
							</th>

							<th>
								点数
							</th>

							<th></th>

						</tr>

						<c:forEach var="test"
								   items="${testList}"
								   varStatus="st">

							<tr>

								<td>
									${test.student.entYear}
								</td>

								<td>
									${test.classNum}
								</td>

								<td>
									${test.student.no}
								</td>

								<td>
									${test.student.name}
								</td>

								<td>

									<input type="hidden"
										   name="studentNo"
										   value="${test.student.no}">

									<input type="number"
										   name="point"
										   value="${test.point}">

									<c:if test="${not empty pointErrors[st.index]}">

										<div style="color: orange;">

											${pointErrors[st.index]}

										</div>

									</c:if>

								</td>

								<!-- 削除 -->
								<td>

									<form action="TestDelete.action"
										  method="post">

										<input type="hidden"
											   name="studentNo"
											   value="${test.student.no}">

										<input type="hidden"
											   name="subjectCd"
											   value="${subjectCd}">

										<input type="hidden"
											   name="num"
											   value="${num}">

										<input type="hidden"
											   name="entYear"
											   value="${entYear}">

										<input type="hidden"
											   name="classNum"
											   value="${classNum}">

										<button type="submit"
												class="btn btn-danger btn-sm">

											削除

										</button>

									</form>

								</td>

							</tr>

						</c:forEach>

					</table>

					<!-- 検索条件 -->
					<input type="hidden"
						   name="subjectCd"
						   value="${subjectCd}">

					<input type="hidden"
						   name="num"
						   value="${num}">

					<input type="hidden"
						   name="entYear"
						   value="${entYear}">

					<input type="hidden"
						   name="classNum"
						   value="${classNum}">

					<div class="mt-4">

						<button class="btn btn-secondary">

							登録して終了

						</button>

					</div>

				</form>

			</c:if>

		</section>

	</c:param>

</c:import>