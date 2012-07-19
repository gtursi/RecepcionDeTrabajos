#!/bin/sh

keytool -genkey -alias dummy -validity 10000 -keypass dummypass -storepass dummypass -keystore dummy.keystore -dname "CN=Dummy, OU=Dummy, O=D, L=CA, ST=CA, C=US"
