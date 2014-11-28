<?php
ini_set('error_reporting', E_ALL);
ini_set('display_errors', "stdout");

$databasehost = "localhost"; 
$databasename = "culturalspaces"; 
$databasetable = "locations"; 
$databaseusername="root"; 
$databasepassword = ""; 
$fieldseparator = ",";
$fieldenclosure = '\"'; 
$lineseparator = "\n";

system("curl -o inbound.csv ftp://anonymous:anonymous@webftp.vancouver.ca/opendata/CulturalSpace/CulturalSpaces.csv");
try {
    /*
    $pdo = new PDO("mysql:host=$databasehost;port=3306;dbname=$databasename", 
        $databaseusername, $databasepassword,
        array(
            PDO::MYSQL_ATTR_LOCAL_INFILE => true,
            PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
        )
    );
    */
    $pdo = new PDO('mysql:host=mysql.culturalspaces.lambyte.com;port=3306;dbname=culturalspaces', "lambyte", "p0000000", array(PDO::MYSQL_ATTR_LOCAL_INFILE=>1));
} catch (PDOException $e) {
    die("database connection failed: ".$e->getMessage());
}

//clear table
$pdo->exec("TRUNCATE TABLE locations");

//parse file's data into table
$affectedRows = $pdo->exec("
    LOAD DATA LOCAL INFILE ".$pdo->quote("inbound.csv")." INTO TABLE `$databasetable`
      
      FIELDS OPTIONALLY ENCLOSED BY '\"' TERMINATED BY ".$pdo->quote($fieldseparator)." 
      LINES TERMINATED BY ".$pdo->quote($lineseparator));

try {
    $dbh = new PDO('mysql:host=mysql.culturalspaces.lambyte.com;port=3306;dbname=culturalspaces', "lambyte", "p0000000");
    $statement = $dbh->query('SELECT * from locations');
    $locations = $statement->fetchAll(PDO::FETCH_ASSOC);
    print json_encode($locations);
    $dbh = null;
} catch (PDOException $e) {
    print "Error!: " . $e->getMessage() . "<br/>";
    die();
}

?>