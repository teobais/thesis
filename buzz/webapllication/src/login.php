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
	}
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
			width : 250px;
		}
		input.button {
			border : 1px solid #444;
			background-color : #559;
			color : #f4f4f4;
			width : 100px;
		}
</style>
</head>
<body>
	<?php
		if (!isset($_POST['username'])){
	?>
	<form name="login" method="post" action="">
	Username
	<input type="text" name="username" id="username">
	Password
	<input type="text" name="password" id="password">
	<input class="button" type="submit" value="Login" >
	</form>
	<?php
		}
		else {
			if (($_POST['username']=="admin") && ($_POST['password']=="password")){
				$_SESSION['user'] = "administrator";
				?>
				<script type="text/javascript">
					alert("Successfull Login!!!");
				</script>
				<?php
			}
			else {
				?>
				<script type="text/javascript">
					alert("Login Error!!!");
				</script>
				<?php
			}
			?>
			<script type="text/javascript">
				window.location = "index.php";
			</script>
			<?php
		}
	?>
	
	
</body>
</html>