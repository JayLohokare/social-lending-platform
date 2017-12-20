<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);

error_reporting(E_ERROR | E_PARSE);


$json       = file_get_contents('php://input');
$json_array = json_decode($json, true);

$username = $json_array["username"];
$amount = $json_array["amount"];

$amount = (int)$amount;
$m= new MongoClient("mongodb://127.0.0.1:27017");
$db = $m->r_db;
$coll = 'user_info';
$collection = $db->$coll;
$query = array("name"=>$username, "loan_amount" => 0);

$result = array(
				"success" => 0
			);
			
$cursor = $collection->find($query);


foreach($cursor as $doc){
	$credit_score = $doc[credit_score];
	
	$var = $collection->update(array("name"=>$username),array('$set'=>array("loan_amount"=>$amount)));
	$result = array(
				"success" => 1,

			);
			
	$coll2 = 'loan';
	$collection2 = $db->$coll2;
	
	$document = array( 
      "requester_name" => $username, 
      "lender_name" => 0, 
	  "requester_credit_score" => $credit_score,
	  "loan_amount" => $amount,
	  "loan_amount" => $amount,
	  
	  "pending_amount" => 0
   );
   $collection2->insert($document);
	
}


echo json_encode($result);

?>

