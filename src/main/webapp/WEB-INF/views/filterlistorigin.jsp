<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
   
   body{
      
   }
   
   .callimg{
      position:relative;
   }
   
   .txt{
      width:1280px;
      margin: 0 auto;
   }
   
   .callimg .lefttxt{
      text-align:left;
      color:#fff;
      float: left;
      
      position:absolute;
      top:0;
      
      line-height:120px;
   }
   
   .wrap{
      width:1280px;
      margin: 0 auto;
   }
   
   .left{
      float:left;
      width:240px;
      margin-right:10px;
      height:900px;
      margin-top: 30px;
      text-align: left;
      padding: 15px;
      border: 1.5px solid #ddd;
   }
   
   .left h3{
      padding-bottom: 5px;
      width: 100%;
   }
   
   .right{
      width:997px;
      height:640px;
      float: right;
      margin-top: 30px;
   }
   
   .right table {
      width: 1000px;
   }
   
   ul.main{
      width: 100%;
      margin-top: 10px;
      
   }
   
   ul.main>li{
      width: 228px;
      height: 40px;
      line-height: 40px;
      border: 1.5px solid #eee;
      padding-left: 10px;
      font-weight: bold;
      display:inline-block;
      
   }
   
   ul.main>li:hover{
      background-color:#eee;
   }
   
   ul.main>li>ul.sub{
      display: none;

   }
   
   ul.main>li>ul.sub>li{
      width:228px;
      height:40px;
      line-height: 40px;
      z-index: 1;
      overflow:visible;
      font-size:14px;
      border-bottom:1px solid #eee;
      
      display: flex;
   }
   
   ul.main>li>ul.sub>li:last-of-type {
      border:none;
   }
   
   ul.main>li>ul.sub>li input{
      margin-right:8px;
   }
   
   /* ul.main>li>ul.sub>li:hover{
      background-color:#eee;
   } */
   
   .see{
      width:980px;
      height:120px;
      border: 1.5px solid #ddd;
      margin-bottom:10px;
      padding:10px;
   }
   
   
   .ll{
      float:left;
      width:220px;
      background-color: #eee;
      height:120px;
   }
   
   .ll img{
      margin-top:12px;
   }
   
   .rr{
   
      float:right;
      width:750px;
      text-align: left;
   }
   
   .rr h3{
      margin-bottom:15px;
   }
   
   .hide{
      display: none;
   }
   .price{
      float:right;
      text-align:right;
      margin-top: 25px;
   }
   
   .rr h5{
      font-weight: normal;
      color:green;
      margin-top: 5px;
   }
   
   .tt{
      width:220px;
      height:20px;
      margin:10px 0;
   }
   
   .fbtn{
      padding: 2px;
      margin-right: 5px;
      margin-top: 10px;
   }
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">

/* function cc(){
    let click = document.getElementById("sub");
    if(click.style.display === "none"){
        click.style.display = "block";

    }else{
        click.style.display = "none";

    }
} */
   
   
   /* $(document).ready(function() {
       $("ul.main>li").click(function() {
           $(this).children("ul.main>li>ul.sub").stop().slideDown();
       });
       $("ul.main>li").mouseleave(function() {
           $(this).children("ul.main>li>ul.sub").stop().slideUp();
       });
   }); */
   
   /* $(document).ready(function() {
       $('ul.main>li').click(function() {
          $('ul.main>li>ul.sub').show();
       })
   }); */
   
   ??$(document).ready(function(){
      $("ul.main>li>a").click(function(){
         
         var submenu = $(this).next("ul");
         
         if( submenu.is(":visible") ){
            submenu.slideUp();
            }else{submenu.slideDown();}
         });
      });
      
   
   
</script>
</head>
<body>
<div class="callimg">
      <img src="./image/carout1img.jpg" width="1950px;">
      
      <div class="txt">
         <div class="lefttxt">
            <h1>?????????</h2>
         </div>
      </div>
</div>

<div class="wrap">
   <div class="left">
      <h3>????????? ??????</h3>
      
      <form action="filterlist" method="post">
      <ul class="main">
         <li><a onclick="cc()">?????????</a>
            <ul class="sub" id="sub">
               <li><input type="checkbox" name="carbrand" value="1" >??????</li>
               <li><input type="checkbox" name="carbrand" value="2" >??????</li>
               <li><input type="checkbox" name="carbrand" value="3" >??????</li >
               <li><input type="checkbox" name="carbrand" value="4" >?????????</li>
            </ul>
         </li>
         
         <li><a>??????</a>
            <ul class="sub" id="sub">
               <li><input type="checkbox" name="fueltype">??????</li>
               <li><input type="checkbox" name="fueltype">?????????</li>
            </ul>
         </li>
         <li><a>??????</a>
            <ul class="sub" id="sub">
               <li>
                  <select class="tt" name="cyear">
                     <option value="2022">2022</option>
                     <option value="2021">2021</option>
                     <option value="2020">2020</option>
                     <option value="2019">2019</option>
                     <option value="2018">2018</option>
                     <option value="2017">2017</option>
                     <option value="2016">2016</option>
                     <option value="2015">2015</option>
                     <option value="2014">2014</option>
                     <option value="2013">2013</option>
                     <option value="2012">2012</option>
                    </select>
                 </li>
            </ul>
         </li>
         
         <li><a>??????</a>
            <ul class="sub" id="sub">
               <li><input type="radio" name="color">?????????</li>
               <li><input type="radio" name="color">??????</li>
               <li><input type="radio" name="color">??????</li>
               <li><input type="radio" name="color">?????????</li>
            </ul>
         </li>
         
         <li><a>????????????</a>
            <ul class="sub" id="sub">
               <li><input type="radio" name="km">10,000~30,000km</li>
               <li><input type="radio" name="km">30,000~70,000km</li>
               <li><input type="radio" name="km">70,000~100,000km</li>
            </ul>
         </li>
         
         <li><a>??????</a>
            <ul class="sub" id="sub">
               <li><input type="radio" name="price">100~500??????</li>
               <li><input type="radio" name="price">500~1000??????</li>
               <li><input type="radio" name="price">1000~1500??????</li>
               <li><input type="radio" name="price">1500~2000??????</li>
               <li><input type="radio" name="price">2000~2500??????</li>
               <li><input type="radio" name="price">2500~3000??????</li>
               <li><input type="radio" name="price">3000?????? ??????</li>
            </ul>
         </li>
      </ul>
      
      <input class="fbtn" type="submit" value="??????"><input class="fbtn" type="reset" value="?????????">
   </form>
   </div>
   
   <div class="right">
      <c:forEach items="${dto }" var="list">
      <ul class="see">
         <li class="ll"><img src="./image/${list.picture }" width="200" height="100"></li>
         <li class="rr"><a class="hide"></a></li>
         <li class="rr"><h3><a href="cardetail?carnum=${list.carnum}">${list.carbrand} ${list.carname}</a></h3></li>
         <li class="rr">${list.fueltype} | ${list.origin} | ${list.cyear} | <fmt:formatNumber pattern="#,###" value="${list.km}"/>km</li>
         <li class="rr"><h5>${list.content}</h5></li>
         <li class="price"><strong><fmt:formatNumber pattern="#,###" value="${list.price}"/></strong> ??????</li>
         
      </ul>
      </c:forEach>
      
      <table>
      <!--????????? ?????? 4-->
      <tr style="border-left: none;border-right: none;border-bottom: none">
         <td colspan="5" style="text-align: center;">
            <c:forEach begin="${paging.startPage }" end="${paging.endPage}" var="p"> 
               <c:choose>
                  <c:when test="${p == paging.nowPage }">
                     <b>${p}</b>
                  </c:when>   
                  <c:when test="${p != paging.nowPage }">
                     <a href="carout?nowPage=${p}&cntPerPage=${paging.cntPerPage}">${p}</a>
                  </c:when>   
               </c:choose>
            </c:forEach>
         </td>
      </tr>
      </table>
      <!--??????????????? ???-->
   </div>
</div>
</body>
</html>