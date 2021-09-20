# Projet (quarkus, lombok + mapstruct + RESTEasy + Jackson pour la sérialisation JSON + hibernate orm panache et plus encore)


Le projet utilise Quarkus, Le Framework Java Supersonic Subatomic. <br />
Website: https://quarkus.io/ . <br />


# Mode développement en local (localhost)

Dans une invite de commande, etre positionné sur le répertoire du projet.


##### 1) docker-compose (keycloak + postgres) 

L'application quarkus sera lancée manuellement et l'image docker sera construite pour les autres environnements.

```shell script
docker-compose -f docker/local/docker-compose.yml --env-file .env up -d
```

psql -U postgres_user -d multicommerces -h localhost -p 5435 <br />
POSTGRES_PASSWORD: postgres_pwd   
    
##### 2) Exécuter l'application en mode développement (codage en direct) 

```shell script
./mvnw compile quarkus:dev -DdebugHost=0.0.0.0 
```

##### Toutes les étapes (shell script) :

```shell script
sdk default java 11.0.12-open && \
cd /home/njlg6500/workspace/jilari/webmaker/webmaker-business/docker/local/ && \
docker-compose up -d && \
cd /home/njlg6500/workspace/jilari/webmaker/webmaker-business && \
./mvnw compile quarkus:dev -DdebugHost=0.0.0.0
```




# Mode développement à distance (remote)

https://blog.sebastian-daschner.com/entries/quarkus-remote-dev-in-containers-update

L'Application doit-être mutable, en utilisant le format mutable-jar. Nous avons définit les propriétés suivantes dans application.properties :

<li>quarkus.package.type =mutable-jar (1)
<li>quarkus.live-reload.password =changeit (2)
<li>quarkus.live-reload.url =http://my.cluster.host.com:8080 (3)

Nous avons définit dans le docker-compse la variable d'environnement QUARKUS_LAUNCH_DEVMODE=true.


Vous devez maintenant connecter votre agent local à l'hôte distant, à l'aide de la remote-devcommande :

```shell script
./mvnw quarkus:remote-dev -Dquarkus.live-reload.url=http://my-remote-host:8081
```


#####  Lancement (être dans le répertoire ./docker/local)

```shell script
docker-compose -f docker.compose.yml --env-file .env up -d
```


# PRODCUTION

##### 1) Compiler l'appication

```shell script
./mvnw compile
```

##### 2) Copier l'application sur le raspberry

```shell script
scp -r ./target/quarkus-app  pi@192.168.1.67:/home/pi/workspace/
```

##### 3) Construire l'image docker

```shell script
docker build -f src/main/docker/Dockerfile.fast-jar -t quarkus/webmaker-api .
```

##### 4) Créer une archive "tar" de l'image docker

```shell script
docker save quarkus/webmaker-api > ./target/docker-image-quarkus-webmaker-api.tar
```

##### 5) Copier l'image docker sur le raspberry

```shell script
scp ./target/docker-image-quarkus-webmaker-api.tar pi@192.168.1.67:/home/workspace/
```

##### Toutes les étapes (shell script) :

```shell script
./mvnw package && \
docker build -f src/main/docker/Dockerfile.fast-jar -t quarkus/webmaker-api . && \
docker save quarkus/webmaker-api > ./target/docker-image-quarkus-webmaker-api.tar && \
scp ./target/docker-image-quarkus-webmaker-api.tar pi@192.168.1.67:/home/workspace/ && \
scp -r ./target/quarkus-app  pi@192.168.1.67:/home/pi/workspace/
```


# ACCESS TOKEN

```
export access_token=$(\
    curl --insecure -X POST https://localhost:8081/auth/realms/webmaker/protocol/openid-connect/token \
    --user backend-service:secret \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=<<adresse_mail>>&password=<<password>>&grant_type=password' | jq --raw-output '.access_token' \
 )
 ```
 
 ```
 curl -v -X GET \
  http://localhost:8081/languages/fr/products \
  -H "Authorization: Bearer "$access_token
 ```



# Empaquetage et exécution de l'application

L'application peut être packagée en utilisant:

```shell script
./mvnw package
```

Il produit le fichier `api-1.0.0-SNAPSHOT-runner.jar` dans le répertoire`/target`.
Sachez qu'il ne s'agit pas d'un uber jar car les dépendances sont copiées dans le répertoire `target/lib`.

Si vous souhaitez créer un uber jar, exécutez la commande suivante:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

L'application est désormais exécutable en utilisant `java -jar target/api-1.0.0-SNAPSHOT-runner.jar`.



## Créer un exécutable natif

Vous pouvez créer un exécutable natif en utilisant:

```shell script
./mvnw package -Pnative -Dmaven.test.skip=true
```

Ou, si vous n'avez pas installé GraalVM, vous pouvez exécuter la version exécutable native dans un conteneur en utilisant:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

Vous pouvez ensuite exécuter votre exécutable natif avec: `./target/api-1.0.0-SNAPSHOT-runner`

Si vous souhaitez en savoir plus sur la création d'exécutables natifs, veuillez consulter 
https://quarkus.io/guides/maven-tooling.html.

## Créer l'image docker de l'application quarkus

Si l'exécutable natif généré n'a pas été supprimé , créer l'image docker avec :


```
docker build -f src/main/docker/Dockerfile.native -t quarkus/webmaker-api .
```


Et enfin, lancez-le avec :

```
docker run -i --rm -p 8080:8080 quarkus/webmaker-api
```

--net=host option est utilisée pour donner l'impression que les programmes à l'intérieur du conteneur Docker s'exécutent sur l'hôte lui-même, du point de vue du réseau. Il permet au conteneur un plus grand accès au réseau que ce qu'il peut normalement obtenir.

 
 # SWAGGER 

http://localhost:8081/openapi/ui/

Swagger est égelement activé pour la production
quarkus.swagger-ui.always-include=true



# Requêtes (psql)


<b>SELECT</b> pi.*, title, i.name, file_name, ci.width || 'px' "width", ci.height || 'px' "height" <br />
<b>FROM</b> product_image pi <b>INNER JOIN</b> image i on pi.image_id = i.image_id <br />
   <b>INNER JOIN</b> CONFIG_IMAGE ci on ci.CONFIG_IMAGE_ID = i.CONFIG_IMAGE_ID ;


# Mode débug Eclipse

Lancement Application (avec activation debug sur le port 5005)

```shell script
 ./mvnw compile quarkus:dev -DdebugHost=0.0.0.0
```

 ![Alt text](readme/debug.png?raw=true "Title") 

# Quarkus - Sécurité (JWT, OAUTH2, OCID)

OIDC server 'http://localhost:8080/auth/realms/multi-commerces' 
<li>https://quarkus.io/guides/security-jwt
<li>https://quarkus.io/guides/security-oauth2
<li>https://quarkus.io/guides/security-openid-connect
<li>https://quarkus.io/guides/security-openid-connect-web-authentication
 
 
# Authorization Client Java API

https://github.com/keycloak/keycloak-documentation/blob/master/authorization_services/topics/service-client-api.adoc

https://www.keycloak.org/docs-api/15.0/rest-api/index.html#_clients_resource

# RESTEasy JSON serialisation en utilisant Jackson

<p>Cet exemple illustre la sérialisation de RESTEasy JSON en vous permettant de lister, d'ajouter et de supprimer des types de quarks d'une liste. </p>
<p><b>Quarked !</b> </p>

Guide: https://quarkus.io/guides/rest-json

# MISE EN CACHE DES DONNÉES D'APPLICATION - cache-quickstart

Dans ce guide, vous apprendrez à activer la mise en cache des données d'application dans n'importe quel bean géré CDI de votre application Quarkus.

Guide: https://quarkus.io/guides/cache

# CERBOT pour la génération d'un certicat 

Nous utiliserons Docker, Certbot et Nginx sur un serveur Linux.
Suivre le guide pour déployer des certificats gratuits, valables 90 jours et délivrés par une autorité certifiée.

Guide:https://blog.eleven-labs.com/fr/https-part-2/

docker run -it --rm --name certbot -p 80:80 -p 443:443 -v "/etc/letsencrypt:/etc/letsencrypt" -v "/var/lib/letsencrypt:/var/lib/letsencrypt" certbot/certbot certonly --standalone --email "julien.ilari@gmail.com" -d "jilari.fr"

Une fois la commande exécutée, si tout s’est bien déroulé, vous trouverez le certificat dans le dossier 
“/etc/letsencrypt/live/”.

# Prise en compte du certificat google


# Activer Java Faces + Omnis Faces + Primefaces

	<dependency>
		<groupId>org.apache.myfaces.core.extensions.quarkus</groupId>
		<artifactId>myfaces-quarkus-deployment</artifactId>
		<version>${myfaces.version}</version>
	</dependency>
	<dependency>
		<groupId>org.omnifaces</groupId>
		<artifactId>omnifaces</artifactId>
		<version>${omnifaces.version}</version>
		<scope>provided</scope>
	</dependency>

	<dependency>
		<groupId>com.github.adminfaces</groupId>
		<artifactId>quarkus-omnifaces</artifactId>
		<version>${omnifaces-quarkus.version}</version>
	</dependency>
	<dependency>
		<groupId>org.apache.myfaces.core.extensions.quarkus</groupId>
		<artifactId>myfaces-quarkus</artifactId>
		<version>${myfaces.version}</version>
	</dependency>
	
	<dependency>
		<groupId>org.primefaces</groupId>
		<artifactId>primefaces</artifactId>
		<version>10.0.0</version>
	</dependency>

		

