<?php
$conn = mysqli_connect("localhost","root","","aidmedb");

$pass = $_POST['pass'];


$sql1 = "SELECT id FROM doners WHERE pass='$pass'";
$result = mysqli_query($conn, $sql1);
$first = 2;
$second = 2 ;

if (mysqli_num_rows($result) > 0) {
	$first = 1;
 	

} else {
 $first = 0;

}



$sql = "DELETE FROM doners WHERE pass='$pass'";
if(mysqli_query($conn,$sql)){
	
}
$sql2 = "SELECT id FROM doners WHERE pass='$pass'";
$result = mysqli_query($conn, $sql2);


if (mysqli_num_rows($result) > 0) {
	$second = 1;
 	

} else {
 $second = 0;

}
if($first==1 && $second ==0){
 $response['what']=1;

}else{

	 $response['what']=2;
}



// if (mysqli_query($conn, $sql)) {
//   $response['what']=  1;

// } else {
//     $response['what']=  2;
// }
 echo json_encode($response);
mysqli_close($conn);
?>