<?php
/*ini_set('display_errors', 1);
$filename = __DIR__.DIRECTORY_SEPARATOR."fileinfo.json";
$json = $_POST{'json'};
$data = json_decode($json, FALSE);

if(is_null($json) == false){
	file_put_contents($filename,"$json \n", FILE_APPEND);
	foreach ($data as $name => $value) {
		file_put_contents($filename, "$name -> $value \t", FILE_APPEND);
	}
	file_put_contents($filename, "\n", FILE_APPEND);
}

$myFile = "fileinfo.json";
$fh = fopen($myFile, 'r');
$theData = fread($fh, filesize($myFile));
fclose($fh);*/

if(isset($_POST["json"])){
	$data = json_decode($_POST["json"]);
	$data -> msg = strrev($data -> msg);

	echo json_encode(($data));
}

?>

<meta http-equiv="refresh" content="3">
<html>
	<p><?php echo $theData; ?></p>
</html>