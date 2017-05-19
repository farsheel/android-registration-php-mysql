<?php

## Defining some contants ##

define('HOST', 'localhost');
define('USER', 'root');
define('PASSWORD', '');
define('DB', 'precoders_demo');

############################


$con=new mysqli(HOST,USER,PASSWORD,DB); # Creating connection

if ($con->connect_error) # Checking the connection
{
    die("Connection failed: " . $con->connect_error); # Printing if there is any error
}
?>