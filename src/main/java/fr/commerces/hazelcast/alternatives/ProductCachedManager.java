//package fr.commerces.hazelcast.alternatives;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//import javax.annotation.PostConstruct;
//import javax.enterprise.event.Observes;
//import javax.inject.Inject;
//import javax.transaction.Transactional;
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.map.IMap;
//
//import fr.commerces.services.authentifications.provider.AuthenticationContext;
//import fr.commerces.services.products.data.ProductData;
//import fr.commerces.services.products.ressources.ProductManager;
//import fr.commerces.services.products.ressources.ProductService;
//import io.quarkus.arc.Lock;
//import io.quarkus.runtime.ShutdownEvent;
//
///**
// * <h1>ProductCachedManager</h1>
// * <h2>Lock.Type.WRITE</h2>
// * <p>
// * Indique au conteneur de verrouiller l'instance du bean pour toute invocation
// * de n'importe quelle méthode métier.
// * </p>
// * <p>
// * c'est-à-dire que le client a un "accès exclusif" et aucune invocation
// * simultanée ne sera autorisée.
// * </p>
// * 
// * @author Julien ILARI
// *
// */
////@Lock(Lock.Type.WRITE)
////@ApplicationScoped
////@AlternativePriority(-1)
//class ProductCachedManager implements ProductService {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(ProductCachedManager.class);
//
//	@Inject
//	ProductManager manager;
//
//	@Inject
//	HazelcastInstance hazelcastClient;
//
//	private final static String NAME = "products";
//
//	private IMap<Long, ProductData> getMap() {
//		return hazelcastClient.getMap(NAME);
//	}
//
//	@PostConstruct
//	public void postConstructProductCachedManager() {
//
//	}
//
//	/**
//	 * Lock.Type.READ
//	 * <p>
//	 * remplace la valeur spécifiée au niveau de la classe. Cela signifie que
//	 * n'importe quel nombre de clients peut appeler la méthode simultanément, sauf
//	 * si l'instance du bean est verrouillée par @Lock(Lock.Type.WRITE).
//	 * </p>
//	 */
//	@Lock(value = Lock.Type.READ, time = 1, unit = TimeUnit.SECONDS)
//	@Override
//	public List<ProductData> list(Optional<Integer> limite) {
//
//		final IMap<Long, ProductData> map = getMap();
//		if (map.isEmpty()) {
//			LOGGER.info("CACHE isEmpty !", map.getName());
//			manager.list(Optional.empty()).parallelStream().forEach(o -> {
//				map.put(0L, o);
//			});
//
//			LOGGER.info("CACHE up {}");
//		} else {
//			LOGGER.info("CACHE use {}", map.size());
//		}
//
//		return map.values().parallelStream().limit(limite.get()).collect(Collectors.toList());
//	}
//
//	@Override
//	public Optional<ProductData> findById(@NotNull Long id) {
//		return Optional.ofNullable(getMap().get(id));
//	}
//
//	/**
//	 * <h1>Mise à jour avec prise en compte du cache</h1>
//	 * <p>
//	 * Si la mappe contenait auparavant un mappage pour la clé, l'ancienne valeur
//	 * est remplacée par la valeur spécifiée.
//	 * </p>
//	 */
//	@Transactional
//	@Override
//	public Optional<Long> update(@NotNull Long id, @Valid ProductData data) {
//		var value = manager.update(id, data);
//		if (value.isPresent()) {
//			getMap().put(value.get(), data);
//		}
//		return value;
//	}
//
//	@Lock(Lock.Type.NONE)
//	@Transactional
//	@Override
//	public Long create(AuthenticationContext ctx, ProductData data) {
//		
//			Thread daemonThread = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						for (int i = 0; i < 1111222L; i++) {
//							var identifiant = manager.create(ctx, data);
//							getMap().put(identifiant, data);
//						}
//					} catch (Exception e) {
//						// ignore
//						
//					}
//				}
//			}, "DemonCreateProduct");
//			daemonThread.setDaemon(true);
//			daemonThread.start();
//		
//		var id = manager.create(ctx, data);
//		getMap().put(id, data);
//		
//		return id;
//	}
//
////	void onStart(@Observes StartupEvent event, HazelcastInstance hazelcastClient, ProductManager manager, ProductMapper mapper) {
////		LOGGER.info("[hazelcastClient] remise à zero !");
////		
////		try {
////			hazelcastClient.getMap(NAME).clear();
////			var values = Product.<Product>listAll();
////					//manager.list(Optional.empty());
////			LOGGER.info("[hazelcastClient]  {}");
////			values.parallelStream().forEach(o -> {
////				getMap().put(0L, mapper.toData(o));
////				
////			});
////			
////			LOGGER.info("[hazelcastClient] Mise à jour réussie ! {}", values.size());
////		} catch (Exception e) {
////			// ignore
////			LOGGER.error("[hazelcastClient] Mise à jour en echec !", e);
////		}
////	}
//
//	void onStop(@Observes ShutdownEvent ev) {
//		// Ignore
//	}
//
//}
