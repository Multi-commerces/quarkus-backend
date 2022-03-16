package fr.mycommerce.commons.views;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import fr.mycommerce.commons.models.Model;
import fr.mycommerce.commons.tools.JavaFacesTool;
import fr.mycommerce.exception.MissingArgumentException;
import fr.mycommerce.exception.MissingModelException;
import fr.webmaker.data.BaseResource;
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
public abstract class AbstractCrudView<M extends BaseResource>  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected ObjectMapper objectMapper;
	
	/**
	 * Opération de récupération d'un item
	 * @return
	 */
	protected abstract List<Model<M>> findAll();
	
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
	protected abstract void delete(String id);
	
	protected abstract void delete(List<String> ids);

	/**
	 * Liste des items
	 */
	@Getter
	protected DataModel<Model<M>> dataModels;
	
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
	protected List<Model<M>> items;

	/**
	 * <h1>DataTable, éléments filtrés</h1>
	 * <p>
	 * <b>Attention :</b> M.A.J à la charge du framework primefaces
	 * </p>
	 */
	@Getter
	@Setter
	private List<Model<M>> filteredItems;

	/**
	 * <h1>DataTable, selection multiple</h1>
	 * <p>
	 * <b>Attention :</b> M.A.J à la charge du framework primefaces
	 * </p>
	 */
	@Getter
	@Setter
	private List<Model<M>> selectedItems;

	/**
	 * <h1>DataTable, selection simple</h1>
	 * <p>
	 * <b>Attention : M.A.J</b> à la charge du framework primefaces
	 * </p>
	 */
	@Getter
	@Setter
	protected Model<M> model;

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

		JavaFacesTool.sendFacesMessage("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName, 
				FacesMessage.SEVERITY_INFO,
				false);
	}

	@Getter
	@Setter
	protected List<UploadedFile> uploadedFiles;

	@Getter
	@Setter
	private DataTable dataTable;

	public AbstractCrudView() {
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.registerModule(new JsonNullableModule());
		
		dataModels = new ListDataModel<Model<M>>();
		items = new ArrayList<>();

		reset();
	}


	@PostConstruct
	public void postConstructAbstractCrudView() {
		loadItems(findAll());
	}
	
	protected void loadItems(List<Model<M>> items)
	{
		try {
			this.items.clear();
			this.items = Optional.ofNullable(items).orElse(Collections.emptyList());
			dataModels.setWrappedData(items);
		} catch (Exception e) {
			JavaFacesTool.sendFacesMessage(
					e.getMessage(), 
					FacesMessage.SEVERITY_ERROR,
					false);
		}
	}

	public void reset() {
		model = newModelInstance();
		uploadedFile = null;
		this.action = ActionType.DEFAULT;
//		this.selectedItems = null;
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
	

	protected Model<M> newModelInstance() {
		return new Model<M>(newDataInstance());
	}
	
	protected abstract M newDataInstance();

	/**
	 * Action selectionner item
	 * 
	 * @param event
	 */
	public void onRowDblSelect(SelectEvent<Model<M>> event) {
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

		model = items.stream().filter(o -> o.getIdentifier().equals(id)).findAny()
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
		final String identifier = (String) Optional.ofNullable(event.getComponent().getAttributes().get("identifier"))
				.orElseThrow(() -> new MissingArgumentException("identifier"));
		
		// [BUSINESS] Opération de supression 
		try {
			delete(identifier);
		} catch (Exception e) {
			failureOfAction();
			return;
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
		
		successOfAction();
		this.action = ActionType.DEFAULT;
	}

	/**
	 * Succès de l'action
	 */
	protected void successOfAction() {
		JavaFacesTool.sendFacesMessage("success of the action " + getAction().name().toLowerCase(), 
				FacesMessage.SEVERITY_INFO,
				true);
		
		// ---- Refresh dataTable ---- //
		if (null != dataTable) {
//			final Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
//			flash.setKeepMessages(true);
//			flash.setRedirect(true);
			PrimeFaces.current().ajax().update("form-list:dataTable");
//			PrimeFaces.current().executeScript("PF('" + dataTable.getWidgetVar() + "').filter();");
		}
	}

	/**
	 * Passage de la validation en échec avec création d'un facesMessage. Demande de
	 * passer à la phase Response, en ignorant les phases non encore exécutées.
	 */
	protected void failureOfAction() {	
		JavaFacesTool.sendFacesMessage("failure of the action " + getAction().name().toLowerCase(), 
				FacesMessage.SEVERITY_ERROR,
				false);
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
		
		final List<String> identifiers = selectedItems.stream()
				.map(Model::getIdentifier)
				.collect(Collectors.toList());
		
		/**
		 *  [BUSINESS] Opération de suppression 
		 */
		try {
			delete(identifiers);
		} catch (Exception e) {
			failureOfAction();
			JavaFacesTool.sendFacesMessage(e.getMessage(), 
					FacesMessage.SEVERITY_ERROR,
					false);
			return;
		}
		
		/**
		 * [WEB] Suppression des items sélectionnés de la liste
		 */
		items.removeIf(o -> identifiers.contains(o.getIdentifier()));
		
		/**
		 * [WEB] Demande une nouvelle instance si le model en-cours de modification est celui
		 * qui vient d'être supprimer
		 */
		String identifierModel = model.getIdentifier();
		if (identifierModel != null && identifiers.stream().anyMatch(identifierModel::equals)) {
				reset();
		}
		
		successOfAction();
		action = ActionType.DEFAULT;
	}

}
