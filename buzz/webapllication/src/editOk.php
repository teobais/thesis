
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
	
	
	$queryString = "delete from `movie` where `id`='".$_POST['id']."';";
	executeQuery($queryString,$host,$user,$pass,$database);

	
	$queryString = "Insert into `movie`( ";
	$queryString .= "`id`,";
    $queryString .= "`title`,";
    $queryString .= "`year`,";
    $queryString .= "`rated`,";
    $queryString .= "`released`,";
    $queryString .= "`genre`,";
    $queryString .= "`director`,";
    $queryString .= "`writer`,";
    $queryString .= "`actors`,";
    $queryString .= "`plot`,";
    $queryString .= "`poster`,";
    $queryString .= "`runtime`,";
    $queryString .= "`rating`,";
    $queryString .= "`votes`,";
    $queryString .= "`imdb`,";
    $queryString .= "`tstamp`) values ('";
	$queryString .= $_POST['id']."','";
    $queryString .= $_POST['title']."','";
    $queryString .= $_POST['year']."','";
    $queryString .= $_POST['rated']."','";
    $queryString .= $_POST['released']."','";
    $queryString .= $_POST['genre']."','";
    $queryString .= $_POST['director']."','";
    $queryString .= $_POST['writer']."','";
    $queryString .= $_POST['actors']."','";
    $queryString .= $_POST['plot']."','";
    $queryString .= $_POST['poster']."','";
    $queryString .= $_POST['runtime']."','";
    $queryString .= $_POST['rating']."','";
    $queryString .= $_POST['votes']."','";
    $queryString .= $_POST['imdb']."','";
    $queryString .= $_POST['tstamp']."')";
	
	executeQuery($queryString,$host,$user,$pass,$database);
	
?>

	<script type="text/javascript">
		alert("Movie details edited!");
		window.location = "editMovies0.php";
	</script>

	
</body>
</html>