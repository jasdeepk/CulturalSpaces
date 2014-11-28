<?php
$databasehost = "localhost"; 
$databasename = "test_db"; 
$databasetable = "test_table"; 
$databaseusername="root"; 
$databasepassword = ""; 
$fieldseparator = ",";
$fieldenclosure = '\"'; 
$lineseparator = "\n";
$csvfile = "test.csv";

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
    $pdo = new PDO('mysql:host=localhost;port=3306;dbname=test_db', "root", "root", array(PDO::MYSQL_ATTR_LOCAL_INFILE=>1));
} catch (PDOException $e) {
    die("database connection failed: ".$e->getMessage());
}

//clear table
$pdo->exec("TRUNCATE TABLE test_table");

//parse file's data into table
$affectedRows = $pdo->exec("
    LOAD DATA LOCAL INFILE ".$pdo->quote($csvfile)." INTO TABLE `$databasetable`
      
      FIELDS OPTIONALLY ENCLOSED BY '\"' TERMINATED BY ".$pdo->quote($fieldseparator)." 
      LINES TERMINATED BY ".$pdo->quote($lineseparator));


/*echo "Loaded a total of $affectedRows records from this csv file.\n"; */


$dbh = new PDO('mysql:host=localhost;port=3306;dbname=test_db', "root", "root");
    $statement = $dbh->query('SELECT * from test_table');
    $rows = $statement->fetchAll(PDO::FETCH_ASSOC);
   
$test_array = array(
    array(dummy_name1 => "1", dummy_name2 => "a"),
    array(dummy_name1 => "2", dummy_name2 => "b")
    )

if (($rows[0] == $test_array[0]) && ($rows[1] == $test_array[1]))
    {
 echo "Works!\n";
}
 else echo "Failure.\n";

?>