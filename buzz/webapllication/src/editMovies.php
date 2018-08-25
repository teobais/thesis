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
    $queryString .= " from `movie` where `id`='".$_POST['id']."';";
	
	$k = executeQuery($queryString,$host,$user,$pass,$database);
	
	$row=mysql_fetch_assoc($k);
		
	
?>
	<h3>Edit Movie</h3>
	<form name="edit" id="form" method="post" action = "editOk.php">
		<table>
			<tr>
				<th>
					<?php echo $id; ?>
				</th>
				<td>
					<input type="text" name="id" id="id" value="<?php echo $row['id']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $title; ?>
				</th>
				<td>
					<input type="text" name="title" id="title" value="<?php echo $row['title']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $year; ?>
				</th>
				<td>
					<input type="text" name="year" id="year" value="<?php echo $row['year']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $rated; ?>
				</th>
				<td>
					<input type="text" name="rated" id="rated" value="<?php echo $row['rated']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $released; ?>
				</th>
				<td>
					<input type="text" name="released" id="released" value="<?php echo $row['released']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $genre; ?>
				</th>
				<td>
					<input type="text" name="genre" id="genre" value="<?php echo $row['genre']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $director; ?>
				</th>
				<td>
					<input type="text" name="director" id="director" value="<?php echo $row['director']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $writer; ?>
				</th>
				<td>
					<input type="text" name="writer" id="writer" value="<?php echo $row['writer']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $actors; ?>
				</th>
				<td>
					<input type="text" name="actors" id="actors" value="<?php echo $row['actors']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $plot; ?>
				</th>
				<td>
					<input type="text" name="plot" id="plot" value="<?php echo $row['plot']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $runtime; ?>
				</th>
				<td>
					<input type="text" name="runtime" id="runtime" value="<?php echo $row['runtime']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $poster; ?>
				</th>
				<td>
					<input type="text" name="poster" id="poster" value="<?php echo $row['poster']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $rating; ?>
				</th>
				<td>
					<input type="text" name="rating" id="rating" value="<?php echo $row['rating']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $votes; ?>
				</th>
				<td>
					<input type="text" name="votes" id="votes" value="<?php echo $row['votes']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $imdb; ?>
				</th>
				<td>
					<input type="text" name="imdb" id="imdb" value="<?php echo $row['imdb']; ?>">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $tstamp; ?>
				</th>
				<td>
					<input type="text" name="tstamp" id="tstamp" value="<?php echo $row['tstamp']; ?>">
				</td>
			</tr>
			<tr>
				<th colspan="2">
					
				
					<input class="button" type="submit" value="EDIT">
				</th>
			</tr>
		</table>
	</form>
	<hr>
	<a href="index.php">Back</a>
</body>
</html>