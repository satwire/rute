<?php

$host = "localhost";
$user = "root";
$pass = "";
$dbname = "ruteskripsi";

$conn = mysqli_connect($host, $user, $pass, $dbname);
if ($conn) {
    $query = "SELECT * FROM graph";
    $result = mysqli_query($conn, $query);
    $dbdata = array();
    while ($row = $result->fetch_assoc()) {
        $dbdata[] = $row;
    }
    echo json_encode($dbdata);
} else {
    echo json_encode("Cannot connect database");
}
