<?php
##############################
#    @Author  : Farsheel
#    @website : precoders.com
##############################


include 'dbConnect.php'; # Including the database connection file

if(!empty($_REQUEST['name']) && !empty($_REQUEST['email']) && !empty($_REQUEST['password'])) # Checking all the fields 
{

	## Initialising variables for the data
	$name=$con->real_escape_string($_REQUEST['name']);
	$email=$con->real_escape_string($_REQUEST['email']);
	$password=md5($_REQUEST['password']); # Encrypting the password with md5

	## Checking the email is already exist in the db ##
	$check_stmnt=$con->prepare("SELECT email from users where email=?"); # Prepared Query
	$check_stmnt->bind_param("s",$email);	# Setting parameters to the query
	$check_stmnt->execute();				# Executing the query
	$result=$check_stmnt->get_result();
	$check_stmnt->close();
	if($result->num_rows>0)
	{
		echo "The email is already Registered";
		
	}
	
	#/# Checking the email is already exist in the db #/#

	else
	{
		$reg_stmt=$con->prepare("INSERT into users(full_name,email,password) values(?,?,?)"); # Prepared Query

		

		$reg_stmt->bind_param("sss",$name,$email,$password); # Setting parameters to the query

		if($reg_stmt->execute()) # Executing the query
		{
			echo "true";  # Print true if success
		}
		else
		{
			echo "false";
		}

		$reg_stmt->close(); 
	}


}
else
{
	echo "All Fields are required";
}

?>