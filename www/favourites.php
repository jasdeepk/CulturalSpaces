<?php
error_reporting (-1);
ini_set('display_errors', -1);
ini_set("display_errors", 1);

$dbh = new PDO('mysql:host=localhost;port=3306;dbname=culturalspaces', "root", "root");
if (isset($_GET['userID'])) {
    $userID = $_GET['userID'];

    try {
        $statement = $dbh->query('SELECT locationID from favourites where userID='.$userID);
        $favourites = $statement->fetchAll(PDO::FETCH_ASSOC);
        print json_encode($favourites);
        $dbh = null;
    } catch (PDOException $e) {
        print "Error!: " . $e->getMessage() . "<br/>";
        die();
    }
}
else if (isset($_POST['isFavourited'])) {
    $conn = new PDO("mysql:host=localhost;dbname=culturalspaces","root","root");
    $isFavourited = $_POST['isFavourited'];
    $locationID = $_POST['locationID'];
    $userID = $_POST['userID'];

    error_log("isFav = $isFavourited");
    error_log("locationID = $locationID");
    error_log("userID = $userID");

    
    if ($isFavourited == "true")
    {
        $sql = "INSERT INTO favourites (userID,locationID) VALUES (:userID,:locationID)";
        $q = $conn->prepare($sql);
        $q->execute(array(':userID'=>$userID, ':locationID'=>$locationID));    
    }
    else
    {
        $sql = "DELETE from favourites where userID = ".$userID." AND locationID = ".$locationID;
        $q = $conn->query($sql);
    }
}

?>