<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);

error_reporting(E_ERROR | E_PARSE);


$json       = file_get_contents('php://input');
$json_array = json_decode($json, true);

$lender = $json_array["lender"];
$borrower = $json_array["borrower"];
$amount = $json_array["amount"];

$m= new MongoClient("mongodb://127.0.0.1:27017");
$db = $m->r_db;
$coll = 'user_info';


$collection = $db->$coll;

$query = array("name"=>$borrower);
$cursor = $collection->find($query);
foreach($cursor as $document){		
$borrowerBalance = $document[wallet_balance];
$newborrowerBalance = $borrowerBalance + $amount;
$var = $collection->update(array("name"=>$borrower),array('$set'=>array("wallet_balance"=>$newborrowerBalance, "lender"=>$lender)));
}


$query = array("name"=>$lender);
$cursor = $collection->find($query);
foreach($cursor as $document){		
$lenderBalance = $document[wallet_balance];
$newlenderBalance = $lenderBalance - $amount;
$var = $collection->update(array("name"=>$lender),array('$set'=>array("wallet_balance"=>$newlenderBalance)));
}




$col2 = 'loan';
$collection2 = $db->$col2;
$query = array("requester_name"=>$borrower);
$cursor2 = $collection2->find($query);
foreach($cursor2 as $document){	
$var = $collection2->update(array("requester_name"=>$borrower),array('$set'=>array("lender_name"=>$lender, "pending_amount"=>$amount)));
}



$result = array("success" => 1);
echo json_encode($result);

?>

