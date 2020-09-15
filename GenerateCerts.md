# Generate Keys and Certificates

The following instructions will generate a self-signed certificate for the MNMS project, and two server certificates: one for the controller and one for the Web-console.
Make sure OpenSSL is installed.

First, create the directory structure with the following command:

```bash
mkdir certs ; cd certs ; mkdir controller gui
```

## Self-signed Root Certificate

In the `certs` directory, create the file `req.conf` with the following content: 

```text
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
```

Execute the following commands to generate a self-signed root certificate:

```bash
openssl genrsa -out mnms-rootCA.key.pem 4096
openssl req -x509 -new -nodes -days 1024 -sha256 -key mnms-rootCA.key.pem -out mnms-rootCA.crt.pem -config req.conf -extensions 'v3_req'
```

## Controller Key and Certificate

In the `certs/controller` directory, create the file `req.conf` with the following content: 

```text
[req]
basicConstraints = CA:FALSE
nsCertType = server
subjectKeyIdentifier = hash
authorityKeyIdentifier = keyid,issuer:always
keyUsage = critical, digitalSignature, keyEncipherment
extendedKeyUsage = serverAuth
subjectAltName = @alt_names
[alt_names]
IP.1 = 127.0.0.1
DNS.1 = mnms.controller
```

Execute the following commands to generate a key and a certificate for the controller:

```bash
openssl genrsa -out mnms.controller.key.pem 2048
openssl req -new -key mnms.controller.key.pem -out mnms.controller.csr -subj "/C=US/ST=MD/L=SomeCity/O=MNMS/CN=mnms.controller"
openssl x509 -req -in mnms.controller.csr -CA ../mnms-rootCA.crt.pem -CAkey ../mnms-rootCA.key.pem -out mnms.controller.crt.pem -CAcreateserial -days 365 -sha256 -extfile req.conf
```

## Web-console Key and Certificate

In the `certs/gui` directory, create the file `req.conf` with the following content: 

```text
[req]
basicConstraints = CA:FALSE
nsCertType = server
subjectKeyIdentifier = hash
authorityKeyIdentifier = keyid,issuer:always
keyUsage = critical, digitalSignature, keyEncipherment
extendedKeyUsage = serverAuth
subjectAltName = @alt_names
[alt_names]
IP.1 = 127.0.0.1
DNS.1 = mnms.gui
```

Execute the following commands to generate a key and a certificate for the console:

```bash
openssl genrsa -out mnms.gui.key.pem 2048
openssl req -new -key mnms.gui.key.pem -out mnms.gui.csr -subj "/C=US/ST=MD/L=SomeCity/O=MNMS/CN=mnms.gui"
openssl x509 -req -in mnms.gui.csr -CA ../mnms-rootCA.crt.pem -CAkey ../mnms-rootCA.key.pem -out mnms.gui.crt.pem -CAcreateserial -days 365 -sha256 -extfile req.conf
```

## Set Certificates in the Project

To set the certificates in the right directories do the following:

- copy the file `mnms-rootCA.crt.pem` into both `<controller-dir>/ca/` and `<console-dir>/docker/cert/` directories
- copy the files `mnms.controller.crt.pem` and `mnms.controller.key.pem` into the `<controller-dir>/api-gareway/src/main/resources/cert/` directory
- copy the files `mnms.gui.crt.pem` and `mnms.gui.key.pem` into `<console-dir>/docker/cert/` directory

## Trust Certificates Locally

In order to allow https traffic between the controller and the Web-console server, browsers must be configured to trust the local certificate authority which signed the controller and the Web-console server certificates.
In addition, the browser will show up the nice green lock when accessing the Web-console.

To do so, import the file `mnms-rootCa.crt.pem` as a CA certificate into your browser. 
The steps depend on the browser:

- Chrome: navigate to `chrome://settings/certificates`. In the `Authorities` tab, import the certificate file and check `Trust this certificate for identifying websites`.
- Firefox: navigate to `about:preferences#privacy`, scroll down to the `Certificates` section and click on `View Certificates`.
In the `Authorities` tab, import the certificate file and check `Trust this CA to identify websites`.

Restart your browser after adding the certificate.