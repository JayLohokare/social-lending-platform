<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);

error_reporting(E_ERROR | E_PARSE);


$json       = file_get_contents('php://input');
$json_array = json_decode($json, true);

$username = $json_array["username"];
 
 
$m= new MongoClient("mongodb://127.0.0.1:27017");
$db = $m->r_db;
$coll = 'user_info';
$collection = $db->$coll;

$query = array("name"=>$username);

$cursor = $collection->find($query);
$result = array("success" => 0);

foreach($cursor as $document){
		
		$loan_amount = $document[loan_amount];
		$current_balance = $document[wallet_balance];
		
		if($loan_amount== 0){
			$coll2 = 'loan';
			$collection2 = $db->$coll2;
			$query = array("pending_amount" => 0, "loan_amount" => array( '$lt' => $current_balance ));
			
			$cursor = $collection2->find($query);
		
			$array= [];
			foreach($cursor as $doc3){
				array_push($array, $doc3);
				
			}
			$result = [];
			$result["data"] = $array;
			
			
		}
}
		
	
echo json_encode($result);

?>

