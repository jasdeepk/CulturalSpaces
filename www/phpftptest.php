<?php

//$handle = fopen('ftp://anonymous:anonymous@webftp.vancouver.ca/opendata/CulturalSpace/CulturalSpaces.csv', 'r');
//echo fgets($handle);

system("curl -o test.csv ftp://anonymous:anonymous@webftp.vancouver.ca/opendata/CulturalSpace/CulturalSpaces.csv");
?>