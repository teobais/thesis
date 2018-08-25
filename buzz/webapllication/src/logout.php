<?php
session_start();
?>
<html>
<head>
<style>
	body {
		font-family : verdana, tahoma, arial;
		font-size : 12px;
		color : #333;
		background-color : #ddf;
		td {
			border : 1px solid #444;
			background-color : #eef;
			color : #444;
		}
		th {
			border : 1px solid #444;
			background-color : #444;
			color : #eef;
		}
		input {
			border : 1px solid #444;
			background-color : #fff;
			color : #444;
		}
	}
</style>
</head>
<body>
	<?php
		session_unset();
		session_destroy();
	
			?>
			<script type="text/javascript">
				window.location = "index.php";
			</script>
			
	
	
</body>
</html>