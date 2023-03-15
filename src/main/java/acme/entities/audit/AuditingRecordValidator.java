
package acme.entities.audit;

import java.time.Duration;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import acme.framework.helpers.MomentHelper;

public class AuditingRecordValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return AuditingRecord.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final AuditingRecord auditingRecord = (AuditingRecord) target;
		final Duration duration = MomentHelper.computeDuration(auditingRecord.getStartAudit(), auditingRecord.getEndAudit());
		if (MomentHelper.isBefore(auditingRecord.getStartAudit(), auditingRecord.getEndAudit()))
			errors.rejectValue("startAudit", "startAudit.afterEndAudit", "The Audit's start date is after Audit's end date");
		if (duration.toMinutes() < 30)
			errors.rejectValue("endAudit", "endAudit.notEnoughTime", "The Audit finish in less than 30 minuts");
	}

}
