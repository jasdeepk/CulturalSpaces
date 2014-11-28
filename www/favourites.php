<?php
error_reporting (-1);
ini_set('display_errors', -1);
ini_set("display_errors", 1);
echo 'hi';
if (isset($_POST['variable'])) {
      echo $_POST['variable'];
      $userID = $_POST['variable'];
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