<?php
$conn = mysqli_connect("localhost","root","","testdb");
$name = $_POST['name'];
$age = $_POST['age'];
$number = $_POST['number'];
// $name = "is";
// $age = "3";
// $number = "656456";
//if (!$conn) {
  //die("Connection failed: " . mysqli_connect_error());
//}

$sql = "INSERT INTO testtable (name,age,number) VALUES ('$name','$age','$number')";
if(mysqli_query($conn,$sql)){
	
}



//if (mysqli_query($conn, $sql)) {
 // echo "New record created successfully";
//} else {
  //echo "Error: " . $sql . "<br>" . mysqli_error($conn);
//}

//if($conn->query($sql)){}
//$result = mysql_query($conn,$query_result);
?>