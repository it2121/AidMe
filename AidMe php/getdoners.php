<?php
$conn = mysqli_connect("localhost","root","","aidmedb");
// $name = $POST['name'];
// $age = $POST['age'];
// $number = $POST['number'];
$id = $_POST['id'];
//$id = "1";
$query_check_user = "SELECT * FROM doners WHERE id = '$id'";
$result = mysqli_query($conn,$query_check_user);
if(mysqli_num_rows($result)==0){


}else{

	$row = mysqli_fetch_assoc($result);

	$response['name']=  $row['name'];
	$response['phone']=  $row['phone'];
	$response['gives']=  $row['gives'];
	$response['pass']=  $row['pass'];
	$response['lon']=  $row['lon'];
	$response['lat']=  $row['lat'];

//echo("fuck us all");

}
echo json_encode($response);
mysqli_close($conn);
?>
