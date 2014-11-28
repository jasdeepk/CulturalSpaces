<?php
ini_set('error_reporting', E_ALL);
ini_set('display_errors', "stdout");

if (isset($_GET['var_PHP_data'])) {
      echo $_GET['var_PHP_data'];;
      $userID = $_GET['var_PHP_data'];
    } 

try {
    $dbh = new PDO('mysql:host=localhost;port=3306;dbname=culturalspaces', "root", "root");
    $statement = $dbh->query('SELECT locationID from favourites where userID='$userID'');
    $favourites = $statement->fetchAll(PDO::FETCH_ASSOC);
    print json_encode($favourites);
    $dbh = null;
} catch (PDOException $e) {
    print "Error!: " . $e->getMessage() . "<br/>";
    die();
}

?>