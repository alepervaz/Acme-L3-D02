
package acme.entities.sessionPracticum;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SessionValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return SessionPracticum.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final SessionPracticum session = (SessionPracticum) target;
		final ZoneId zoneId = ZoneId.systemDefault();
		final LocalDateTime start = LocalDateTime.ofInstant(session.getStart().toInstant(), zoneId);
		final LocalDateTime end = LocalDateTime.ofInstant(session.getEnd().toInstant(), zoneId);
		final LocalDateTime now = LocalDateTime.now();

		if (start.isBefore(now.plusWeeks(1)))
			errors.rejectValue("start", "start.beforeNow", "Start date must be at least one week in the future");
		else if (end.isAfter(start.plusWeeks(1)))
			errors.rejectValue("end", "end.afterStart", "End date must be at least one week after start");
	}
}
