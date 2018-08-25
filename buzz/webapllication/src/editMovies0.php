<?php
	$id="ID";
    $title="TITLE";
    $year="YEAR";
    $rated="RATED";
    $released="RELEASED";
    $genre="GENRE";
    $director="DIRECTOR";
    $writer="WRITER";
    $actors="ACTORS";
    $plot="PLOT";
    $poster="POSTER";
    $runtime="RUNTIME";
    $rating="RATING";
    $votes="VOTES";
    $imdb="IMDB";
    $tstamp="TIMESTAMP";
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
if (!isset($_SESSION['user'])){
?>
<script type="text/javascript">
window.location = "index.php";
</script>
<?php
}
?>

	<h3>Edit Movie</h3>
	<form name="edit" id="form" method="post" action = "editMovies.php">
		<table>
			<tr>
				<th>
					Title
				</th>
				<td>
					<select name="id" id="id">
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
						$queryString .= "`title`";
						
						$queryString .= " from `movie` order by `title`;";
						
						
						$k = executeQuery($queryString,$host,$user,$pass,$database);
						
						while ($row=mysql_fetch_assoc($k)){
							echo "<option value='".$row['id']."'>".$row['title']."</option>";
							
						}

					?>
					</select>
				</td>
			</tr>
			
			<tr>
				<th colspan="2">
					
					
					<input type="submit" value="SELECT">
				</th>
			</tr>
		</table>
	</form>
	<hr>
	<a href="index.php">Back</a>
</body>
</html>