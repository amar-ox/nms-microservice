<p align="center">
  <img src="docs/images/logo.png" alt="Multiverse Network Management System [Controller]" />
</p>

> Note: This is the Controller component of the Multiverse project.

## Overview

The Multiverse project aims at providing a feature-rich solution for configuring, monitoring and managing networks.
Although Multiverse is designed with commercial (IP) network management features in mind (e.g., FCAPS), a great attention is dedicated to natively support the information-centric concepts of [Named Data Networking](https://named-data.net/) (NDN).

Multiverse defines two main entities. First, the agent running on each managed node (e.g., a router). Second, the controller, which represents the brain of Multiverse and provides a set of services for network management and telemetry.
The agents and the controller interact in order to provide two sets of services accessible via a Web-based user interface:

- Telemetry, which allows the operator to trigger customized measurements and collect results.
- Network management through which the operator configures and manages the network, assisted by the automatic processing features of the controller.

The current version (v1) supports NDN deployments with the [NDN-DPDK](https://github.com/usnistgov/ndn-dpdk) forwarder.

For network management, the controller and the [Management Agent](https://github.com/multiverse-nms/ndn-dpdk-agent) communicate over HTTPS using the REST API. The OpenAPI Controller-Agent specification can be found [here](docs/openapi/controller-to-agent.yaml).

For telemetry, the controller and the [Telemetry Agent](https://github.com/multiverse-nms/telemetry-agent) use the publish-subscribe communication pattern with AMQP.

## Deployment Instructions

The Controller and the [Web console](https://github.com/multiverse-nms/multiverse-console) are deployable as Docker containers.
The following instructions have been tested on Ubuntu (16.04, 18.04, 20.04), Windows 10, and macOS Catalina.

### Prerequisites

- Docker (19.03)
- Docker-compose (1.26 or 1.27)
- OpenSSL (1.1.1)
- Git Bash (for Windows)

### Prepare The System

In order to build and run Multiverse, you need to generate and trust your own certificates.
The following steps create the SSL keys and certificates used by Multiverse components:

```bash
curl https://raw.githubusercontent.com/multiverse-nms/multiverse-controller/dev/main/gencerts.sh -o gencerts.sh
chmod +x gencerts.sh
./gencerts.sh "<certs_location>"
```

> Note: `<certs_location>` parameter is the location in which the keys and certificates will be created. To avoid errors, absolute paths are recommended.

### Build/Run

#### Controller

Open a terminal and issue the following commands:

```bash
git clone https://github.com/multiverse-nms/multiverse-controller.git
cd multiverse-controller/docker
chmod +x build.sh run.sh
./build.sh
./run.sh "<certs_location>/certs/controller"
```

#### Web-console (GUI)
 
Open another terminal and issue the following commands:

```bash
git clone https://github.com/multiverse-nms/multiverse-console.git
cd multiverse-console
docker build -t multiverse-console .
docker run -it --rm --name multiverse-console -p 4443:443 --network=docker_nms -v <certs_location>/certs/console:/opt/data multiverse-console
```

### Configuration

When you run the project for the first time, there are some configurations to do.

Edit the `/etc/hosts` file and add the following entries:

```text
127.0.0.1    console.multiverse.com
127.0.0.1    controller.multiverse.com
```

In order to allow https traffic between the controller and the Web-console server, browsers must be configured to trust the local certificate authority which signed the controller and the Web-console server certificates.
In addition, the browser will show up the nice green lock when accessing the Web-console.

To do so, add `<certs_location>/ca/MultiverseRootCA.crt.pem` as a trusted CA certificate into your browser. 
The steps depend on the browser and the OS.

### Multiverse is Ready

The Multiverse Network Management System is finally ready.
The Web console is accessible at `https://console.multiverse.com:4443`.
Login with: `username=admin password=admin`.

## Use Management

Instructions to deploy the NDN-DPDK agent are available [here](https://github.com/multiverse-nms/ndn-dpdk-agent). 

## Use Telemetry

Instructions to deploy the telemetry agent are available [here](https://github.com/multiverse-nms/telemetry-agent).


## Contributing

Contributions and feedback are definitely welcome!