#!/bin/bash
#this script uses BlueZ to startup the bluetooth radio that
#is connected to the Raspberry Pi via USB

sudo hciconfig hci0 up
