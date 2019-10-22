<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<script type="text/javascript">
	
	
	window.onload = function(){

		$.ajax({
			url : "/admin/board.do",
			method : "post",
			data : {
				"pageno" : "1",
			},
			dataType: "text",
			success:function(data){
				$('#fList').html(data);
			}
		});
	}
	function pagingMove(pageno){
		//alert(pageno);
		$.ajax({
			url : "/admin/board.do",
			method : "post",
			data : {
				"pageno" : pageno,
			},
			dataType: "text",
			success:function(data){
				$('#fList').html(data);
			}
		});
	}
	
	function ftpDownload(seq, sendDt, status, filename){
		/* alert("seq" + seq +",\nsendDt : " + "," + sendDt + "\nstatus : " + status + "\nfilename : " + filename); */
		
		if(status == "P"){
			alert("파일 전송이 진행중입니다.\n 완료된 후에 다운로드를 시도해주세요.")
		}else{
			/* alert("else status != P") */
			location.href="/admin/ftpDownload.do?seq="+seq+"&sendDt="+sendDt+"&status="+status+"&filename="+filename;
		}	
	}
	</script>
	
	
	<style>
	/* 페이징 css */
	.page{
		text-align: center;
		width: 50%;
	}	
	.pagination{
		list-style: none;
		display: table;
		padding: 0;
		margin-top:20px;
		margin-left:auto;
		margin-right:auto;
	}
	
	.pagination li {
		display: inline;
		text-align: center;
	}
	
	/* 숫자들에 대한 스타일 지정 */
	.pagination a {
		float: left;
		display: block;
		font-size: 14px;
		text-decoration: none;
		padding: 5px 12px;
		color: #96a0ad;
		line-height:1.5;
	}
	.first{
		margin-right: 15px;
	}
	.last{
		margin-left: 15px;
	}
	.first:hover, .last:hover, .left:hover, .right:hover{
		color: #2e9cdf;
	}
	.pagination a.active{
		cursor: default;
		color:#ffffff;
	}
	.pagination a:active{
		outline: none;
	}
	.modal2 .num{
		margin-left: 3px;
		padding: 0;
		width: 30px;
		height: 30px;
		line-height: 30px;
		-moz-border-radius: 100%;
		-webkit-border-radius: 100%;
		border-radius: 100%;
	}
	.modal2 .num:hover{
		background-color: #2e9cdf;
		color:#ffffff;
	}

	.modal2 .num.active, .modal2 .num:active{
		background-color: #2e9cdf;
		cursor: pointer;
	}	
	
	.arrow-left{
		width:0;
		height: 0;
		border-top: 10px solid transparent;
		border-bottom: 10px solid transparent;
		border-right:10px solid blue;
		
	}
	
	</style>