#!/bin/bash

cd $1
mkdir certs ; cd certs ; mkdir ca ; mkdir controller ; mkdir console

cd ca
cat > req.conf <<EOF
[req]
distinguished_name = req_distinguished_name
x509_extensions = v3_req
prompt = no
[req_distinguished_name]
C = US
ST = MD
L = SomeCity
O = MultiverseNMS
CN = multiverse.com
[v3_req]
subjectKeyIdentifier = hash
authorityKeyIdentifier = keyid:always,issuer:always
basicConstraints = CA:TRUE
subjectAltName = @alt_names
[alt_names]
DNS.1 = multiverse.com
EOF

# generate self-signed certificate
openssl genrsa -out MultiverseRootCA.key.pem 4096
openssl req -x509 -new -nodes -days 1024 -sha256 -key MultiverseRootCA.key.pem -out MultiverseRootCA.crt.pem -config req.conf -extensions 'v3_req'

rm req.conf

cd ../controller

cat > req.conf <<EOF
[req]
distinguished_name = req_distinguished_name
req_extensions = req_ext
prompt = no
[req_distinguished_name]
C = US
ST = MD
L = SomeCity
O = MultiverseNMS
CN = controller.multiverse.com
[req_ext]
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
subjectAltName = @alt_names
[alt_names]
DNS.1 = controller.multiverse.com
EOF

cat > req.ext <<EOF
subjectAltName=DNS:controller.multiverse.com
EOF

# generate controller certificate
openssl req -out multiverse.controller.csr -newkey rsa:2048 -nodes -keyout multiverse.controller.key.pem -config req.conf
# openssl req -noout -text -in multiverse.controller.csr
openssl x509 -req -days 365 -sha256 -extfile req.ext -in multiverse.controller.csr -CA ../ca/MultiverseRootCA.crt.pem -CAkey ../ca/MultiverseRootCA.key.pem -CAcreateserial -out multiverse.controller.crt.pem
# openssl x509 -in multiverse.controller.crt.pem -text -noout

rm req.*
rm *.csr

cd ../console

cat > req.conf << EOF
[req]
distinguished_name = req_distinguished_name
req_extensions = req_ext
prompt = no
[req_distinguished_name]
C = US
ST = MD
L = SomeCity
O = MultiverseNMS
CN = console.multiverse.com
[req_ext]
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
subjectAltName = @alt_names
[alt_names]
DNS.1 = console.multiverse.com
EOF

cat > req.ext <<EOF
subjectAltName=DNS:console.multiverse.com
EOF

# generate console certificate
openssl req -out multiverse.console.csr -newkey rsa:2048 -nodes -keyout multiverse.console.key.pem -config req.conf
# openssl req -noout -text -in multiverse.console.csr
openssl x509 -req -days 365 -sha256 -extfile req.ext -in multiverse.console.csr -CA ../ca/MultiverseRootCA.crt.pem -CAkey ../ca/MultiverseRootCA.key.pem -CAcreateserial -out multiverse.console.crt.pem
# openssl x509 -in multiverse.console.crt.pem -text -noout

rm req.*
rm *.csr