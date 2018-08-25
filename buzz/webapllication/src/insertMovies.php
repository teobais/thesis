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
	<h3>Insert Movie</h3>
	<form name="insert" id="form" method="post" action = "insertOk.php">
		<table>
			<tr>
				<th>
					<?php echo $id; ?>
				</th>
				<td>
					<input type="text" name="id" id="id">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $title; ?>
				</th>
				<td>
					<input type="text" name="title" id="title">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $year; ?>
				</th>
				<td>
					<input type="text" name="year" id="year">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $rated; ?>
				</th>
				<td>
					<input type="text" name="rated" id="rated">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $released; ?>
				</th>
				<td>
					<input type="text" name="released" id="released">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $genre; ?>
				</th>
				<td>
					<input type="text" name="genre" id="genre">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $director; ?>
				</th>
				<td>
					<input type="text" name="director" id="director">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $writer; ?>
				</th>
				<td>
					<input type="text" name="writer" id="writer">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $actors; ?>
				</th>
				<td>
					<input type="text" name="actors" id="actors">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $plot; ?>
				</th>
				<td>
					<input type="text" name="plot" id="plot">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $runtime; ?>
				</th>
				<td>
					<input type="text" name="runtime" id="runtime">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $poster; ?>
				</th>
				<td>
					<input type="text" name="poster" id="poster">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $rating; ?>
				</th>
				<td>
					<input type="text" name="rating" id="rating">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $votes; ?>
				</th>
				<td>
					<input type="text" name="votes" id="votes">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $imdb; ?>
				</th>
				<td>
					<input type="text" name="imdb" id="imdb">
				</td>
			</tr>
			<tr>
				<th>
					<?php echo $tstamp; ?>
				</th>
				<td>
					<input type="text" name="tstamp" id="tstamp">
				</td>
			</tr>
			<tr>
				<th colspan="2">
					
					<input class="button" type="submit" value="INSERT">
				</th>
			</tr>
		</table>
	</form>
	<hr>
	<a href="index.php">Back</a>
</body>
</html>