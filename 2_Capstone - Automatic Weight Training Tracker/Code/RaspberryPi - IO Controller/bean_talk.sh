#!/bin/bash

#This script connects to the bean and reads the temperature
#from two scratch characteristics and then prints them
#to the output stream. c code will call this script and
#collect the values written to the output stream

# "$()" store result of cmd in brackets into variable temp
# temp is stored in scratch 1 (0x0033) and weight is stored in scratch 2 (0x0037)
#D0:39:72:D3:4D:83 is the MAC address of the LightBlue Bean that we used
#gatttool is provided by the BlueZ library to access properties that are advertised 
#	by a bluetooth LE device
 
temp="$(sudo gatttool -b D0:39:72:D3:4D:83 --char-read -a 0x0033)"

if [ "$?" -ne "0" ]
then
	#connection to bean has failed return error code 
	echo "6B"
	exit 1
fi

weight="$(sudo gatttool -b D0:39:72:D3:4D:83 --char-read -a 0x0037)"

if [ "$?" -ne "0" ]
then
	#connection to bean has failed return error code 
	echo "6B"
	exit 1;
fi

#Start at 33 character and take two chars to get only temperature
temp=${temp:33:2}
weight=${weight:33:3}

echo $temp
echo $weight