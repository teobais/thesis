
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
if (!isset($_SESSION['user'])){
?>
<script type="text/javascript">
window.location = "index.php";
</script>
<?php
}
?>
<?php
	
	$host = "mysql15.000webhost.com";
	$user = "a9747610_0";
	$pass = "a9747610_0";
	$database = "a9747610_0";
	
	function executeQuery($sql,$l,$u,$p,$d){
		$r = mysql_connect($l,$u,$p);
		mysql_select_db($d,$r);
		mysql_query($sql,$r);
		mysql_close($r);
	}
	
	
	

	
	$queryString = "delete from `movie` where `id` = '".$_POST['id']."';";
	echo $queryString;
	executeQuery($queryString,$host,$user,$pass,$database);
	
?>

	<script type="text/javascript">
		alert("Movie Deleted!");
		window.location = "deleteMovies0.php";
	</script>

	
</body>
</html>