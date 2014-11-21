<?php
$databasehost = "localhost"; 
$databasename = "culturalspaces"; 
$databasetable = "relocations"; 
$databaseusername="root"; 
$databasepassword = ""; 
$fieldseparator = ",";
$fieldenclosure = '\"'; 
$lineseparator = "\n";
$csvfile = "CulturalSpaces.csv";

if(!file_exists($csvfile)) {
    die("File not found. Make sure you specified the correct path.");
}

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
    $pdo = new PDO('mysql:host=localhost;port=3306;dbname=culturalspaces', "root", "root", array(PDO::MYSQL_ATTR_LOCAL_INFILE=>1));
} catch (PDOException $e) {
    die("database connection failed: ".$e->getMessage());
}

//clear table
$pdo->exec("TRUNCATE TABLE relocations");

//parse file's data into table
$affectedRows = $pdo->exec("
    LOAD DATA LOCAL INFILE ".$pdo->quote($csvfile)." INTO TABLE `$databasetable`
      
      FIELDS OPTIONALLY ENCLOSED BY '\"' TERMINATED BY ".$pdo->quote($fieldseparator)." 
      LINES TERMINATED BY ".$pdo->quote($lineseparator));


echo "Loaded a total of $affectedRows records from this csv file.\n";

?>