
package acme.features.auditor.auditingRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audit.AuditingRecord;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuditingRecordController extends AbstractController<Authenticated, AuditingRecord> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuditingRecordShowService		showAuditingRecordService;
	@Autowired
	protected AuditingRecordListService		listAuditingRecordService;
	@Autowired
	protected AuditingRecordCreateService	createAuditingRecordService;
	@Autowired
	protected AuditingRecordDeleteService	deleteAuditingRecordService;
	@Autowired
	protected AuditingRecordUpdateService	updateAuditingRecordService;


	// Constructors -----------------------------------------------------------
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createAuditingRecordService);
		super.addBasicCommand("show", this.showAuditingRecordService);
		super.addBasicCommand("list", this.listAuditingRecordService);
		super.addBasicCommand("delete", this.deleteAuditingRecordService);
		super.addBasicCommand("update", this.updateAuditingRecordService);

	}

}
