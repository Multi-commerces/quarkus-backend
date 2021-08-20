package fr.mycommerce.transverse;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.IOUtils;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import fr.mycommerce.exception.MissingArgumentException;
import fr.mycommerce.exception.MissingModelException;
import fr.mycommerce.transverse.old.RenderMode;
import fr.webmaker.commons.identifier.Identifier;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>Classe d'administration générique</h1>
 * <p>
 * Gère les actions crud (create, read, update, delete)
 * </p>
 * 
 * @author Julien ILARI
 *
 */
@Slf4j
public abstract class AbstractCrudView<Data extends Serializable, I extends Identifier<?>>  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Opération de récupération d'un item
	 * @return
	 */
	protected abstract List<Model<Data, I>> findAll();
	
	/**
	 * Opération de création d'un nouveau item
	 */
	protected abstract void create();
	
	/**
	 * Opération de mise à jour d'un item existant
	 */
	protected abstract void update();
	
	/**
	 * Opération de suppression d'un item existant
	 * @param id identifiant de l'item existant
	 */
	protected abstract void delete(I id);

	/**
	 * Liste des items
	 */
	@Getter
	protected DataModel<Model<Data, Identifier<?>>> dataModels;
	
	@Getter
	private TreeNode root;

	/**
	 * Type opération
	 */
	@Getter
	@Setter
	protected ActionType action;


	/**
	 * DataTable, nos éléments
	 */
	@Getter
	protected List<Model<Data, I>> items;

	/**
	 * <h1>DataTable, éléments filtrés</h1>
	 * <p>
	 * <b>Attention :</b> M.A.J à la charge du framework primefaces
	 * </p>
	 */
	@Getter
	@Setter
	private List<Model<Data, I>> filteredItems;

	/**
	 * <h1>DataTable, selection multiple</h1>
	 * <p>
	 * <b>Attention :</b> M.A.J à la charge du framework primefaces
	 * </p>
	 */
	@Getter
	@Setter
	private List<Model<Data, I>> selectedItems;

	/**
	 * <h1>DataTable, selection simple</h1>
	 * <p>
	 * <b>Attention : M.A.J</b> à la charge du framework primefaces
	 * </p>
	 */
	@Getter
	@Setter
	protected Model<Data, I> model;

	/**
	 * Fichier upload
	 */
	@Getter
	@Setter
	protected Part file;

	/**
	 * Gestion upload
	 */
	@Getter
	protected UploadedFile uploadedFile;
	public void setUploadedFile(UploadedFile uploadedFile) throws IOException {
		this.uploadedFile = uploadedFile;
		if (uploadedFile != null) {
			try {
				//BeanUtils.copyProperty(model, "picture", uploadedFile.getContent());
			} catch (Exception e) {
				// Ignore
			}
		}
	}
	
	/**
	 * Action upload d'un fichier
	 * 
	 * @param event
	 * @throws IOException 
	 */
	public void fileUploadEvent(FileUploadEvent event) throws IOException {
		uploadedFile = event.getFile();
	}
	
	public String getImage() {
		byte[] content = uploadedFile != null ? uploadedFile.getContent() : null;
		if (content == null)
			return "http://placehold.it/372x372";

		return "data:image/png;base64," + Base64.encodeBase64String(content);
	}
	
	/**
	 * Exemple uploadObject("centering-line-273419", "e-commerce-generic-bucket",
	 * "REF001/" + uploadedFile.getFileName(), uploadedFile.getContent());
	 * 
	 * @param projectId  The ID of your GCP project
	 * @param bucketName The ID of your GCS bucket
	 * @param objectName The ID of your GCS object
	 * @param content    The flux
	 * @throws IOException
	 */
	public static void uploadObject(String projectId, String bucketName, String objectName, byte[] content)
			throws IOException {

		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		BlobId blobId = BlobId.of(bucketName, objectName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		storage.create(blobInfo, content);

		System.out.println(" uploaded to bucket " + bucketName + " as " + objectName);
	}
	
	/**
	 * 
	 * @param projectId The ID of your GCP project
	 * @param bucketName The ID of your GCS bucket
	 * @param objectName The ID of your GCS object
	 * @param filePath The path to your file to upload
	 * @throws IOException
	 */
	public static void uploadObject(String projectId, String bucketName, String objectName, String filePath)
			throws IOException {
		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		BlobId blobId = BlobId.of(bucketName, objectName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

		addFlashMessage(FacesMessage.SEVERITY_INFO,
				"File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
	}

	@Getter
	@Setter
	protected List<UploadedFile> uploadedFiles;

	@Getter
	@Setter
	private DataTable dataTable;

	public AbstractCrudView() {
		dataModels = new ListDataModel<Model<Data, Identifier<?>>>();
		items = new ArrayList<>();

		reset();
	}


	@PostConstruct
	public void postConstructAbstractCrudView() {
		loadItems(findAll());
	}
	
	protected void loadItems(List<Model<Data, I>> items)
	{
		try {
			this.items.clear();
			this.items = Optional.ofNullable(items).orElse(Collections.emptyList());
			dataModels.setWrappedData(items);
		} catch (Exception e) {
			addFlashMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}

	public void reset() {
		model = newModelInstance();
		uploadedFile = null;
		this.action = ActionType.DEFAULT;
		this.selectedItems = null;
	}

	public void reset(ActionEvent event) {
		reset();

		// ---- Update dataTable ---- //
		if (null != dataTable) {
			PrimeFaces.current().executeScript("PF('" + dataTable.getWidgetVar() + "').filter();");
		}
	}

	/**
	 * Action passage mode création
	 * 
	 * @param event
	 */
	public void newItem(ActionEvent event) {
		this.model = newModelInstance();
		action = ActionType.CREATE;
	}
	

	protected Model<Data, I> newModelInstance() {
		return new Model<Data, I>(null, newDataInstance());
	}
	
	protected abstract Data newDataInstance();

	/**
	 * Action selectionner item
	 * 
	 * @param event
	 */
	public void onRowDblSelect(SelectEvent<Model<Data, I>> event) {
		model = event.getObject();
		action = ActionType.UPDATE;
	}

	/**
	 * Action demande d'édition de l'item
	 * 
	 * @param event
	 */
	public void editAction(ActionEvent event) {
		final Object id = Optional.ofNullable(event.getComponent().getAttributes().get("itemId"))
				.orElseThrow(() -> new MissingArgumentException("itemId"));

		model = items.stream().filter(o -> o.getIdentifier().getId().equals(id)).findAny()
				.orElseThrow(() -> new MissingModelException());

		action = ActionType.UPDATE;
	}

	/**
	 * Action sauvegarde item
	 * <h2>Action(s)</h2>
	 * <ul>
	 * <li>ActionType.CREATE</li>
	 * <li>ActionType.UPDATE</li>
	 * </ul>
	 */
	public void saveAction(ActionEvent event) {
		if (uploadedFile != null) {
			try {
			//	BeanUtils.copyProperty(model, "picture", uploadedFile.getContent());
			} catch (Exception e) {
				// Ignore
			}
		}
		try {
			switch (action) {
			case CREATE:
				create();
				break;
			case UPDATE:
				update();
				break;
			default:
				log.warn("action({}) doit etre CREATE ou UPDATE !", action);
				return;
			}
			successOfAction();
			reset();
			// TODO : postSaveAction(); ?
		} catch (Exception e) {
			failureOfAction();
		}
	}
	

	/**
	 * ActionEvent event supprimer
	 * 
	 * @param event
	 */
	public void deleteAction(ActionEvent event) {
		@SuppressWarnings("unchecked")
		final I identifier = (I) Optional.ofNullable(event.getComponent().getAttributes().get("identifier"))
				.orElseThrow(() -> new MissingArgumentException("identifier"));
		
		// [BUSINESS] Opération de supression 
		try {
			delete(identifier);
			successOfAction();
		} catch (Exception e) {
			failureOfAction();
		}
		
		// [WEB] Opération de suppression 
		items.removeIf(o -> identifier.equals(o.getIdentifier()));
		
		/**
		 * Demande une nouvelle instance si le model en-cours de modification est celui
		 * qui vient d'être supprimer
		 */
		if (model != null && model.getIdentifier() != null && model.getIdentifier().equals(identifier)) {
			model = newModelInstance();
		}
	}

	/**
	 * Succès de l'action
	 */
	protected void successOfAction() {
		addFlashMessage(FacesMessage.SEVERITY_INFO, "success of the action " + getAction().name().toLowerCase());
		// ---- Refresh dataTable ---- //
		if (null != dataTable) {
			final Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
			flash.setRedirect(true);
			PrimeFaces.current().executeScript("PF('" + dataTable.getWidgetVar() + "').filter();");
		}
	}

	/**
	 * Passage de la validation en échec avec création d'un facesMessage. Demande de
	 * passer à la phase Response, en ignorant les phases non encore exécutées.
	 */
	protected void failureOfAction() {
		addFlashMessage(FacesMessage.SEVERITY_ERROR, "failure of the action " + getAction().name().toLowerCase());
	}

	protected static void addFlashMessage(final Severity severityName, final String message) {
		FacesMessage facesMessage = null;
		if (severityName.equals(FacesMessage.SEVERITY_INFO)) {
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", message);
		} else if (severityName.equals(FacesMessage.SEVERITY_ERROR)) {
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message);
		}else
		{
			return;
		}

		final FacesContext facesContext = FacesContext.getCurrentInstance();
	
		facesContext.addMessage(null, facesMessage);
	}

	/**
	 * Obtient le fichier sous forme d'octet
	 * 
	 * @exception IllegalArgumentException [error.arg.null] - argument file est null
	 * @param file élément reçu dans une demande POST multipart/form-data.
	 * @return le tableau d'octets demandé
	 */
	protected static byte[] encodeToFile(final Part file) {
		if (file == null)
			return null;
		try (InputStream input = file.getInputStream()) {
			/*
			 * toByteArray met l'entrée en mémoire tampon en interne, il n'est donc pas
			 * nécessaire d'utiliser un BufferedInputStream.
			 */
			return IOUtils.toByteArray(input);
		} catch (IOException e) {
			throw new RuntimeException("error.techn.io.encode", e);
		}
	}

	protected String getValueParam(String param) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// la demande n'est pas une publication:
		if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
			// extraire l'id de la chaîne de requête
			Map<String, String> paramMap = facesContext.getExternalContext().getRequestParameterMap();
			return paramMap.get(param);
		}

		return null;
	}
	
	/**
	 * Extraction de l'identifiant (param => id)
	 * @return
	 */
	protected Long extractId()
	{
		String id = getValueParam("id");
		if(id != null)
		{
			return Long.valueOf(id);
		}
		return null;
	}
	
	/**
	 * Demande le mode de rendu (mobile ou ordinateur de bureau)
	 * @return
	 */
    private RenderMode getBrowserRenderMode() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");
        return userAgent.toLowerCase().contains("mobile") ? RenderMode.MOBILE : RenderMode.WEB;
    }
    
    /**
     * Le mode de rendu (cf. getBrowserRenderMode)
     * @return
     */
    public String getRenderMode() {
        return getBrowserRenderMode().name();
    }
    
    /**
     * Demande le message a afficher sur le bouton de suppression
     * @return
     */
    public String getDeleteButtonMessage() {
        if (hasSelectedItems()) {
            int size = this.selectedItems.size();
            return size > 1 ? size + " items selected" : "1 product selected";
        }

        return "Delete";
    }

    /**
     * Demande si des items sont sélectionnés
     * @return
     */
    public boolean hasSelectedItems() {
        return this.selectedItems != null && !this.selectedItems.isEmpty();
    }

    /**
     * Opération de suppression des items
     */
	public void deleteSelectedItems() {	
		if(!hasSelectedItems()) return;
		
		List<Identifier<?>> identifiers = selectedItems.stream()
				.map(Model::getIdentifier)
				.collect(Collectors.toList());
		
		/**
		 * [WEB] Suppression des items sélectionnés de la liste
		 */
		items.removeIf(o -> identifiers.contains(o.getIdentifier()));
		
		/**
		 * [WEB] Demande une nouvelle instance si le model en-cours de modification est celui
		 * qui vient d'être supprimer
		 */
		Identifier<?> identifierModel = model.getIdentifier();
		if (identifierModel != null && identifiers.stream().anyMatch(identifierModel::equals)) {
				reset();
		}
		
		successOfAction();
	}

}
