<a name="readme-top"></a>

<br />

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
      <a href="#usage">Usage</a>
    </li>
    <li>
      <a href="#howToRunApplication">How to Run Application</a>
    </li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This project is a Spring Boot Application that uses [bucket4j-hazelcast](https://github.com/bucket4j/bucket4j/tree/master/bucket4j-hazelcast-all/bucket4j-hazelcast) to provide Rate Limiting on distributed nodes.

<p align="right"><a href="#readme-top">back to top</a></p>

### Built With

* [![Java][Java.com]][Java-url]
* [![Spring][Spring.com]][Spring-url]
* [![Hazelcast][Hazelcast.com]][Hazelcast-url]
* [![Gradle][Gradle.com]][Gradle-url]
* [![Docker][Docker.com]][Docker-url]


<p align="right"><a href="#readme-top">back to top</a></p>

## Getting Started

Please follow Prerequisites and Installation before you run the application.

### Prerequisites

* Java JDK 17
* Gradle 8.4
* Docker 24.0.6
* Docker Compose v2.22.0-desktop.2

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/berkanterdogan/bucket4j-demo.git
   ```
2. Go to docker-compose directory of the project
3. Before run Hazelcast Cluster, you should arrange some environment variables below:
   - `HZ_NETWORK_PUBLICADDRESS`
   - `HAZELCAST_MC_ADMIN_USER`
   - `HAZELCAST_MC_ADMIN_PASSWORD`
4. Run Hazelcast Cluster
   ```sh
   docker-compose up -d
   ```

<p align="right"><a href="#readme-top">back to top</a></p>

## Usage

You can secure your endpoints with Rate Limiting according to `bucket4j-demo.rate-limiter.object-value` property in [application.yml](src/main/resources/application.yml).

An example config for `bucket4j-demo.rate-limiter.object-value` property:

```json
{
   "limiters": [
      {
         "path": "/", // DEFAULT RATE LIMITING CONFIG
         "limits": [
            {
               "timeUnit": "seconds",
               "timeValue": "1",
               "capacity": "1"
            },
            {
               "timeUnit": "minutes",
               "timeValue": "5",
               "capacity": "10"
            },
            {
               "timeUnit": "hours",
               "timeValue": "1",
               "capacity": "100"
            },
            {
               "timeUnit": "days",
               "timeValue": "1",
               "capacity": "1000"
            }
         ]
      },
      {
         "path": "/api/area-calculation/rectangle",
         "limits": [
            {
               "timeUnit": "seconds",
               "timeValue": "1",
               "capacity": "1"
            },
            {
               "timeUnit": "minutes",
               "timeValue": "1",
               "capacity": "5"
            },
            {
               "timeUnit": "hours",
               "timeValue": "1",
               "capacity": "10"
            },
            {
               "timeUnit": "days",
               "timeValue": "1",
               "capacity": "15"
            }
         ]
      }
   ]
}
```

In this application, there are 2 endpoint. 
- **/api/area-calculation/rectangle**
- **/api/area-calculation/square**

According to existing configuration, you see there is no any specific configuration in `bucket4j-demo.rate-limiter.object-value` property 
for **/api/area-calculation/square** endpoint. So, The default rate limiting configuration is used for this endpoint.

<p align="right"><a href="#readme-top">back to top</a></p>

## How to Run Application

1. Go to the project directory

2. Build:
   ```sh
   gradle clean build
   ```

3. Run first application:
   ```sh
   gradle bootRun
   ```

4. Run second application:
   ```sh
   PORT=8082 gradle bootRun
   ```

Also, if you use Intellij IDEA, you will have [2](.idea/runConfigurations) run configurations. You can run your applications using them.

After running applications, you can send requests to exposed endpoints using [sample-requests.http](sample-requests.http) file in Intellij IDEA. 
If you don't have Intellij IDEA, you can send requests using Postman, CURL etc.

<p align="right"><a href="#readme-top">back to top</a></p>

[Java.com]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://openjdk.org/projects/jdk/17/
[Spring.com]: https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/projects/spring-boot
[Docker.com]: https://img.shields.io/badge/Docker-0db7ed?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/
[Gradle.com]: https://img.shields.io/badge/Gradle-209BC4?style=for-the-badge&logo=gradle&logoColor=white
[Gradle-url]: https://gradle.org/
[Hazelcast.com]: https://img.shields.io/badge/H_Hazelcast-C6FF3A?style=for-the-badge&logo=Hazelcast&logoColor=white
[Hazelcast-url]: https://hazelcast.com/
