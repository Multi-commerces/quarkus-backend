# Projet (quarkus, lombok + mapstruct + RESTEasy + Jackson pour la sérialisation JSON + hibernate orm panache et plus encore)

Le projet utilise Quarkus, Le Framework Java Supersonic Subatomic.
Website: https://quarkus.io/ .


Dans une invite de commande, etre positionné sur le répertoire du projet.

##### 1) DOCKER COMPOSE  

=> sudo docker-compose -f docker/keycloak-postgres-dev.yml up
    
##### 2) Lancement Application (avec activation debug sur le port 5005)

=> ./mvnw compile quarkus:dev -DdebugHost=0.0.0.0 

# SWAGGER 

http://localhost:8081/q/openapi/ui/

# Connexion Base de donnnées (psql)

psql -U postgres_user -d multicommerces -h localhost -p 5435


POSTGRES_PASSWORD: postgres_pwd
 

# Mode débug Eclipse

 ![Alt text](readme/debug.png?raw=true "Title") 

# Quarkus - Sécurité (JWT, OAUTH2, OCID)
OIDC server 'http://localhost:8080/auth/realms/multi-commerces' 

https://quarkus.io/guides/security-jwt

https://quarkus.io/guides/security-oauth2

https://quarkus.io/guides/security-openid-connect

https://quarkus.io/guides/security-openid-connect-web-authentication


## Pour lancer l'application en mode développement

Exécuter l'application en mode développement (codage en direct) en utilisant:
```shell script
./mvnw compile quarkus:dev
```

## Empaquetage et exécution de l'application

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
./mvnw package -Pnative
```

Ou, si vous n'avez pas installé GraalVM, vous pouvez exécuter la version exécutable native dans un conteneur en utilisant:
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

Vous pouvez ensuite exécuter votre exécutable natif avec: `./target/api-1.0.0-SNAPSHOT-runner`

Si vous souhaitez en savoir plus sur la création d'exécutables natifs, veuillez consulter 
https://quarkus.io/guides/maven-tooling.html.

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

