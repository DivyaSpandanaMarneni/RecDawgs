<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<form name="form1" action="AssignScores" method="post">
  
<h1>Enter the scores</h1>

<table width="70%"  border="0" cellpadding="1" cellspacing="2" >
  <#list teams as team>
  <tr>
  Enter Score for:
   <td valign="top"><b><font size="+1"><p>${team[1]}(Home Team)</p></font></font></b></td>
         
    <td><input name="HomeScores" type="text"> <br>
          </td>
          
  
  <td valign="top"><b><font size="+1"><p>${team[2]}(Away Team)</p></font></font></b></td>
          
    <td><input name="AwayScores" type="text"> <br>
          </td>
          
          </td>
      
       
          <td valign="top"><input name="Submit" value="Submit" type="submit">
          
          </td>
      
   
         
   </tr>
    
    
 
 
 </#list></table>
  
<p><p>Back to the <a href="ShowMainWindow"> main window</a>
  
</body>
</html>
