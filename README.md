# SportsCentre Backend

SportsCentre is een sporttracking- en deelplatform ontwikkeld als project in semester 6 (2022-2023) van de studie Software Engineering op Fontys Hogescholen Eindhoven. Het project bestaat uit een front- en backend. De backend is opgebouwd volgens een microservices-architectuur met Java Spring Boot als backendframework en MongoDB als de gekozen database. De frontend repository is [hier](https://github.com/freekvlier/Musicplanet-frontend) te vinden.Hieronder vind je de context en technische details van het backend project. 

## Projectomschrijving
Het idee achter SportsCentre ontstond uit de observatie dat veel mensen hun workouts in de notitie-app van hun telefoon bijhouden, wat leidt tot lange lijsten aan het einde van de dag. SportsCentre biedt een oplossing door een platform te creëren waarmee gebruikers eenvoudig hun sportieve prestaties kunnen bijhouden en delen. Het omvat functionaliteiten zoals het starten van een workout, het invoeren van oefeningen en reps, en het delen van workouts met vrienden via een tijdlijn.

## Technologieën
#### Backend Framework: Java Spring Boot
Voor de backend van SportsCentre is gekozen voor Java Spring Boot. Deze keuze is gebaseerd op de wens voor een flexibel backendframework dat goed aansluit bij de vereisten van een microservices-architectuur.

#### Database: MongoDB
De backend maakt gebruik van MongoDB als databaseoplossing. MongoDB biedt horizontale schaalbaarheid en snelheid, wat essentieel is voor het ondersteunen van een groot aantal gebruikers.

#### Message Broker: Apache Kafka
Apache Kafka is geïmplementeerd als message broker om communicatie tussen verschillende microservices mogelijk te maken. De keuze voor Kafka was gebaseerd op zijn hoge throughput en de mogelijkheid tot horizontale schaling.

#### Authenticatie: Microsoft Azure App Service
Voor authenticatie is gekozen voor Edge-Level Authorization met API Gateway. De API Gateway werkt als de centrale interface voor alle verzoeken. In deze API Gateway wordt authenticatie afgehandeld via OAuth met Microsoft Azure als Identity Provider. Dit betekent dat gebruikers worden geauthenticeerd en geautoriseerd via Microsoft voordat hun verzoeken de microservices bereiken.

## Cloud Platform: Google Cloud
Het project maakt gebruik van Google Cloud als cloudplatform. De keuze voor Google Cloud is gebaseerd op de beschikbare gratis tier en de naadloze integratie met de CI/CD-pipeline.

## Microservices
De backend bestaat uit de volgende microservices:

#### Gateway Service
Beheert authenticatie en routeert verkeer naar de juiste microservice.

#### User Service
Handelt gebruikersgegevens af.

#### Workout Service
Houdt de workouts van gebruikers bij.

## Monitoring
Voor monitoring wordt Prometheus geïntegreerd in het project. Prometheus is een open-source monitoring- en alertingsysteem dat is ontworpen voor cloud-native omgevingen.

## Containerization & Orchestration
#### Docker
Het project maakt gebruik van Docker voor containerisatie. Er zijn verschillende Docker Compose-bestanden beschikbaar voor verschillende configuraties, waaronder lokale ontwikkeling, gebruik van Azure of MongoDB Atlas.

#### Kubernetes
De Kubernetes-configuratie bevindt zich in de map "sportscentre/kubernetes/deployment.yaml".

## Load Testing
#### k6
K6 wordt gebruikt voor het uitvoeren van load tests. Het load_test.js-bestand in de k6-directory kan worden gebruikt om de prestaties van het systeem onder verschillende belastingen te evalueren.

## CI/CD
Er zijn verschillende pipelines geïmplementeerd voor Continuous Integration en Continuous Deployment:

- Build & Unit/Integration Tests Pipeline
- Docker Build & Push Pipeline
- Google Cloud Deployment Pipeline
- Kubescape Scan Pipeline
- SonarQube Scan Pipeline

# Installatie
Volg onderstaande stappen om de backend van SportsCentre op te zetten:
### 1. Clone de repository en navigeer naar de backend-directory:

```bash
git clone https://github.com/freekvlier/sportscentre-backend.git
cd sportscentre-backend
```

### 2. Maak een .env-bestand aan:
Maak een nieuw bestand met de naam .env in de root van de backend-directory. Voeg de volgende variabelen toe en vul ze in met de juiste waarden:

```bash
AZURE_SET_URI={Insert Azure set uri here}
AZURE_ISSUER_URI={Insert Azure issuer uri here}
AZURE_AUDIENCE={Insert Azure audience(s) here}
ENCRYPT_KEY={Insert MongoDB encryption key here}
ATLAS_CONNECTION_STRING={Insert MongoDB atlas connection string here}
AZURE_CONNECTION_STRING={Insert azure connection string here}
USER_DATABASE_NAME={Insert user database name here}
WORKOUT_DATABASE_NAME={Insert workout database name here}
```

### 3. Start de backend-services:
#### Docker 
Gebruik een van de Docker Compose-bestanden om de backend-services op te starten. Bijvoorbeeld:
```bash
docker-compose -f docker-compose-development.yml up
```


#### Kubernetes
```bash
kubectl apply -f sportscentre/kubernetes/deployment.yaml
```

# Leeruitkomsten
Gedurende het semester zijn de volgende leeruitkomsten aangetoond voor het behalen van het semester:
### Learning outcome 1 - Developing and Deployment of Enterprise Software as a Professional

You develop and deploy enterprise software, both individually and as a team. You select a suitable enterprise development platform and application framework(s). You select and apply a software development process, which complies with professional industry standards. You actively share knowledge within your team and with stakeholders to improve knowledge & processes.

### Learning Outcome 2 - Context Based Research

You deliver professional products according to planning, which is the result of a structured and methodical investigation. You have a critical view towards your own and other people’s work, by comparing them to alternatives, judging the structured and methodical approach and considering general accepted and ethical values. Your products are validated with stakeholders and other available research, and you can judge & communicate the relevance and value of the project in its own context.

### Learning Outcome 3 - Preparation for Life-Long Learning

You acquire skills required for your future career. You are aware of multiple career paths and can reflect which ones fit best, considering your (potential) skills and ambitions. You are aware of developments in software engineering and can signal trends.

### Learning Outcome 4 - Scalable Architectures

Besides functionality, you develop the architecture of enterprise software based on quality attributes. You especially consider attributes most relevant to enterprise contexts with high volume data and events. You design your architecture with future adaptation in mind. Your development environment supports this by being able to independently deploy and monitor the running parts of your application.

### Learning Outcome 5 - Development and Operations (DevOps)

You set up environments and tools which support your chosen software development process. You provide governance for all stakeholders’ goals. You aim for as much automation as possible, to enable short release times and high software quality.

### Learning Outcome 6 - Cloud Services

You can explain what a cloud platform provider is and can deploy (parts of) your application to a cloud platform. You integrate cloud services (for example: Serverless computing, cloud storage, container management) into your enterprise application, and can explain the added value of these cloud services for your application.

### Learning Outcome 7 - Security by Design

You investigate how to minimize security risks for your application, and you incorporate best practices in your whole software development process. 

### Learning Outcome 8 – Distributed Data

You are aware of specific data requirements for enterprise systems. You apply best practices for distributed data during your whole development process, both for non-functional and functional requirements. You especially take legal and ethical issues into consideration.
