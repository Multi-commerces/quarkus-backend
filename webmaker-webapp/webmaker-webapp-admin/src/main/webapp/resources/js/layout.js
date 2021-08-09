//menu

if (!indexedDB) {
	alert("Votre navigateur ne supporte pas une version stable d'IndexedDB. Quelques fonctionnalités ne seront pas disponibles.")
}

// Ouvrons notre première base
var request = indexedDB.open("MyTestDatabase", 3);
var db;
// Gestionnaire
request.onerror = function(event) {
	// Gestionnaire d'erreur générique pour toutes les erreurs de requêtes de cette base
	alert("Database error: " + event.target.errorCode);
};
request.onsuccess = function(event) {
	// Faire quelque chose avec request.result = ok !
	db = event.target.result;

};
