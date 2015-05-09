<?php
	$f = fopen("leaderboard.txt", "a") or die("Failed to open file");
	if(isset($_GET['name'])) {
		$text = "\n" . $_GET['name'];
		if(flock($f, LOCK_EX)){
			fwrite($f, $text) or die("Could no write to file");
			flock($f, LOCK_UN);
			fclose($f);
			echo "File 'leadership.txt' successfully updated";
		}
	}
?>