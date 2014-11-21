<?php
ini_set('error_reporting', E_ALL);
ini_set('display_errors', "stdout");

try {
    $dbh = new PDO('mysql:host=localhost;port=3306;dbname=culturalspaces', "root", "root");
    $statement = $dbh->query('SELECT * from relocations');
    $locations = $statement->fetchAll(PDO::FETCH_ASSOC);
    print json_encode($locations);
    $dbh = null;
} catch (PDOException $e) {
    print "Error!: " . $e->getMessage() . "<br/>";
    die();
}

?>