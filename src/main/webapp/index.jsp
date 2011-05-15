<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cloud image processing server</title>
    </head>
    <body>
        <h1>Testing page</h1>
		<form action="UploadResource" method="POST" enctype="multipart/form-data">
			Choose a file:<input type="file" name="file1"/>
			<input type="submit" value="Submit!" />
		</form>
    </body>
</html>
