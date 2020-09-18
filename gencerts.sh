#!/bin/bash
MV_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

CONTROLLER_DIR=$MV_DIR/nms-microservice
CONSOLE_DIR=$MV_DIR/nms-console

ROOT_CA=$MV_DIR/certs/mnms-rootCA.crt.pem
CONTROLLER_CERT=$MV_DIR/certs/controller
CONSOLE_CERT=$MV_DIR/certs/gui

mkdir certs ; cd certs ; mkdir controller gui

cat > req.conf <<EOF
[req]
distinguished_name = req_distinguished_name
x509_extensions = v3_req
prompt = no
[req_distinguished_name]
C = US
ST = MD
L = SomeCity
O = MNMS
CN = mnms
[v3_req]
subjectKeyIdentifier = hash
authorityKeyIdentifier = keyid:always,issuer:always
basicConstraints = CA:true
subjectAltName = @alt_names
[alt_names]
DNS.1 = mnms
EOF

# generate self-signed certificate
openssl genrsa -out mnms-rootCA.key.pem 4096
openssl req -x509 -new -nodes -days 1024 -sha256 -key mnms-rootCA.key.pem -out mnms-rootCA.crt.pem -config req.conf -extensions 'v3_req'

rm req.conf

cd ./controller

cat > req.conf <<EOF
[req]
distinguished_name = req_distinguished_name
req_extensions = req_ext
prompt = no
[req_distinguished_name]
C = US
ST = MD
L = SomeCity
O = MNMS
CN = mnms.controller
[req_ext]
subjectAltName = @alt_names
[alt_names]
IP.1 = 127.0.0.1
DNS.1 = mnms.controller
EOF

# generate controller certificate
openssl req -out mnms.controller.csr -newkey rsa:2048 -nodes -keyout mnms.controller.key.pem -config req.conf
# openssl req -noout -text -in mnms.controller.csr
openssl x509 -req -days 365 -sha256 -extfile <(printf "subjectAltName=DNS:mnms.controller,IP:127.0.0.1") -in mnms.controller.csr -CA ../mnms-rootCA.crt.pem -CAkey ../mnms-rootCA.key.pem -CAcreateserial -out mnms.controller.crt.pem
# openssl x509 -in mnms.controller.crt.pem -text -noout

rm req.conf

cd ../gui

cat > req.conf << EOF
[req]
distinguished_name = req_distinguished_name
req_extensions = req_ext
prompt = no
[req_distinguished_name]
C = US
ST = MD
L = SomeCity
O = MNMS
CN = mnms.gui
[req_ext]
subjectAltName = @alt_names
[alt_names]
IP.1 = 127.0.0.1
DNS.1 = mnms.gui
EOF

# generate console certificate
openssl req -out mnms.gui.csr -newkey rsa:2048 -nodes -keyout mnms.gui.key.pem -config req.conf
# openssl req -noout -text -in mnms.gui.csr
openssl x509 -req -days 365 -sha256 -extfile <(printf "subjectAltName=DNS:mnms.gui,IP:127.0.0.1") -in mnms.gui.csr -CA ../mnms-rootCA.crt.pem -CAkey ../mnms-rootCA.key.pem -CAcreateserial -out mnms.gui.crt.pem
# openssl x509 -in mnms.gui.crt.pem -text -noout

rm req.conf

cp $ROOT_CA $CONTROLLER_DIR/ca
cp $ROOT_CA $CONSOLE_DIR/docker/cert

cp $CONTROLLER_CERT/*.pem $CONTROLLER_DIR/api-gateway/src/main/resources/cert
cp $CONSOLE_CERT/*.pem $CONSOLE_DIR/docker/cert

cd ../../
rm -rf certs/
