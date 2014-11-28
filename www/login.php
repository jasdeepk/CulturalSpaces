<?php
error_reporting(-1);
ini_set('display_errors', 1);

session_start();
require_once dirname(__FILE__).'/google-api-php-client/autoload.php';


$client = new Google_Client();
$client->setApplicationName('hitmail');
$client->setClientId('183665922035-guv6irto0u06jqb7rah717vdhkd60rfe.apps.googleusercontent.com');
$client->setClientSecret('OkX_JBOvN32OZrIBYq01vhBg');
$client->addScope(https://www.googleapis.com/auth/plus.me)
$client->setRedirectUri('http://google.com');
$client->setDeveloperKey('AIzaSyA3-m4BM9mWnCfk0lvRslAr3L7Qp1-CvV4'); // API key

$auth_url = $client->createAuthUrl();
header('Location: ' . filter_var($auth_url, FILTER_SANITIZE_URL));


// $service implements the client interface, has to be set before auth call
$services = new Analytics();
$service = $services->__construct($client);

if (isset($_GET['logout'])) { // logout: destroy token
    unset($_SESSION['token']);
	die('Logged out.');
}

if (isset($_GET['code'])) { // we received the positive auth callback, get the token and store it in session
    $client->authenticate();
    $_SESSION['token'] = $client->getAccessToken();
}

if (isset($_SESSION['token'])) { // extract token from session and configure client
    $token = $_SESSION['token'];
    $client->setAccessToken($token);
}

if (!$client->getAccessToken()) { // auth call to google
    $authUrl = $client->createAuthUrl();
    header("Location: ".$authUrl);
    die;
}
echo 'Hello, world.';

  ?>