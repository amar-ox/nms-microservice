
<!-- h1 align="center"> Multiverse Network Management System [Controller] </h1 -->
<p align="center">
  <img src="docs/images/logo.png" />
</p>

> Note: This repo is the Controller part of the MNMS project.

## Overview
The Multiverse Network Management System (MNMS) project aims at providing a feature-rich solution for configuring, monitoring and managing networks. 
Although MNMS is designed with commercial (IP) network management features in mind (e.g., FCAPS), a great attention is dedicated to natively supporting the information-centric concepts of [Named Data Networking](https://named-data.net/).

MNMS defines two main entities. First, the management agent running on each managed node (e.g., a router). Second, the controller, which represents the central controlling entity of MNMS, provides a set of services for network management and telemetry. 
The agents and the controller interact in order to provide two sets of services accessible via a Web-based user interface: (i) telemetry which allows the operator to trigger customized measurements and collect results, and (ii) network management through which the operator configures and manages the network, assisted by the automatic processing features of the controller.

This version (v1) supports NDN deployments with the NDN-DPDK forwarder. 

For the management services, the controller and the agents communicate over HTTPS using the REST API. The OpenAPI Controller-Agent specification can be found [here](https://github.com/amar-ox/nms-microservice/blob/master/docs/openapi/controller-to-agent.yaml). 

For the telemetry service, the controller and the agents use the publish-subscribe communication pattern with AMQP.

## Deployment Instuructions
This project works with Vert.x **3.9.0**. It can be deployed as a multicontainer application. The following instructions have been tested to run on Ubuntu 16.04, 18.04, and 20.04.

### Prerequisites
In order to build and run the controller, the system must have the following installed:
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
If you run the project for the first time, there are some configurations required before using the console.

First, edit the `/etc/hosts` file and add the following entries:
```
127.0.0.1	mnms.gui
127.0.0.1	mnms.controller
```

In order to allow the https traffic between the controller and the console server, browsers must be configured to trust the certificate authority which signed the controller and the console server certificates.
In addition, the browser will show up the nice green lock.

To do so, import the file `nms-microservice/ca/mnms-rootCa.crt.pem` as a CA certificate into your browser. The steps depend on the browser:

- Chorme: navigate to `chrome://settings/certificates`. In the `Authorities` tab, import the certificate file and check `Trust this certificate for identifying websites`.
- Firefox: navigate to `about:preferences#privacy`, scroll down to the `Certificates` section and click on `View Certificates`. 
Import the certificate file and check `Trust this CA to identify websites`.

### MNMS is Ready
The Multiverse Network Management System is finally ready.
The Web console is accessible at `https://mnms.gui:4443`.
Login with: `username=admin password=admin`.

## Deploy from DockerHub images
[TBD]

## Use Telemetry (experimental)
> Note: The telemetry service is not secured yet, i.e., agents are not authenticated to the controller and communications are not encrypted. We rely on the next version of MNMS to secure telemetry.

To use the telemetry service, you have to deploy a [Telemetry Agent](https://github.com/amar-ox/nms-telemetry-agent):
```
git clone https://github.com/amar-ox/nms-telemetry-agent.git
cd nms-telemetry-agent
mvn clean install
java -jar target/nms-telemetry-agent-fat.jar src/conf/conf.json
```
> Note: In this example, the agent is configured to run on the same host as the controller.
More information on configuring the agents will be available soon.

## Contributing
Contributions and feedback are definitely welcome!
