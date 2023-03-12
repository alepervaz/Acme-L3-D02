
package acme.entities.practicum;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import acme.framework.helpers.MomentHelper;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PracticumValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return Practicum.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final Practicum practicum = (Practicum) target;
		final List<SessionPracticum> sessions = practicum.getSessions();
		final Double estimatedTimeInHours = practicum.getEstimatedTimeInHours();
		double totalHours = 0.0;

		for (final SessionPracticum session : sessions) {

			final Date start = session.getStart();
			final Date end = session.getEnd();
			final Duration duration = MomentHelper.computeDuration(start, end);
			totalHours += duration.toHours();
		}
		if (totalHours < estimatedTimeInHours * 0.9 || totalHours > estimatedTimeInHours * 1.1)
			errors.rejectValue("estimatedTimeInHours", "estimatedTimeInHours", "Estimated time must be between 90% and 110% of the total time");
	}
}
