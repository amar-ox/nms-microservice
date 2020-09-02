
<!-- h1 align="center"> Multiverse Network Management System [Controller] </h1 -->
<p align="center">
  <img src="docs/images/logo.png" />
</p>

> Note: This repo is the Controller part of the MNMS project.

## Overview
The Multiverse Network Management System (MNMS) project aims at providing a feature-rich solution for configuring, monitoring and managing networks. 
Although MNMS is designed with commercial (IP) network management features in mind (e.g., FCAPS), a great attention is dedicated to natively supporting the information-centric concepts of [Named Data Networking](https://named-data.net/) (NDN).

MNMS defines two main entities. First, the agent running on each managed node (e.g., a router). Second, the controller which represents the brain of MNMS and provides a set of services for network management and telemetry. 
The agents and the controller interact in order to provide two sets of services accessible via a Web-based user interface: 
- Telemetry, which allows the operator to trigger customized measurements and collect results.
- Network management through which the operator configures and manages the network, assisted by the automatic processing features of the controller.

The current version (v1) of MNMS supports NDN deployments with the NDN-DPDK forwarder. 

For the network management services, the controller and the [Management Agent](https://github.com/elmiomar/nms-agent) communicate over HTTPS using the REST API. The OpenAPI Controller-Agent specification can be found [here](https://github.com/amar-ox/nms-microservice/blob/master/docs/openapi/controller-to-agent.yaml). 

For the telemetry service, the controller and the [Telemetry Agent](https://github.com/amar-ox/nms-telemetry-agent) use the publish-subscribe communication pattern with AMQP.

## Deployment Instuructions
The controller (this repo) and the [Web console](https://github.com/amar-ox/nms-console.git) are deployable as Docker containers. 
The following instructions have been tested to run on Ubuntu 16.04, 18.04, and 20.04.

### Prerequisites
The system must have the following tools installed:
- Java 8 (openJDK 1.8)
- Maven (versions 3.3 to 3.6 should work fine)
- Docker (19.03.12)
- Docker-compose (1.26.2)

### Build/Run
#### Controller
First, deploy the controller. Open a terminal and run the following commands:

```
git clone https://github.com/amar-ox/nms-microservice.git
cd nms-microservice
mvn clean install -Dmaven.test.skip=true
cd docker
sudo ./build.sh
sudo ./run.sh
```

#### Web-console (GUI)
Second, deploy the Web-console. Open another terminal run the following commands:

```
git clone https://github.com/amar-ox/nms-console.git
cd nms-console
sudo docker build -t mnms/console .
sudo docker run -it -p 4443:443 --network=docker_nms --rm --name mnms-console mnms/console
```

### Configuration
When you run the project for the first time, there are some configurations to do.

Edit the `/etc/hosts` file and add the following entries:
```
127.0.0.1	mnms.gui
127.0.0.1	mnms.controller
```

In order to allow https traffic between the controller and the Web-console server, browsers must be configured to trust the certificate authority which signed the controller and the Web-console server certificates.
In addition, the browser will show up the nice green lock when accessing the Web-console.

To do so, import the file `nms-microservice/ca/mnms-rootCa.crt.pem` as a CA certificate into your browser. The steps depend on the browser:

- Chorme: navigate to `chrome://settings/certificates`. In the `Authorities` tab, import the certificate file and check `Trust this certificate for identifying websites`.
- Firefox: navigate to `about:preferences#privacy`, scroll down to the `Certificates` section and click on `View Certificates`. 
Import the certificate file and check `Trust this CA to identify websites`.

### MNMS is Ready
The Multiverse Network Management System is finally ready.
The Web console is accessible at `https://mnms.gui:4443`.
Login with: `username=admin password=admin`.

## Deploy from DockerHub Images
[TBD]

## Use Management
[TBD]

## Use Telemetry
> Note: The telemetry service is not secured yet; i.e., agents are not authenticated to the controller and communications are not encrypted. We rely on the next version of MNMS to secure telemetry.

To use the telemetry service, you have to deploy a [Telemetry Agent](https://github.com/amar-ox/nms-telemetry-agent) with the following commands:
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
