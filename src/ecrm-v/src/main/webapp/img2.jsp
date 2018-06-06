<%@ page contentType="image/jpeg" import=" java.util.*" %> 

<% 
response.setHeader("Pragma", "No-cache");  
response.setHeader("Cache-Control", "no-cache");  
response.setDateHeader("Expires", 0);  

int max=4;
int min=1;
Random random = new Random();

int s = random.nextInt(max)%(max-min+1) + min;
//System.out.println("code"+s+".jsp?ss="+System.currentTimeMillis());
response.sendRedirect("code"+s+".jsp?ss="+System.currentTimeMillis());
//request.getRequestDispatcher("code"+s+".jsp?ss="+System.currentTimeMillis()).forward(request, response);
%>