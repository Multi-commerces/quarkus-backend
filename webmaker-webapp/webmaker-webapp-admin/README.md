# Projet WEB

Le projet utilise OPenLiberty, Un framework ouvert léger pour créer des microservices Java natifs dans le cloud rapides et efficaces.
Website: https://openliberty.io/ .
    

## Pour lancer l'application en mode développement

Exécuter l'application en mode développement (codage en direct) en utilisant :
`` ./mvn clean:install liberty:dev ``

# Accès page d'ccueil 
http://localhost:9080/dashboard.xhtml

## Empaquetage et exécution de l'application

L'application peut être packagée en utilisant:
```shell script
./mvnw package
```


#FAQ (erreurs)

javax.el.PropertyNotFoundException: Propriété 'data' non trouvée sur le type fr.webmaker.data.product.ProductLangCompositeData
<br />
=> exception sur javax.el, faire une vérification de la page xhtml

com.github.jasminb.jsonapi.exceptions.InvalidJsonApiResourceException: Resource must contain at least one of 'data', 'error' or 'meta' nodes.
<br />
=> surement une reponse vide, il faut au minimum 'data', 'error' or 'meta' dans la réponse.
