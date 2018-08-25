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
	<hr>
        <?php
            if (isset($_SESSION['user'])){
        ?>
            <a href="insertMovies.php">Insert Movie</a>|
            <a href="editMovies0.php">Edit Movie</a>|
            <a href="deleteMovies0.php">Delete Movie</a>|
            <a href="makeSQL.php">Make SQL file</a> | 
            <a href="logout.php">Logout</a>
        <?php
            }
            else {
        ?>
            <a href="login.php">Login</a>
        <?php
            }
		?>
	<hr>
	
</body>
</html>