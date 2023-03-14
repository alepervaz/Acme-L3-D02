
package acme.entities.audit;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AuditingRecordValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return AuditingRecord.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final AuditingRecord auditingRecord = (AuditingRecord) target;
		final Long epochEnd = auditingRecord.getEndAudit().toInstant().getEpochSecond();
		final Long epochStart = auditingRecord.getStartAudit().toInstant().getEpochSecond();
		final long periodInSeconds = epochEnd - epochStart;
		final long lessMinim = periodInSeconds - 30 * 60;
		if (epochEnd - epochStart < 0L)
			errors.rejectValue("startAudit", "startAudit.afterEndAudit", "The Audit's start date is after Audit's end date");
		if (lessMinim < 0L)
			errors.rejectValue("endAudit", "endAudit.notEnoughTime", "The Audit finish in less than 30 minuts");

	}

}
