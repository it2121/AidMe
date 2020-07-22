<?php
$conn = mysqli_connect("localhost","root","","aidmedb");
$id = $_POST['id'];
$query = "SELECT id FROM needes"; 
$result = mysqli_query($conn, $query); 
$row = mysqli_num_rows($result); 
$response['id']=  $row;

echo json_encode($response);
mysqli_close($conn);
?>


