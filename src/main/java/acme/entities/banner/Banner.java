
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent(message = "Instant must be in the past")
	private Date				instant;

	@Temporal(TemporalType.TIMESTAMP)
	private Date				displayStart;

	@Temporal(TemporalType.TIMESTAMP)
	private Date				displayEnd;

	@URL
	private String				picture;

	@NotBlank(message = "Slogan must not be blank")
	@Length(max = 76, message = "Slogan must be shorter than 76 characters")
	private String				slogan;

	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
