<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);

error_reporting(E_ERROR | E_PARSE);


$m = new MongoClient("mongodb://root:jV3Qe02d5!nn@ds127536.mlab.com:27536/r_db");
$db = $m->r_db;
$name = 'try';
$type = 'EMI';
$amount = "200";
$account_number = '123';
$p_d = 0;
$additional = '_payments';
$name = $name.$additional;
$collection = $db->$name;

$value1 = '2';

$value1 = '1';

$document = array( 
      "account_number" => $account_number, 
      "amount" => $amount, 
      "timely_payment" => $value1,
	  "default_payment" => $value2,
      "type" => $type, 

   );
	
$collection->insert($document);


?>