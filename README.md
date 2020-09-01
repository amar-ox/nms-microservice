
<h1 align="center"> Multiverse Network Management System [Controller] </h1>
<p align="center">
  <img src="docs/images/logo.png" />
</p>

## Overview
The Multiverse Network Management System (MNMS)...


## Deployment Instuructions
This project works with Vert.x **3.9.0**. It can be deployed as a multicontainer application. The following instructions have been tested to run on Ubuntu 16.04, 18.04, and 20.04.

### Prerequisites
In order to build and run the controller, the system requires the following tools to e installed:
- Java 8 (openJDK 1.8)
- Maven (versions 3.3 to 3.6 should work fine)
- Docker (19.03.12)
- Docker-compose (1.26.2)

### Build/Run
#### Controller
First, to deploy the controller, open a terminal and clone the (current) repo:

```
git clone https://github.com/amar-ox/nms-microservice.git
```

Then build the code:

```
cd nms-microservice
mvn clean install -Dmaven.test.skip=true
```

After that, build all Docker containers
```
cd docker
sudo ./build.sh
```

Once the build finished, run the controller:
```
sudo ./run.sh
```

#### Console (GUI)
Second, we deploy the web-inteface console; Open another terminal and clone [the MNMS console](https://github.com/amar-ox/nms-console.git) repo:

```
git clone https://github.com/amar-ox/nms-console.git
```

Then build the container:

```
cd nms-console
sudo docker build -t mnms/console .
```

Once the build finished, run the console:
```
sudo docker run -it -p 4443:443 --network=docker_nms --rm --name mnms-console mnms/console
```

### Configuration
If you run the project for the first time, there are some configurations required before getting started.

First, edit the `/etc/hosts` file and add the following entries:
```
127.0.0.1	mnms.gui
127.0.0.1	mnms.controller
```

In order to allow the https traffic between the controller and the console server, browsers must be configured to trust the certificate authority which signed the controller and the console server certificates.
In addition, the browser will show up the nice green lock.

To do so, import the file `nms-microservice/ca/mnms-rootCa.crt.pem` as a CA certificate into your browser. The steps depend on the browser:

- Chorme: navigate to `chrome://settings/certificates` and go to the `Authorities` tab. Import the certificate file and check `trust this certificate for identifying websites`.
- Firefox: navigate to `chrome://settings/certificates` and go to the `Authorities` tab. Import the certificate file and check `trust this certificate for identifying websites`.

### Getting stated
The Multiverse Network Management System is finally ready.
The Web console is accessible at `https://mnms.gui:4443`.
Login with: `username=admin password=admin`.

## Deploy from DockerHub images
[TBD]

## Use Telemetry (experimental)
[TBD]

## Contributing
Contributions and feedback are definitely welcome!
