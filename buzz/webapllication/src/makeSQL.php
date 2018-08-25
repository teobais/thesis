
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
		$k = mysql_query($sql,$r);
		mysql_close($r);
		return $k;
	}
	
	
	$queryString = "Select  ";
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
    $queryString .= "`imdb` ";
    $queryString .= " from `movie`;";
	echo $queryString;
	$fp = fopen("movies.txt","w");
	$k = executeQuery($queryString,$host,$user,$pass,$database);
	
	while ($row=mysql_fetch_assoc($k)){
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
		$queryString .= $row['id']."','";
		$queryString .= $row['title']."','";
		$queryString .= $row['year']."','";
		$queryString .= $row['rated']."','";
		$queryString .= $row['released']."','";
		$queryString .= $row['genre']."','";
		$queryString .= $row['director']."','";
		$queryString .= $row['writer']."','";
		$queryString .= $row['actors']."','";
		$queryString .= $row['plot']."','";
		$queryString .= $row['poster']."','";
		$queryString .= $row['runtime']."','";
		$queryString .= $row['rating']."','";
		$queryString .= $row['votes']."','";
		$queryString .= $row['imdb']."','";
		$queryString .= $row['tstamp']."');";
			
		fwrite($fp,$queryString."\n");
	}
	
	fclose($fp);
	
	
	
?>
	<script type="text/javascript">
		alert("Movie details inserted!");
		window.location = "insertMovies.php";
	</script>
	
</body>
</html>